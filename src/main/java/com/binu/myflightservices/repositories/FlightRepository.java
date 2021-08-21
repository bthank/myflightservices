package com.binu.myflightservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.binu.myflightservices.entities.Flight;

public interface FlightRepository extends JpaRepository<Flight, Integer> {

}
