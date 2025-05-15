package org.example.repository;

import org.example.model.Customer;
import org.example.model.Reservation;
import org.example.model.Workspace;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DataStorage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1l;

    private Map<Integer, Customer> customers;

    private DataStorage() {
        customers = new HashMap<>();
    }

    public Customer getCustomer(final int id) {
        return customers.get(id);
    }

    public void addCustomer(final Customer customer) {
        customers.put(customer.getId(), customer);
    }

    public Map<Integer, Customer> getAllCustomers() {
        return customers;
    }

    public void removeCustomer(final int id) {
        customers.remove(id);
    }

    public static DataStorage getInstance() {
        return DataStorageHolder.DATA_STORAGE;
    }

    private static class DataStorageHolder {
        private static final DataStorage DATA_STORAGE = new DataStorage();
    }
}
