package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@ToString

public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer customer;

    @Getter
    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @Getter
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private String startReservation;
    private String endReservation;

    public Reservation(final Customer customer, final Workspace workspace, final String startReservation, final String endReservation) {
        this.customer = customer;
        this.workspace = workspace;
        this.startReservation = startReservation;
        this.endReservation = endReservation;
        this.status = ReservationStatus.UNAVAILABLE;
    }

    public void cancel(final Long id) {
        if (Objects.equals(this.id, id)) {
            this.status = ReservationStatus.AVAILABLE;
        }
    }
}
