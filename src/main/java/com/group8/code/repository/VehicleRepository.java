package com.group8.code.repository;

import com.group8.code.domain.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends MongoRepository<Vehicle,String> {
    Optional<List<Vehicle>> findAllByCustomerId(String id);
    Optional<Vehicle> findByLicensePlateOrVin(String plate,String vin);
}
