package com.apap.tutorial5.controller;

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
import com.apap.tutorial5.service.PilotService;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;

	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("navbarinfo", "APAP");
		return "home";
	}

	@RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("pilot", new PilotModel());
		model.addAttribute("navbarinfo", "Add Pilot");
		return "addPilot";
	}

	@RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot, Model model) {
		pilotService.addPilot(pilot);
		model.addAttribute("navbarinfo", "Add Pilot");
		return "add";
	}

	@RequestMapping(value = "/pilot/view", method = RequestMethod.GET)
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		List<FlightModel> flight = archive.getPilotFlight();

		model.addAttribute("pilot", archive);
		model.addAttribute("flight", flight);
		model.addAttribute("navbarinfo", "View Pilot");
		return "view-pilot";

	}

	@RequestMapping(value = "/pilot/delete/{id}", method = RequestMethod.GET)
	private String deletePilot(@PathVariable(value = "id") String id, Model model) {
		model.addAttribute("navbarinfo", "Delete Pilot");
		pilotService.deletePilot(Long.parseLong(id));
		return "delete";
	}

	@RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.GET)
	private String updatePilot(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {

		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("pilot", archive);
		model.addAttribute("navbarinfo", "Update Pilot");
		return "updatePilot";
	}

	@RequestMapping(value = "/pilot/update/{licenseNumber}/succes", method = RequestMethod.POST)
	private String updatePilotSubmit(@PathVariable(value = "licenseNumber") String licenseNumber,
			@ModelAttribute PilotModel newPilot, Model model) {
		pilotService.updatePilot(newPilot, licenseNumber);
		model.addAttribute("navbarinfo", "Update Pilot");
		return "update";
	}

}
