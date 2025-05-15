package org.example.repository;

import org.example.model.Reservation;
import org.example.model.ReservationStatus;
import org.example.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
