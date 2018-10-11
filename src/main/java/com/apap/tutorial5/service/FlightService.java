package com.apap.tutorial5.service;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;

public interface FlightService {
	FlightModel getFlightDetail(Long id);
	FlightModel getFlightDetailNumber(String flightNumber);
	void addFlight(FlightModel flight);
	void deleteFlight(Long id);
	void updateFlight(FlightModel newFlight, String id);
}
