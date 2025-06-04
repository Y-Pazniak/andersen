package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CUSTOMER")
@Getter
@ToString
public class Customer extends User {
    public Customer(String username, String password, Role role) {
        super(username, password, role);
    }

    public Customer() {
        super();
    }

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();
}
