/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private static final int CAPACITY=10000;
    private int size = 0;
    private Resume[] storage = new Resume[CAPACITY];

    void clear() {
    }

    void save(Resume r) {
        if (size<CAPACITY) {
            storage[size] = r;
            size++;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)){
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return new Resume[0];
    }

    int size() {
        return size;
    }
}
