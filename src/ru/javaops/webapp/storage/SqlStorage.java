package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.model.*;
import ru.javaops.webapp.storage.util.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String url, String user, String password) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(url, user, password));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO resume (UUID, full_name) VALUES (?,?)")) {
                setStatement(statement, resume.getUuid(), resume.getFullName());
                statement.execute();
            }
            insertContacts(resume, connection);
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactExecute(connection -> {
            Resume resume;
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM resume WHERE uuid=?")) {
                setStatement(statement, uuid);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistsStorageException(uuid);
                }
                resume = getResume(resultSet);
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM contact WHERE resume_uuid=?")) {
                setStatement(statement, resume.getUuid());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    ContactType contactType = ContactType.valueOf(resultSet.getString("type"));
                    resume.setContact(contactType, resultSet.getString("value"));
                }
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM section WHERE resume_uuid=?")) {
                setStatement(statement, resume.getUuid());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    SectionType sectionType = SectionType.valueOf(resultSet.getString("type"));
                    resume.setSection(sectionType, switchSection(sectionType, resultSet.getString("value")));
                }
            }
            return resume;
        });
    }

    private AbstractSection switchSection(SectionType sectionType, String value) {
        return switch (sectionType) {
            case OBJECTIVE, PERSONAL -> new TextSection(value);
            case ACHIEVEMENT, QUALIFICATION -> {
                List<String> lines = Arrays.asList(value.split("\n"));
                yield new ListTextSection(lines);
            }
            case EXPERIENCE, EDUCATION -> null;
        };
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE resume SET full_name=? WHERE uuid=?")) {
                setStatement(statement, resume.getFullName(), resume.getUuid());
                if (statement.executeUpdate() == 0) {
                    throw new NotExistsStorageException(resume.getUuid());
                }
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM contact WHERE resume_uuid=?")) {
                setStatement(statement, resume.getUuid());
                statement.execute();
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM section WHERE resume_uuid=?")) {
                setStatement(statement, resume.getUuid());
                statement.execute();
            }
            insertContacts(resume, connection);
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", statement -> {
            setStatement(statement, uuid);
            if (statement.executeUpdate() == 0) {
                throw new NotExistsStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactExecute(connection -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Resume resume = getResume(resultSet);
                    resumes.put(resume.getUuid(), resume);
                }
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM contact")) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String resumeUuid = resultSet.getString("resume_uuid").strip();
                    String contactType = resultSet.getString("type");
                    String contactValue = resultSet.getString("value");
                    resumes.get(resumeUuid).setContact(ContactType.valueOf(contactType), contactValue);
                }
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM section")) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String resumeUuid = resultSet.getString("resume_uuid").strip();
                    SectionType sectionType = SectionType.valueOf(resultSet.getString("type"));
                    String value = resultSet.getString("value");
                    resumes.get(resumeUuid).setSection(sectionType, switchSection(sectionType, value));
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int getSize() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", statement -> {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }

    private void setStatement(PreparedStatement statement, String... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            statement.setString(i + 1, values[i]);
        }
    }

    private Resume getResume(ResultSet resultSet) throws SQLException {
        return new Resume(resultSet.getString("uuid").strip(), resultSet.getString("full_name"));
    }

    private void insertContacts(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                setStatement(statement, resume.getUuid(), contact.getKey().name(), contact.getValue());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void insertSections(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
                String value = switch (section.getKey()) {
                    case OBJECTIVE, PERSONAL -> ((TextSection) section.getValue()).getText();
                    case ACHIEVEMENT, QUALIFICATION ->
                            String.join("\n", ((ListTextSection) section.getValue()).getTextList());
                    case EXPERIENCE, EDUCATION -> null;
                };
                setStatement(statement, resume.getUuid(), section.getKey().name(), value);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

}