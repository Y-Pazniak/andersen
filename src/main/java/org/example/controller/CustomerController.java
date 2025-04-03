package org.example.controller;

import org.example.model.*;
import org.example.repository.DataStorage;
import org.example.service.ReservationService;
import org.example.service.WorkspaceService;

import java.util.List;

public class CustomerController {
    private final WorkspaceService workspaceService;
    private final ReservationService reservationService;
    private final DataStorage dataStorage;

    private CustomerController() {
        workspaceService = WorkspaceService.getInstance();
        reservationService = ReservationService.getInstance();
        dataStorage = DataStorage.getInstance();
    }

    public static CustomerController getInstance() {
        return CustomerControllerHolder.CUSTOMER_CONTROLLER;
    }

    public List<Workspace> getAvailableWorkspaces() {
        return workspaceService.getAvailableWorkspaces();
    }

    public void addCustomer() {
        dataStorage.addCustomer(new Customer());
    }

    public Customer getCustomer(final int id) {
        return dataStorage.getCustomer(id);
    }

    public List<Reservation> getReservationByUserId(final int userId) {
        return reservationService.getReservationByUserId(userId);
    }

    public void cancelReservation(final int idReservation) {
        reservationService.cancelReservation(idReservation);
    }

    private static class CustomerControllerHolder {
        private static final CustomerController CUSTOMER_CONTROLLER = new CustomerController();
    }
}
