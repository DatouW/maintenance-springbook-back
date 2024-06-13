package com.group8.code.service;

import com.group8.code.domain.Appointment;
import com.group8.code.dto.AppointmentDto;

import java.util.List;

public interface AppointmentService {
    List<Appointment> getAll();
    List<Appointment> getAllByCustomer(String customerId);
    List<Appointment> getAllPendingAppointments();
    Appointment getById(String id);
    Appointment create(AppointmentDto appointmentDto);
    Appointment update(String id, AppointmentDto appointmentDto);
    Appointment delete(String id);

}
