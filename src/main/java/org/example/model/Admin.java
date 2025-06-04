package org.example.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("ADMIN")

public class Admin extends User {
    public Admin(String username, String password, Role role) {
        super(username, password, role);
    }

    public Admin() {
        super();
    }
}
