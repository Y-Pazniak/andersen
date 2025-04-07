package org.example.controller;

import org.example.exception.EmptyListException;
import org.example.exception.InvalidCommandException;
import org.example.exception.InvalidPriceException;
import org.example.exception.InvalidRoomTypeException;
import org.example.model.Message;
import org.example.model.Reservation;
import org.example.model.Type;
import org.example.model.Workspace;
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
        if (!isValidType(type)) {
            throw new InvalidRoomTypeException("Invalid roomtype: " + type);
        } else {
            if (price < 0) {
                throw new InvalidPriceException("Price can not be below zero: " + price);
            } else {
                workspaceService.addWorkspace(Type.valueOf(type.toUpperCase()), price);
                System.out.println(Message.SUCCESSFUL);
            }
        }
    }

    public Workspace getWorkspaceById(final int id) {
        return workspaceService.getAllWorkspaces()
                .stream()
                .filter(n -> n.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Workspace> getAllWorkspaces(){
        return workspaceService.getAllWorkspaces();
    }

    public void removeWorkspace(final int id) {
        List<Workspace> workspaceList = workspaceService.getAllWorkspaces();
        if (workspaceList.isEmpty() || !workspaceList.contains(getWorkspaceById(id))) {
            throw new InvalidCommandException("Wrong id or no working places");
        }
        workspaceService.removeWorkspace(
                workspaceService.getAllWorkspaces()
                        .stream()
                        .filter(n -> n.getId() == id)
                        .findFirst()
                        .orElse(null));
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> reservationList = reservationService.getAllReservations();
        if (reservationList.isEmpty()) {
            throw new EmptyListException("Reservation list is empty");
        } else {
            return reservationList;
        }
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
