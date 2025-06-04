package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Workspace implements Serializable {
    @Serial
    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Type type;

    private int price;

    @Setter
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Reservation> reservations = new ArrayList<>();

    public Workspace(Type type, int price, ReservationStatus status) {
        this.type = type;
        this.price = price;
        this.status = status;
    }

    public void setPrice(final int price) {
        if (price > 0) {
            this.price = price;
        }
    }

    public boolean isAvailable() {
        return this.status == ReservationStatus.AVAILABLE;
    }
}
