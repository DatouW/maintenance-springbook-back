package com.group8.code.repository;

import com.group8.code.domain.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends MongoRepository<Appointment,String> {
    Optional<List<Appointment>> findAllByCustomerId(String customerId);
    Optional<List<Appointment>> findAllByStatus(String status);
}
