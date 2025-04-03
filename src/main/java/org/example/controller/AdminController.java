package org.example.controller;

import org.example.model.Message;
import org.example.model.Reservation;
import org.example.model.Type;
import org.example.service.ReservationService;
import org.example.service.WorkspaceService;

import java.util.List;

public class AdminController {
    private final WorkspaceService workspaceService;
    private final ReservationService reservationService;

    private AdminController() {
        workspaceService = WorkspaceService.getInstance();
        reservationService = ReservationService.getInstance();
    }

    public static AdminController getInstance() {
        return AdminControllerHolder.ADMIN_CONTROLLER;
    }

    public void addWorkspace(final String type, final int price) {
        if (isValidType(type) && price > 0) {
            workspaceService.addWorkspace(Type.valueOf(type.toUpperCase()), price);
            System.out.println(Message.SUCCESSFUL);
        } else {
            System.out.println(Message.NOT_SUCCESSFUL);
        }
    }

    public void removeWorkspace(final int id) {
        workspaceService.removeWorkspace(
                workspaceService.getAllWorkspaces()
                        .stream()
                        .filter(n -> n.getId() == id)
                        .findFirst()
                        .orElse(null));
    }

    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    private boolean isValidType(final String type) {
        try {
            Type.valueOf(type.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static class AdminControllerHolder {
        private static final AdminController ADMIN_CONTROLLER = new AdminController();
    }
}
