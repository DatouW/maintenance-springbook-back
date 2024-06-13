package com.group8.code.repository;

import com.group8.code.domain.Maintenance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface MaintenanceRepository extends MongoRepository<Maintenance, String> {
    Optional<List<Maintenance>> findAllByEmployeeId(String id);
    Optional<Maintenance> findByAppointmentId(String id);
    Optional<List<Maintenance>> findAllByStatusIn(List<String> status);
}
