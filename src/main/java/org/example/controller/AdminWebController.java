package org.example.controller;

import org.example.exception.InvalidCommandException;
import org.example.model.Command;
import org.example.model.Reservation;
import org.example.model.Type;
import org.example.model.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.example.service.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminWebController {
    private final WorkspaceService workspaceService;
    private final ReservationService reservationService;

    @Autowired
    public AdminWebController(WorkspaceService workspaceService, ReservationService reservationService) {
        this.workspaceService = workspaceService;
        this.reservationService = reservationService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "admin-dashboard";
    }

    @GetMapping("/add-workspace-form")
    public String addWorkspaceForm(Model model) {
        model.addAttribute("availableRoomTypes", Type.values());
        return "add-workspace-form";
    }

    @GetMapping("/delete-workspace-form")
    public String deleteWorkspaceForm(Model model) {
        model.addAttribute("availableRooms", workspaceService.getAllWorkspaces());
        return "delete-workspace-form";
    }

    @GetMapping("/view-reservations")
    public String viewReservations(Model model) {
        List<Reservation> reservations = reservationService.getAllReservations();
        model.addAttribute("reservations", reservations);
        return "view-reservations";
    }

    @PostMapping("/add-workspace")
    public String addWorkspace(@RequestParam String type, int price) {
        workspaceService.addWorkspace(Type.valueOf(type), price);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete-workspace")
    public String deleteWorkspace(@RequestParam int id) {
        workspaceService.removeWorkspace(workspaceService.getAllWorkspaces().stream().filter(w -> w.getId() == id).findFirst().get());
        return "redirect:/admin/dashboard";
    }
}
