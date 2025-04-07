package org.example.repository;

import java.io.*;

public class DataStorageSerialization implements Serializable {
    @Serial
    private static final long serialVersionUID = 1l;
    private final String FILE_NAME = "dataStorage.ser";

    private DataStorageSerialization() {
    }

    public static DataStorageSerialization getInstance() {
        return DataStorageSerializationHolder.DATA_STORAGE_SERIALIZATION;
    }

    public void save(final DataStorage dataStorage) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            objectOutputStream.writeObject(dataStorage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(FILE_NAME)))) {
            DataStorage loadedData = (DataStorage) objectInputStream.readObject();
            DataStorage currentData = DataStorage.getInstance();

            currentData.getAllReservations().clear();
            currentData.getAllWorkspaces().clear();
            currentData.getAllCustomers().clear();

            currentData.getAllReservations().putAll(loadedData.getAllReservations());
            currentData.getAllWorkspaces().putAll(loadedData.getAllWorkspaces());
            currentData.getAllCustomers().putAll(loadedData.getAllCustomers());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static class DataStorageSerializationHolder {
        private static final DataStorageSerialization DATA_STORAGE_SERIALIZATION = new DataStorageSerialization();
    }
}
