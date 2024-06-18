package com.group8.code.controller;

import com.group8.code.domain.Maintenance;
import com.group8.code.dto.DetailDto;
import com.group8.code.dto.MaintenanceDto;
import com.group8.code.dto.Pagination;
import com.group8.code.service.MaintenanceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
public class MaintenanceGraphQLController {
    @Autowired
    private MaintenanceService maintenanceService;


    @QueryMapping
    @PreAuthorize("hasAnyAuthority('maintenance/view','Ver Mantenimientos')")
    public List<Maintenance> maintenances() {
        return maintenanceService.getAll();
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('maintenance/view','Ver Mantenimientos')")
    public Pagination<Maintenance> maintenancesPag(@Argument int offset, @Argument int limit) {
        return maintenanceService.getAll(offset,limit);
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('maintenance/view','Ver Citas de Mantenimiento')")
    public List<Maintenance> maintenancesNotCompleted() {
        return maintenanceService.getAllNotCompleted();
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('maintenance/view','Ver Citas de Mantenimiento')")
    public Pagination<Maintenance> maintenancesNotCompletedPag(@Argument int offset, @Argument int limit) {
        return maintenanceService.getAllNotCompleted(offset,limit);
    }
    @QueryMapping
    @PreAuthorize("hasAnyAuthority('maintenance/view','Ver Citas de Mantenimiento')")
    public List<Maintenance> maintenancesByEmployee(@Argument @NotEmpty String employeeId) {
        return maintenanceService.getAllByEmployee(employeeId);
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('maintenance/view','Ver Mantenimientos')")
    public Maintenance maintenance(@Argument String id) {
        return maintenanceService.getById(id);
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('maintenance/view','Ver Citas de Mantenimiento')")
    public Maintenance maintenanceByAppointment(@Argument String id) {
        return maintenanceService.getByAppointment(id);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('maintenance/create','Ver Citas de Mantenimiento')")
    public Maintenance createMaintenance(@Argument @Valid MaintenanceDto maintenanceDto) {
        return maintenanceService.create(maintenanceDto);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('maintenance/update','Ver Mantenimientos')")
    public Maintenance addDetails(@Argument String id, @Argument List<DetailDto> detailDtos) {

        return maintenanceService.addDetails(id,detailDtos);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('maintenance/update','Ver Mantenimientos')")
    public Maintenance completedStatus(@Argument String id,@Argument String description, @Argument List<DetailDto> detailDtos) {
        return maintenanceService.saveStatus(id,description,detailDtos);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('maintenance/update','Ver Mantenimientos')")
    public Maintenance updateMaintenance(@Argument String id, @Argument @Valid MaintenanceDto maintenanceDto) {
        return maintenanceService.update(id, maintenanceDto);
    }


    @MutationMapping
    @PreAuthorize("hasAnyAuthority('maintenance/delete','Ver Mantenimientos')")
    public Maintenance deleteMaintenance(@Argument String id) {
        return maintenanceService.deleteMaintenance(id);
    }
}
