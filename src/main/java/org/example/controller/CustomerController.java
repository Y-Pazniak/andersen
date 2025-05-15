package org.example.controller;

import org.example.exception.EmptyListException;
import org.example.model.*;
import org.example.repository.DataStorage;
import org.example.service.ReservationService;
import org.example.service.WorkspaceService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerController {
    private final WorkspaceService workspaceService;
    private final ReservationService reservationService;
    private final DataStorage dataStorage;

    private CustomerController(WorkspaceService workspaceService, ReservationService reservationService) {
        this.workspaceService = workspaceService;
        this.reservationService = reservationService;
        dataStorage = DataStorage.getInstance();
    }

    public List<Workspace> getAvailableWorkspaces() {
        List<Workspace> workspaces = workspaceService.getAvailableWorkspaces();
        if (workspaces.isEmpty()) {
            throw new EmptyListException("There are no available workspaces");
        }
        return workspaceService.getAvailableWorkspaces();
    }

    public void makeReservation(final Customer customer, final Long idWorkspace, final String start, final String end){
        reservationService.makeReservation(customer, idWorkspace, start, end);
    }

    public void addCustomer() {
        dataStorage.addCustomer(new Customer());
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

    public void cancelReservation(final Long idReservation) {
        reservationService.cancelReservation(idReservation);
    }
}
