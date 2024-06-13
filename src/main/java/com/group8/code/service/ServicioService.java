package com.group8.code.service;

import com.group8.code.domain.Servicio;
import com.group8.code.dto.ServiceDto;
import java.util.List;

public interface ServicioService {
    List<Servicio> findAll();

    Servicio findById(String id);

    Servicio findByName(String name);

    Servicio create(ServiceDto serviceDto);

    Servicio update(String id, ServiceDto serviceDto);

    Servicio delete(String id);
}