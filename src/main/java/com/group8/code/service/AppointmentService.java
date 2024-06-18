package com.group8.code.service;

import com.group8.code.domain.Appointment;
import com.group8.code.dto.AppointmentDto;
import com.group8.code.dto.Pagination;

import java.util.List;

public interface AppointmentService {
    List<Appointment> getAll();
    Pagination<Appointment> getAll(int offset, int limit);
    List<Appointment> getAllByCustomer(String customerId);
    List<Appointment> getAllPendingAppointments();
    Pagination<Appointment> getAllByCustomer(String customerId,int offset, int limit);
    Pagination<Appointment> getAllPendingAppointments(int offset, int limit);
    Appointment getById(String id);
    Appointment create(AppointmentDto appointmentDto);
    Appointment update(String id, AppointmentDto appointmentDto);
    Appointment delete(String id);

}
