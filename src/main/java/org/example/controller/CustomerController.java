package org.example.controller;

import org.example.exception.EmptyListException;
import org.example.exception.InvalidPriceException;
import org.example.model.*;
import org.example.repository.CustomerRepository;
import org.example.service.ReservationService;
import org.example.service.WorkspaceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerController {
    private final WorkspaceService workspaceService;
    private final ReservationService reservationService;
    private final CustomerRepository userRepository;

    private CustomerController(WorkspaceService workspaceService, ReservationService reservationService, CustomerRepository userRepository) {
        this.workspaceService = workspaceService;
        this.reservationService = reservationService;
        this.userRepository = userRepository;
    }

    public List<Workspace> getAvailableWorkspaces() {
        List<Workspace> workspaces = workspaceService.getAvailableWorkspaces();
        if (workspaces.isEmpty()) {
            throw new EmptyListException("There are no available workspaces");
        }
        return workspaceService.getAvailableWorkspaces();
    }
    public void makeReservation(final Customer customer, final Long idWorkspace, final String start, final String end){
        userRepository.save(customer);
    }

    public void addCustomer() {
        userRepository.save(new Customer());
    }

    public Customer getCustomer(final Long id) {
        Optional<Customer> customer = userRepository.findById(id);
        return customer.orElse(null);
    }

    public List<Reservation> getReservationsByUserId(final Long userId) {
        List<Reservation> reservations = reservationService.getReservationByUserId(userId);
        if (reservations.isEmpty()) {
            throw new EmptyListException("No reservations");
        }
        return reservationService.getReservationByUserId(userId);
    }

    public void cancelReservation(final Long idReservation) {
        reservationService.cancelReservation(idReservation);
    }

    public Workspace getWorkspaceCheaperThan(final int price) {
        Optional<Workspace> workspace = workspaceService.getWorkspaceCheaperThan(price);
        return workspace.orElseThrow(() -> new InvalidPriceException(price + " - no such price for rooms"));
    }
}
