package com.group8.code.repository;

import com.group8.code.domain.Servicio;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ServiceRepository extends MongoRepository<Servicio,String> {
    Optional<Servicio> findByName(String name);
}
