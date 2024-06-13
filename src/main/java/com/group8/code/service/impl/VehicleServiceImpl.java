package com.group8.code.service.impl;

import com.group8.code.domain.User;
import com.group8.code.domain.Vehicle;
import com.group8.code.dto.VehicleDto;
import com.group8.code.repository.UserRepository;
import com.group8.code.repository.VehicleRepository;
import com.group8.code.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private RestClient restClient;

    @Override
    public List<Vehicle> findAll() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        vehicles.forEach(this::addCustomerInfo);
        return vehicles;
    }

    @Override
    public List<Vehicle> findAllByCustomer(String id) {
        return vehicleRepository.findAllByCustomerId(id).orElse(new ArrayList<>());
    }

    @Override
    public Vehicle findById(String id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id no existe"));
        addCustomerInfo(vehicle);
        return vehicle;
    }

    @Override
    public Vehicle create(VehicleDto vehicleDto) {
        Vehicle vehicle1 = vehicleRepository.findByLicensePlateOrVin(vehicleDto.getLicensePlate(), vehicleDto.getVin()).orElse(null);
        if (vehicle1 != null) {
            throw new RuntimeException("El numero de placa o VIN ya se encuentra registrado");
        }
        userRepository.findById(vehicleDto.getCustomerId()).orElseThrow(() -> new RuntimeException("No existe el cliente"));
        Vehicle vehicle = Vehicle.builder()
                .licensePlate(vehicleDto.getLicensePlate())
                .brand(vehicleDto.getBrand())
                .model(vehicleDto.getModel())
                .year(vehicleDto.getYear())
                .vin(vehicleDto.getVin())
                .customerId(vehicleDto.getCustomerId())
                .build();
        Vehicle savedVehicle = null;
        try {
            savedVehicle = vehicleRepository.save(vehicle);
            addCustomerInfo(savedVehicle);
//            restClient.post("/vehicle/register", vehicle, String.class);
        } catch (Exception e) {
            if (savedVehicle != null) {
                vehicleRepository.delete(savedVehicle); // rollback
            }
            throw new RuntimeException("Error registering vehicle remotely: " + e.getMessage(), e);
        }
        return savedVehicle;
    }

    @Override
    public Vehicle update(String id, VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id not existe"));

        vehicle.setLicensePlate(vehicleDto.getLicensePlate());
        vehicle.setBrand(vehicleDto.getBrand());
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setYear(vehicleDto.getYear());
        vehicle.setVin(vehicleDto.getVin());
        vehicle.setCustomerId(vehicleDto.getCustomerId());
        vehicleRepository.save(vehicle);
        addCustomerInfo(vehicle);

        return vehicle;
    }

    @Override
    public Vehicle delete(String id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id no existe"));
        vehicleRepository.delete(vehicle);
        try {
//            restClient.delete("/vehicle/"+vehicle.getVin());
        } catch (Exception e) {
            if (vehicle != null) {
                vehicleRepository.save(vehicle); // rollback
            }
            throw new RuntimeException("Error deleting vehicle remotely: " + e.getMessage(), e);
        }
        return vehicle;
    }

    private void addCustomerInfo(Vehicle vehicle) {
        if (vehicle != null) {
            User customer = userRepository.findById(vehicle.getCustomerId()).orElseThrow(() -> new RuntimeException("No existe el cliente"));
            vehicle.setCustomer(customer);
        }
    }
}