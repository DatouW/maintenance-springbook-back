package com.group8.code.controller;

import com.group8.code.domain.Appointment;
import com.group8.code.dto.AppointmentDto;
import com.group8.code.dto.Pagination;
import com.group8.code.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Controller
@Validated
public class AppointmentGraphQLController {

    @Autowired
    private AppointmentService appointmentService;

    
    @QueryMapping
    @PreAuthorize("hasAnyAuthority('appointment/view','Ver Citas de Mantenimiento')")
    public List<Appointment> appointments() {
        return appointmentService.getAll();
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('appointment/view','Ver Citas de Mantenimiento')")
    public Pagination<Appointment> appointmentsPag(@Argument int offset, @Argument int limit) {
        return appointmentService.getAll(offset, limit);
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('appointment/view','Ver Citas de Mantenimiento')")
    public Appointment appointment(@Argument String id) {
        return appointmentService.getById(id);
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('appointment/view','Ver Citas de Mantenimiento')")
    public List<Appointment> appointmentsByCustomer(@Argument String customerId) {
        return appointmentService.getAllByCustomer(customerId);
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('appointment/view','Ver Citas de Mantenimiento')")
    public List<Appointment> pendingAppointments() {
        return appointmentService.getAllPendingAppointments();
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('appointment/view','Ver Citas de Mantenimiento')")
    public Pagination<Appointment> appointmentsByCustomerPag(@Argument String customerId, @Argument int offset, @Argument int limit) {
        return appointmentService.getAllByCustomer(customerId,offset,limit);
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('appointment/view','Ver Citas de Mantenimiento')")
    public Pagination<Appointment> pendingAppointmentsPag(@Argument int offset, @Argument int limit) {
        return appointmentService.getAllPendingAppointments(offset, limit);
    }


    @MutationMapping
    @PreAuthorize("hasAnyAuthority('appointment/create','Ver Citas de Mantenimiento')")
    public Appointment createAppointment(@Argument @Valid AppointmentDto appointmentDto) {
        return appointmentService.create(appointmentDto);
    }


    @MutationMapping
    @PreAuthorize("hasAnyAuthority('appointment/update','Ver Citas de Mantenimiento')")
    public Appointment updateAppointment(@Argument String id, @Argument @Valid AppointmentDto appointmentDto) {
        return appointmentService.update(id, appointmentDto);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('appointment/delete','Ver Citas de Mantenimiento')")
    public Appointment deleteAppointment(@Argument String id) {
        return appointmentService.delete(id);
    }
}
