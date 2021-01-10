package com.interview.api.services;

import java.util.Map;

public interface ParkingService {
	public  Map capacity();
	public String entry(String carId,  Boolean isReserved);
	public String exit(String carId);
}
