package org.example.repository;

import java.io.*;

public class DataStorageSerialization implements Serializable {
    @Serial
    private static final long serialVersionUID = 1l;
    private final String fileName = "dataStorage.ser";

    private DataStorageSerialization() {
    }

    public static DataStorageSerialization getInstance() {
        return DataStorageSerializationHolder.DATA_STORAGE_SERIALIZATION;
    }

    public void save(final DataStorage dataStorage) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))){
            objectOutputStream.writeObject(dataStorage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class DataStorageSerializationHolder {
        private static final DataStorageSerialization DATA_STORAGE_SERIALIZATION = new DataStorageSerialization();
    }
}
