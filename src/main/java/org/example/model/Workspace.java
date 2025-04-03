package org.example.model;

public class Workspace {
    private final int id;
    private static int idCounter = 0;
    private final Type type;
    private int price;
    private ReservationStatus status;

    public Workspace(Type type, int price, ReservationStatus status) {
        this.id = ++idCounter;
        this.type = type;
        this.price = price;
        this.status = status;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public int getPrice() {
        return price;
    }

    public Type getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setPrice(final int price) {
        if (price > 0) {
            this.price = price;
        }
    }

    public void setStatus(final ReservationStatus status) {
        this.status = status;
    }

    public boolean isAvailable(){
        return this.status == ReservationStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return String.format("Workspace #%d [Type: %s, Price: %d, Status: %s, Available: %s]",
                id,
                type,
                price,
                status,
                isAvailable() ? "Yes" : "No");
    }
}
