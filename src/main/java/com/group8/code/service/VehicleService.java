package com.group8.code.service;

import com.group8.code.domain.Vehicle;
import com.group8.code.dto.VehicleDto;
import java.util.List;

public interface VehicleService {
    List<Vehicle> findAll();

    List<Vehicle> findAllByCustomer(String id);

    Vehicle findById(String id);

    Vehicle create(VehicleDto vehicleDto);

    Vehicle update(String id, VehicleDto vehicleDto);

    Vehicle delete(String id);
}