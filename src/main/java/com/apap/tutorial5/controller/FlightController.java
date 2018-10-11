package com.apap.tutorial5.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;

@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;

	@Autowired
	private PilotService pilotService;

	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = new PilotModel();

		List<FlightModel> kumpulanFlight = new ArrayList<FlightModel>();
		FlightModel flight = new FlightModel();

		kumpulanFlight.add(flight);
		pilot.setPilotFlight(kumpulanFlight);

		model.addAttribute("pilot", pilot);
		model.addAttribute("navbarinfo", "Add Flight");

		return "addFlight";

	}

	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params = { "addRow" })
	private String addRow(@PathVariable(value = "licenseNumber") String licenseNumber, @ModelAttribute PilotModel pilot,
			Model model) {

		FlightModel flight = new FlightModel();
		pilot.getPilotFlight().add(flight);

		model.addAttribute("licenseNumber", licenseNumber);
		model.addAttribute("pilot", pilot);
		return "addFlight";
	}

	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST)
	private String addFlightSubmit(@PathVariable(value = "licenseNumber") String licenseNumber,
			@ModelAttribute PilotModel newPilot, Model model) {
		PilotModel listPilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		for (int i = 0; i < newPilot.getPilotFlight().size(); i++) {
			FlightModel flight = newPilot.getPilotFlight().get(i);
			flight.setPilot(listPilot);
			flightService.addFlight(flight);
		}
		model.addAttribute("navbarinfo", "Info");
		return "add";
	}

	@RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
	private String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
		for (FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlight(flight.getId());
		}
		model.addAttribute("navbarinfo", "Delete Flight");
		return "delete";
	}

	@RequestMapping(value = "/flight/update/{id}", method = RequestMethod.GET)
	private String updateFlight(@PathVariable(value = "id") String id, Model model) {
		FlightModel archive = flightService.getFlightDetail(Long.parseLong(id));
		System.out.println(archive.getFlightNumber());
		model.addAttribute("flight", archive);
		model.addAttribute("navbarinfo", "Update Flight");
		return "updateFlight";
	}

	@RequestMapping(value = "/flight/update/{id}/succes", method = RequestMethod.POST)
	private String updateFlight(@PathVariable(value = "id") String id, @ModelAttribute FlightModel newFlight) {
		flightService.updateFlight(newFlight, id);
		return "update";

	}

	@RequestMapping("/flight/view/{id}")
	public String viewId(@PathVariable(value = "id") String id, Model model) {
		FlightModel archiveFlight = flightService.getFlightDetail(Long.parseLong(id));
		PilotModel archivePilot = archiveFlight.getPilot();
		model.addAttribute("navbarinfo", "View Flight");
		model.addAttribute("flight", archiveFlight);
		model.addAttribute("pilot", archivePilot);
		return "view-flight";
	}

}
