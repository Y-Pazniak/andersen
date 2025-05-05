package org.example.service;

import org.example.exception.InvalidWorkspaceReservation;
import org.example.exception.WorkspaceUnavailableException;
import org.example.model.*;
import org.example.repository.DataStorage;
import org.example.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReservationService {
    private final DataStorage dataStorage;
    private final WorkspaceRepository workspaceRepository;

    @Autowired
    private ReservationService(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
        dataStorage = DataStorage.getInstance();
    }

    public List<Reservation> getAllReservations() {
        return dataStorage.getAllReservations().values().stream().toList();
    }

    public List<Reservation> getReservationByUserId(final int userId) {
        return dataStorage.getAllReservations().values().stream().filter(n -> n.getCustomer().getId() == userId && n.getStatus() == ReservationStatus.UNAVAILABLE).toList();
    }

    public void makeReservation(final Customer customer, final Long idWorkspace, final String start, final String end) {
        Workspace workspace = workspaceRepository.getReferenceById(idWorkspace);
        if (workspace.isAvailable()) {
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

}
