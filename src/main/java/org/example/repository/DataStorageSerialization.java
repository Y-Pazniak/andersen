package org.example.repository;

import org.example.exception.DatabaseDoesNotExist;

import java.io.*;

public class DataStorageSerialization implements Serializable {
    @Serial
    private static final long serialVersionUID = 1l;
    private static final String FILE_NAME = "dataStorage.ser";

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
        File file = new File(FILE_NAME);
        try {
            if (!file.exists()) {
                throw new DatabaseDoesNotExist(FILE_NAME + " does not exist for some reasons");
            }
        } catch (DatabaseDoesNotExist e) {
            System.out.println(e.getMessage());
            return;
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
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
