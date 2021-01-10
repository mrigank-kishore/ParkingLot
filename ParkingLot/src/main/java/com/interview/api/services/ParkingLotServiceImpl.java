package com.interview.api.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ParkingLotServiceImpl implements ParkingService {
	
	private final static int ALL_PARKING_LOT_SIZE = 120;  //initial parking lot size
	private final static int RESERVED_PARKING_LOT_SIZE = (int) Math.round(ALL_PARKING_LOT_SIZE * 0.2); //20% of the parking lots are reserved parking
	private final static int GENERAL_PARKING_LOT_SIZE = (int) Math.round(ALL_PARKING_LOT_SIZE * 0.8); //80% of the parking are general parking
	
	private static Map<String, Boolean> parkGeneral = new HashMap<>();
	private static Map<String, Boolean> parkReserved = new HashMap<>();
	
	public synchronized Map capacity() {
		Map<String, Integer> cap = new HashMap<>();
		cap.put("Reserved available: ", RESERVED_PARKING_LOT_SIZE - parkReserved.size());
		cap.put("General available: ", GENERAL_PARKING_LOT_SIZE - parkGeneral.size());
		return cap;
	}
	
	public synchronized String entry(String carId,  Boolean isReserved) {
		if(isReserved) {
			if(parkReserved.size() < RESERVED_PARKING_LOT_SIZE) { 
				parkReserved.put(carId.toLowerCase(), isReserved); //allocate reserved parking to reserved requester, lowercase for simplify search
				return "allocated reserved parking";
			}else{
				if(parkGeneral.size() < GENERAL_PARKING_LOT_SIZE) {
					parkGeneral.put(carId.toLowerCase(), isReserved); //allocate general in case reserved not available
					return "reserved full, allocating general";
				}else {
					return "parking full"; //if general and reserved both full then parking is fully occupied
				}
			}
		}	else {
			if(parkGeneral.size() < GENERAL_PARKING_LOT_SIZE) {
				parkGeneral.put(carId.toLowerCase(), isReserved); //allocating general parking if available, lowercase for simplify search
				return "general parking available";
			}else {
				return "parking full";
			}
		}
	}
	
	public synchronized String exit(String carId) {
		//check both reserved and general parking lot to check whether car exists and then de-allocate
		if(parkGeneral.containsKey(carId.toLowerCase())) {
			parkGeneral.remove(carId.toLowerCase());
			return "exit from general parking space";
		}
		if(parkReserved.containsKey(carId.toLowerCase())) {
			parkReserved.remove(carId.toLowerCase());
			return "exit from reserved parking space";
		}
		return "incorrect car id, does not exist";
	}

}
