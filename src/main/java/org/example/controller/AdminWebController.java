package org.example.controller;

import org.example.exception.InvalidCommandException;
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
        List<Workspace> workspaces = workspaceService.getAllWorkspaces();
        model.addAttribute("workspaces", workspaces);
        return "admin_dashboard";
    }

    @PostMapping("/add-workspace")
    public String addWorkspace(@RequestParam String type, @RequestParam int price) {
        workspaceService.addWorkspace(org.example.model.Type.valueOf(type.toUpperCase()), price);
        return "redirect:/admin/dashboard";
    }



}
