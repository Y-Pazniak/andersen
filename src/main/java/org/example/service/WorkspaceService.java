package org.example.service;

import org.example.model.*;
import org.example.repository.DataStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkspaceService {
    private final DataStorage dataStorage;

    private WorkspaceService() {
        dataStorage = DataStorage.getInstance();
    }

    public void addWorkspace(final Type type, final int price) {
        dataStorage.addWorkspace(new Workspace(type, price, ReservationStatus.AVAILABLE));
    }

    public void removeWorkspace(final Workspace workspace) {
        if (workspace != null) {
            dataStorage.removeWorkspace(workspace.getId());
            System.out.println(Message.SUCCESSFUL);
        } else {
            System.out.println(Message.NOT_SUCCESSFUL);
        }
    }

    public void updateWorkspace(final int id, final int newPrice, final ReservationStatus newStatus) {
        Workspace workspace = dataStorage.getWorkspace(id);
        if (workspace != null) {
            workspace.setPrice(newPrice);
            workspace.setStatus(newStatus);
            System.out.println(Message.SUCCESSFUL);
        }
        System.out.println(Message.NOT_SUCCESSFUL);
    }

    public List<Workspace> getAvailableWorkspaces() {
        return dataStorage.getAllWorkspaces().values().stream().filter(n -> n.isAvailable()).toList();
    }

    public List<Workspace> getAllWorkspaces() {
        return dataStorage.getAllWorkspaces().values().stream().toList();
    }

    public static WorkspaceService getInstance() {
        return WorkspaceServiceHolder.WORKSPACE_SERVICE;
    }

    private static class WorkspaceServiceHolder {
        private static final WorkspaceService WORKSPACE_SERVICE = new WorkspaceService();
    }
}
