package com.apap.tutorial5.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.repository.PilotDb;

@Service
@Transactional
public class PilotServiceImpl implements PilotService {
	@Autowired
	private PilotDb pilotDb;
	
	@Override 
	public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
		return pilotDb.findByLicenseNumber(licenseNumber);
	}
	
	@Override
	public void addPilot (PilotModel pilot) {
		pilotDb.save(pilot);
	}
	
	@Override
	public void deletePilot (Long id) {
		PilotModel pilot =pilotDb.findById(id).get();
		pilotDb.delete(pilot);
		
	}
	
	@Override
	public void updatePilot (PilotModel newPilot, String licenseNumber) {
		PilotModel oldPilot = getPilotDetailByLicenseNumber(licenseNumber);
		oldPilot.setName(newPilot.getName());
		oldPilot.setFlyHour(newPilot.getFlyHour());
		
	}
	
	
	
	
}
