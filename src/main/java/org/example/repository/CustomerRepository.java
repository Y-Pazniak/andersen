package org.example.repository;

import org.example.model.Customer;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
