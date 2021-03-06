package com.binu.myflightservices.integration.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.binu.myflightservices.dto.CreateReservationRequest;
import com.binu.myflightservices.dto.UpdateReservationRequest;
import com.binu.myflightservices.entities.Flight;
import com.binu.myflightservices.entities.Passenger;
import com.binu.myflightservices.entities.Reservation;
import com.binu.myflightservices.repositories.FlightRepository;
import com.binu.myflightservices.repositories.PassengerRepository;
import com.binu.myflightservices.repositories.ReservationRepository;

@RestController
//use this annotation to prevent cross site origin errors since backend and front end run on different ports
//@CrossOrigin(origins="http://localhost:3000")  
public class ReservationRestController {

	@Autowired
	FlightRepository flightRepository;
	@Autowired
	PassengerRepository passengerRepository;
	@Autowired
	ReservationRepository reservationRepository;

	/**
	 * Method that returns all flights matching criteria entered
	 * @param from
	 * @param to
	 * @param departureDate
	 * @return list of flights
	 */
	@RequestMapping(value = "/flights", method = RequestMethod.GET)
	@CrossOrigin
	public List<Flight> findFlights(@RequestParam("from")String from, 
			@RequestParam("to")String to, 
			@RequestParam("departureDate") @DateTimeFormat(pattern="MM-dd-yyyy") Date departureDate) {

		return flightRepository.findFlights(from, to, departureDate);
	}
	
	@RequestMapping(value="/flights/{id}", method=RequestMethod.GET)
	@CrossOrigin
	public Flight findFlight(@PathVariable("id")int id) {
		
		return flightRepository.findById(id).get();
		
	}
	
	
	/**
	 * Method that saves a reservation
	 * @param request
	 * @return Reservation
	 */
	@RequestMapping(value = "/reservations", method = RequestMethod.POST)
	@Transactional // this will cause a rollback if one of the saves fails; all or none
	//@RequestBody is needed so that Spring will serialize the incoming JSON request into this Java object 
	public Reservation saveReservation(@RequestBody CreateReservationRequest request) {

		Flight flight = flightRepository.findById(request.getFlightId()).get();
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setMiddleName(request.getPassengerMiddleName());
		passenger.setEmail(request.getPassengerEmail());
		passenger.setPhone(request.getPassengerPhone());

		// the passenger id will be automatically assigned by database when the record
		// is saved
		Passenger savedPassenger = passengerRepository.save(passenger);

		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		// when the reservation is initially created, the passenger is not checked in
		// yet
		reservation.setCheckedIn(false);

		return reservationRepository.save(reservation);

	}

	/**
	 * Method to find a reservation by id
	 * @param id
	 * @return Reservation
	 */
	@RequestMapping(value = "/reservations/{id}", method = RequestMethod.GET)
	public Reservation findReservation(@PathVariable("id") int id) {

		return reservationRepository.findById(id).get();
	}
	
	@RequestMapping(value="/reservations",method=RequestMethod.PUT)
	public Reservation updateReservation(@RequestBody UpdateReservationRequest request) {
		
		Reservation reservation = reservationRepository.findById(request.getReservationId()).get();
		reservation.setNumberOfBags(request.getNumberOfBags());
		reservation.setCheckedIn(request.getCheckInFlag());
		
		// save the reservation and return the saved reservation to caller
		return reservationRepository.save(reservation);
		
	}

}
