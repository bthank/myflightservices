package com.binu.myflightservices.integration.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.binu.myflightservices.dto.CreateReservationRequest;
import com.binu.myflightservices.entities.Flight;
import com.binu.myflightservices.entities.Passenger;
import com.binu.myflightservices.entities.Reservation;
import com.binu.myflightservices.repositories.FlightRepository;
import com.binu.myflightservices.repositories.PassengerRepository;
import com.binu.myflightservices.repositories.ReservationRepository;

@RestController	
public class ReservationRestController {
	
	@Autowired
	FlightRepository flightRepository;
	@Autowired
	PassengerRepository passengerRepository;
	@Autowired
	ReservationRepository reservationRepository;
	
	@RequestMapping(value="/flights",method=RequestMethod.GET)
	public List<Flight> findFlights(){
		
		return flightRepository.findAll();
	}
	
	@RequestMapping(value="/reservations",method=RequestMethod.POST)
	public Reservation saveReservation(CreateReservationRequest request) {
		
		Flight flight = flightRepository.findById(request.getFlightId()).get();
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setMiddleName(request.getPassengerMiddleName());
		passenger.setEmail(request.getPassengerEmail());
		passenger.setPhone(request.getPassengerPhone());
		
		// the passenger id will be automatically assigned by database when the record is saved
		Passenger savedPassenger = passengerRepository.save(passenger);
		
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		// when the reservation is initially created, the passenger is not checked in yet
		reservation.setCheckedIn(false);
		
		return reservationRepository.save(reservation);
		
	}

}
