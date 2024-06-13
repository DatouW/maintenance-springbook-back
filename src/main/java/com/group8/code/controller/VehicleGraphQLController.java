package com.group8.code.controller;

import com.group8.code.domain.Vehicle;
import com.group8.code.dto.VehicleDto;
import com.group8.code.service.VehicleService;
import jakarta.validation.Valid;
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
public class VehicleGraphQLController {

    @Autowired
    private VehicleService vehicleService;

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('vehicle/view','Ver Vehículos')")
    public List<Vehicle> vehicles() {
        return vehicleService.findAll();
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('vehicle/view','Ver Vehículos','Ver Citas de Mantenimiento')")
    public List<Vehicle> vehiclesByCustomer(@Argument String customerId) {
        return vehicleService.findAllByCustomer(customerId);
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('vehicle/view','Ver Vehículos')")
    public Vehicle vehicle(@Argument String id) {
        return vehicleService.findById(id);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('vehicle/create','Registrar Vehículos')")
    public Vehicle createVehicle(@Argument @Valid VehicleDto vehicleDto) {
        return vehicleService.create(vehicleDto);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('vehicle/update','Ver Vehículos')")
    public Vehicle updateVehicle(@Argument String id, @Argument @Valid VehicleDto vehicleDto) {
        return vehicleService.update(id, vehicleDto);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('vehicle/delete','Ver Vehículos')")
    public Vehicle deleteVehicle(@Argument String id) {
        return vehicleService.delete(id);
    }
}
