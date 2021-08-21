package com.binu.myflightservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.binu.myflightservices.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}
