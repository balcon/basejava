CREATE TABLE resume
(
    uuid      CHAR(36) PRIMARY KEY NOT NULL,
    full_name TEXT                 NOT NULL
);

CREATE TABLE contact
(
    resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL,
    CONSTRAINT contact_pk PRIMARY KEY (resume_uuid, type)
);

CREATE TABLE section
(
    resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT    NOT NULL,
    value       TEXT    NOT NULL,
    CONSTRAINT section_pk PRIMARY KEY (resume_uuid, type)
);