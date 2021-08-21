package com.binu.myflightservices.repositories;

 
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.binu.myflightservices.entities.Flight;

public interface FlightRepository extends JpaRepository<Flight, Integer> {

	// add a JPQL custom query
	@Query("from Flight where departureCity=:departureCity AND arrivalCity=:arrivalCity AND dateOfDeparture=:dateOfDeparture")
	List<Flight> findFlights(@Param("departureCity")String from,@Param("arrivalCity")String to, @Param("dateOfDeparture")Date departureDate);
	
	
	
}
