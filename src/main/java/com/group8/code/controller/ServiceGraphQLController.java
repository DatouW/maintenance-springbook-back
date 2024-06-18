package com.group8.code.controller;

import com.group8.code.domain.Servicio;

import com.group8.code.dto.Pagination;
import com.group8.code.dto.ServiceDto;
import com.group8.code.service.ServicioService;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;


import java.util.List;

@Controller
@Validated
public class ServiceGraphQLController {
    @Autowired
    private ServicioService servicioService;

    @PreAuthorize("hasAnyAuthority('service/view','Ver Servicios','Ver Citas de Mantenimiento','Ver Mantenimientos')")
    @QueryMapping
    public List<Servicio> services(){
        return servicioService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('service/view','Ver Servicios','Ver Citas de Mantenimiento','Ver Mantenimientos')")
    @QueryMapping
    public Pagination<Servicio> servicesPag(@Argument int offset, @Argument int limit){
        return servicioService.findAll(offset,limit);
    }

    @PreAuthorize("hasAnyAuthority('service/view','Ver Servicios','Ver Citas de Mantenimiento')")
    @QueryMapping
    public Servicio service(@Argument String id){
        return servicioService.findById(id);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('service/create','Registrar Servicios')")
    public Servicio createService(@Argument @Valid ServiceDto servicioDto){
        //System.out.println(servicioDto);
        Servicio servicio = servicioService.findByName(servicioDto.getName());
        if(servicio == null){
            return servicioService.create(servicioDto);
        }else{
            throw  new RuntimeException("El servicio ya existe");
        }
    }
    @MutationMapping
    @PreAuthorize("hasAnyAuthority('service/update','Ver Servicios')")
    public Servicio updateService(@Argument String id,@Argument @Valid ServiceDto servicioDto){
        return servicioService.update(id,servicioDto);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('service/delete','Ver Servicios')")
    public Servicio deleteService(@Argument String id){
        return servicioService.delete(id);
    }
}
