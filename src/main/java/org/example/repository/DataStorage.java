package org.example.repository;

import org.example.model.Customer;
import org.example.model.Reservation;
import org.example.model.Workspace;

import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    private Map<Integer, Customer> customers;
    private Map<Integer, Reservation> reservations;
    private Map<Integer, Workspace> workspaces;

    private DataStorage() {
        customers = new HashMap<>();
        reservations = new HashMap<>();
        workspaces = new HashMap<>();
    }

    public Customer getCustomer(final int id) {
        return customers.get(id);
    }

    public Reservation getReservation(final int id) {
        return reservations.get(id);
    }

    public Workspace getWorkspace(final int id) {
        return workspaces.get(id);
    }

    public void addCustomer(final Customer customer) {
        customers.put(customer.getId(), customer);
    }

    public void addReservation(final Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
    }

    public void addWorkspace(final Workspace workspace) {
        workspaces.put(workspace.getId(), workspace);
    }

    public Map<Integer, Customer> getAllCustomers() {
        return customers;
    }

    public Map<Integer, Reservation> getAllReservations() {
        return reservations;
    }

    public Map<Integer, Workspace> getAllWorkspaces() {
        return workspaces;
    }

    public void removeCustomer(final int id) {
        customers.remove(id);
    }

    public void removeReservation(final int id) {
        reservations.remove(id);
    }

    public void removeWorkspace(final int id) {
        workspaces.remove(id);
    }

    public static DataStorage getInstance() {
        return DataStorageHolder.DATA_STORAGE;
    }

    private static class DataStorageHolder {
        private static final DataStorage DATA_STORAGE = new DataStorage();
    }
}
