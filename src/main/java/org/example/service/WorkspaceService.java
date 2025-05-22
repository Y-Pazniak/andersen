package org.example.service;

import org.example.model.*;
import org.example.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;

    @Autowired
    private WorkspaceService(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    public void addWorkspace(final Type type, final int price) {
        workspaceRepository.save(new Workspace(type, price, ReservationStatus.AVAILABLE));
    }

    public void removeWorkspace(final Workspace workspace) {
        if (workspace != null) {
            workspaceRepository.delete(workspace);
            System.out.println(Message.SUCCESSFUL);
        } else {
            System.out.println(Message.NOT_SUCCESSFUL);
        }
    }

    public void updateWorkspace(final int id, final int newPrice, final ReservationStatus newStatus) {
        Optional<Workspace> workspaceOptional = workspaceRepository.findById((long) id);
        if (workspaceOptional.isPresent()) {
            Workspace workspace = workspaceOptional.get();
            workspace.setPrice(newPrice);
            workspace.setStatus(newStatus);
            workspaceRepository.save(workspace);
            System.out.println(Message.SUCCESSFUL);
        }
        System.out.println(Message.NOT_SUCCESSFUL);
    }

    public List<Workspace> getAvailableWorkspaces() {
        return workspaceRepository.findAll().stream().filter(n -> n.isAvailable()).toList();
    }

    public List<Workspace> getAllWorkspaces() {
        return workspaceRepository.findAll();
    }

    public List<Workspace> getAllWorkspaces() {
        //here we use stream to convert values from a map to a list
        return dataStorage.getAllWorkspaces().values().stream().toList();
    }

    public Optional<Workspace> getWorkspaceCheaperThan(final int price) {
        //here we use Stream and Optional to get a workspace
        return getAllWorkspaces().stream().filter(n -> n.getPrice() < price).findFirst();
    }

    public static WorkspaceService getInstance() {
        return WorkspaceServiceHolder.WORKSPACE_SERVICE;
    }
  
}
