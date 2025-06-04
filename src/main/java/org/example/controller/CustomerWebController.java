package org.example.controller;

import org.example.model.Customer;
import org.example.model.Reservation;
import org.example.model.Workspace;
import org.example.repository.CustomerRepository;
import org.example.service.ReservationService;
import org.example.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerWebController {
    private final WorkspaceService workspaceService;
    private final ReservationService reservationService;
    private final CustomerRepository userRepository;

    @Autowired
    public CustomerWebController(WorkspaceService workspaceService, ReservationService reservationService, CustomerRepository userRepository) {
        this.workspaceService = workspaceService;
        this.reservationService = reservationService;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "customer-dashboard";
    }

    @GetMapping("browse-available-workspaces")
    public String getAvailableWorkspaces(Model model) {
        List<Workspace> workspaces = workspaceService.getAvailableWorkspaces();
        model.addAttribute("workspaces", workspaces);
        return "browse-available-workspaces";
    }

    @GetMapping("make-reservation-form")
    public String makeReservationForm() {
        return "make-reservation-form";
    }

    @PostMapping("make-reservation")
    public String makeReservation(@RequestParam Long userId,
                                  @RequestParam Long workspaceId,
                                  @RequestParam String from,
                                  @RequestParam String till) {
        Customer customer = userRepository.findById(userId).orElseGet(() -> {
            Customer user = new Customer();
            userRepository.save(user);
            return user;
        });
        reservationService.makeReservation(customer, workspaceId, from, till);
        return "redirect:/customer/dashboard";
    }

    @GetMapping("view-my-reservations-form")
    public String viewReservationsForm(Model model) {
        List<Customer> customers = userRepository.findAll();
        model.addAttribute("customers", customers);
        return "view-my-reservations-form";
    }

    @GetMapping("view-my-reservations")
    public String viewReservations(@RequestParam Long userId, Model model) {
        List<Reservation> reservations = reservationService.getReservationByUserId(userId);
        model.addAttribute("reservations", reservations);
        return "view-my-reservations";
    }

    @GetMapping("cancel-my-reservation-form")
    public String cancelReservationFrom() {
        return "cancel-my-reservation-form";
    }

    @PostMapping("cancel-my-reservation")
    public String cancelReservation(@RequestParam Long userId, @RequestParam Long reservationId) {
        reservationService.cancelReservation(userId, reservationId);
        return "redirect:/customer/dashboard";
    }
}
