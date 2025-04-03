package org.example.model;

public class Reservation {
    private static int idCounter = 0;
    private final Customer customer;
    private final int id;
    private final Workspace workspace;
    private ReservationStatus status;
    private final String startReservation;
    private final String endReservation;

    public Reservation(final Customer customer, final Workspace workspace, final String startReservation, final String endReservation) {
        this.customer = customer;
        this.id = ++idCounter;
        this.workspace = workspace;
        this.startReservation = startReservation;
        this.endReservation = endReservation;
        status = ReservationStatus.UNAVAILABLE;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public String getStartReservation() {
        return startReservation;
    }

    public String getEndReservation() {
        return endReservation;
    }

    public int getId() {
        return id;
    }

    public void cancel(final int id) {
        if (this.id == id) {
            this.status = ReservationStatus.AVAILABLE;
        }
    }

    @Override
    public String toString() {
        return String.format("Reservation #%d [Customer: %s, Workspace: %s, Status: %s, Start: %s, End: %s]",
                id,
                customer.getId(),
                workspace.getType(),
                status,
                startReservation,
                endReservation);
    }
}
