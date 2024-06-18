package com.group8.code.repository;

import com.group8.code.domain.Appointment;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends MongoRepository<Appointment,String> {
    Optional<List<Appointment>> findAllByCustomerId(ObjectId customerId);
    Optional<List<Appointment>> findAllByStatus(String status);
    Page<Appointment> findAllByCustomerId(ObjectId customerId, Pageable pageable);
    Page<Appointment> findAllByStatus(String status,Pageable pageable);
}
