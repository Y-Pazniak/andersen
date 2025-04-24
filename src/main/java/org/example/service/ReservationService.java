package org.example.service;

import org.example.exception.InvalidWorkspaceReservation;
import org.example.exception.WorkspaceUnavailableException;
import org.example.model.*;
import org.example.repository.DataStorage;

import java.util.List;

public class ReservationService {
    private DataStorage dataStorage;

    private ReservationService() {
        this.dataStorage = DataStorage.getInstance();
    }

    public static ReservationService getInstance() {
        return ReservationServiceHolder.RESERVATION_SERVICE;
    }

    public List<Reservation> getAllReservations() {
        return dataStorage.getAllReservations().values().stream().toList();
    }

    public List<Reservation> getReservationByUserId(final int userId) {
        return dataStorage.getAllReservations().values().stream().filter(n -> n.getCustomer().getId() == userId && n.getStatus() == ReservationStatus.UNAVAILABLE).toList();
    }

    public void makeReservation(final Customer customer, final int idWorkspace, final String start, final String end) {
        Workspace workspace = dataStorage.getWorkspace(idWorkspace);
        if (workspace != null && workspace.isAvailable()) {
            Reservation reservation = new Reservation(customer, workspace, start, end);
            dataStorage.addReservation(reservation);
            workspace.setStatus(ReservationStatus.UNAVAILABLE);
            System.out.println(Message.SUCCESSFUL.getMessage());
        } else {
            throw new WorkspaceUnavailableException("Workspace is not available");
        }
    }

    public void cancelReservation(final int idReservation) {
        Reservation reservation = dataStorage.getReservation(idReservation);
        if (reservation != null) {
            reservation.cancel(idReservation);
            reservation.getWorkspace().setStatus(ReservationStatus.AVAILABLE);
            System.out.println(Message.SUCCESSFUL.getMessage());
        } else {
            throw new InvalidWorkspaceReservation("You have no reservation for this id: " + idReservation);
        }
    }

    void setDataStorage(final DataStorage mockStorage) {
        this.dataStorage = mockStorage;
    }

    void restoreDefaultStorage() {
        this.dataStorage = DataStorage.getInstance();
    }

    private static class ReservationServiceHolder {
        private static final ReservationService RESERVATION_SERVICE = new ReservationService();
    }
}
