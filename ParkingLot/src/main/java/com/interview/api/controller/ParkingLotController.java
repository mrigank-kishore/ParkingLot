package com.interview.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.api.services.ParkingService;

@RestController
@RequestMapping(value= "/api/car-park")
public class ParkingLotController {

	@Autowired
	ParkingService parkingService;
	
	@PostMapping
	public String entry(String carNumber, Boolean is) {
		return parkingService.entry(carNumber, is);
	}
	
	@DeleteMapping
	public String exit(String carNumber) {
		return parkingService.exit(carNumber);
	}
	
	@GetMapping
	public Map capicity() {
		return parkingService.capacity();
	}
}
