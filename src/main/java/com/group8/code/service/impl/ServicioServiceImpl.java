package com.group8.code.service.impl;

import com.group8.code.domain.Servicio;
import com.group8.code.domain.Vehicle;
import com.group8.code.dto.Pagination;
import com.group8.code.dto.ServiceDto;
import com.group8.code.repository.ServiceRepository;
import com.group8.code.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioServiceImpl implements ServicioService {
    @Autowired
    private ServiceRepository serviceRepository;
//    @Autowired
//    private RestClient restClient;

    @Override
    public List<Servicio> findAll() {
        System.out.println("finda ll list service");
        return serviceRepository.findAll();
    }

    @Override
    public Pagination<Servicio> findAll(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        System.out.println("finda ll list service");
        Page<Servicio> all = serviceRepository.findAll(pageable);
        return new Pagination<>(all.getTotalPages(),all.getContent());
    }

    @Override
    public Servicio findById(String id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id no existe"));
    }

    @Override
    public Servicio findByName(String name) {
        return serviceRepository.findByName(name).orElse(null);
    }

    @Override
    public Servicio create(ServiceDto serviceDto) {
        Servicio service = Servicio.builder()
                .name(serviceDto.getName())
                .description(serviceDto.getDescription())
                .build();
        Servicio servicio = null;
        try {
            servicio = serviceRepository.save(service);
//            restClient.post("/service/register", servicio, String.class);
        } catch (Exception e) {
            if (servicio != null) {
                serviceRepository.delete(servicio); // rollback
            }
            throw new RuntimeException("Error registering servicio remotely: " + e.getMessage(), e);
        }
        return servicio;
    }

    @Override
    public Servicio update(String id, ServiceDto serviceDto) {
        Servicio service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id not existe"));

        service.setDescription(serviceDto.getDescription());
        service.setName(serviceDto.getName());

        return serviceRepository.save(service);
    }

    @Override
    public Servicio delete(String id) {
        Servicio service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id no existe"));
        serviceRepository.delete(service);
        try {
//            restClient.delete("/service/"+id);
        } catch (Exception e) {
            if (service != null) {
                serviceRepository.save(service); // rollback
            }
            throw new RuntimeException("Error deleting vehicle remotely: " + e.getMessage(), e);
        }

        return service;
    }
}