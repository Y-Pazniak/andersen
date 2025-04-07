package org.example.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Customer extends User  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1l;
    public Customer() {
        super();
    }
}
