package org.example.controller;

import org.example.exception.EmptyListException;
import org.example.model.*;
import org.example.repository.DataStorage;
import org.example.repository.DataStorageSerialization;
import org.example.service.ReservationService;
import org.example.service.WorkspaceService;

import java.util.List;

public class CustomerController {
    private final WorkspaceService workspaceService;
    private final ReservationService reservationService;
    private final DataStorage dataStorage;
    private final DataStorageSerialization dataStorageSerialization;

    private CustomerController() {
        workspaceService = WorkspaceService.getInstance();
        reservationService = ReservationService.getInstance();
        dataStorage = DataStorage.getInstance();
        dataStorageSerialization = DataStorageSerialization.getInstance();
    }

    public static CustomerController getInstance() {
        return CustomerControllerHolder.CUSTOMER_CONTROLLER;
    }

    public List<Workspace> getAvailableWorkspaces() {
        List<Workspace> workspaces = workspaceService.getAvailableWorkspaces();
        if (workspaces.isEmpty()) {
            throw new EmptyListException("There are no available workspaces");
        }
        return workspaceService.getAvailableWorkspaces();
    }

    public void makeReservation(final Customer customer, final int idWorkspace, final String start, final String end){
        reservationService.makeReservation(customer, idWorkspace, start, end);
        dataStorageSerialization.save(dataStorage);
    }

    public void addCustomer() {
        dataStorage.addCustomer(new Customer());
        dataStorageSerialization.save(dataStorage);
    }

    public Customer getCustomer(final int id) {
        return dataStorage.getCustomer(id);
    }

    public List<Reservation> getReservationsByUserId(final int userId) {
        List<Reservation> reservations = reservationService.getReservationByUserId(userId);
        if (reservations.isEmpty()) {
            throw new EmptyListException("No reservations");
        }
        return reservationService.getReservationByUserId(userId);
    }

    public void cancelReservation(final int idReservation) {
        reservationService.cancelReservation(idReservation);
        dataStorageSerialization.save(dataStorage);
    }

    private static class CustomerControllerHolder {
        private static final CustomerController CUSTOMER_CONTROLLER = new CustomerController();
    }
}
