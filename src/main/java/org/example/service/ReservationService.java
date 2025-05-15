package org.example.service;

import org.example.exception.InvalidWorkspaceReservation;
import org.example.exception.WorkspaceUnavailableException;
import org.example.model.*;
import org.example.repository.ReservationRepository;
import org.example.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final WorkspaceRepository workspaceRepository;

    @Autowired
    private ReservationService(WorkspaceRepository workspaceRepository, ReservationRepository reservationRepository) {
        this.workspaceRepository = workspaceRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll().stream().toList();
    }

    public List<Reservation> getReservationByUserId(final int userId) {
        return reservationRepository.findAll().stream().filter(n -> n.getCustomer().getId() == userId && n.getStatus() == ReservationStatus.UNAVAILABLE).toList();

    }

    public void makeReservation(final Customer customer, final Long idWorkspace, final String start, final String end) {
        Optional<Workspace> workspaceOptional = workspaceRepository.findById(idWorkspace);
        if (workspaceOptional.isPresent()) {
            Workspace workspace = workspaceOptional.get();
            Reservation reservation = new Reservation(customer, workspace, start, end);
            reservationRepository.save(reservation);
            workspace.setStatus(ReservationStatus.UNAVAILABLE);
            System.out.println(Message.SUCCESSFUL.getMessage());
        } else {
            throw new WorkspaceUnavailableException("Workspace is not available");
        }
    }

    public void cancelReservation(final Long idReservation) {
        Reservation reservation = reservationRepository.getReferenceById(idReservation);
        reservation.cancel(idReservation);
        reservation.getWorkspace().setStatus(ReservationStatus.AVAILABLE);
        System.out.println(Message.SUCCESSFUL.getMessage());
    }

}
