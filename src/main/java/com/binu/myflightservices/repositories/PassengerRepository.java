package com.binu.myflightservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.binu.myflightservices.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

}
