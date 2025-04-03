package org.example.service;

import org.example.model.*;
import org.example.repository.DataStorage;

import java.util.List;

public class ReservationService {
    private final DataStorage dataStorage;

    private ReservationService() {
        dataStorage = DataStorage.getInstance();
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
            System.out.println(Message.SUCCESSFUL);
        } else {
            System.out.println(Message.NOT_SUCCESSFUL);
        }
    }

    public void cancelReservation(final int idReservation) {
        Reservation reservation = dataStorage.getReservation(idReservation);
        if (reservation != null) {
            reservation.cancel(idReservation);
            reservation.getWorkspace().setStatus(ReservationStatus.AVAILABLE);
            System.out.println(Message.SUCCESSFUL);
        } else {
            System.out.println(Message.NOT_SUCCESSFUL);
        }
    }

    private static class ReservationServiceHolder {
        private static final ReservationService RESERVATION_SERVICE = new ReservationService();
    }
}
