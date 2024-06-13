package com.group8.code.service.impl;

import com.group8.code.domain.Appointment;
import com.group8.code.domain.Servicio;
import com.group8.code.domain.User;
import com.group8.code.domain.Vehicle;
import com.group8.code.dto.AppointmentDto;
import com.group8.code.enums.Status;
import com.group8.code.repository.AppointmentRepository;
import com.group8.code.repository.ServiceRepository;
import com.group8.code.repository.UserRepository;
import com.group8.code.repository.VehicleRepository;
import com.group8.code.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private RestClient restClient;

    @Override
    public List<Appointment> getAll() {
        List<Appointment> appointments = appointmentRepository.findAll();
        appointments.forEach(this::addAddtionalInfo);
        return appointments;
    }
    @Override
    public List<Appointment> getAllByCustomer(String customerId){
        List<Appointment> appointments = appointmentRepository.findAllByCustomerId(customerId).orElse(new ArrayList<>() {
        });
        appointments.forEach(this::addAddtionalInfo);
        return appointments;
    }
    @Override
    public List<Appointment> getAllPendingAppointments(){
        List<Appointment> appointments = appointmentRepository.findAllByStatus(Status.PENDING.toString()).orElse(new ArrayList<>() {
        });
        appointments.forEach(this::addAddtionalInfo);
        return appointments;
    }

    @Override
    public Appointment getById(String id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        addAddtionalInfo(appointment);
        return appointment;
    }

    @Override
    public Appointment create(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        appointment.setScheduledDate(appointmentDto.getScheduledDate());
        appointment.setStatus(Status.PENDING.toString());
        appointment.setRequestedServiceIds(appointmentDto.getRequestedServiceIds());
        appointment.setCustomerId(appointmentDto.getCustomerId());
        appointment.setVehicleId(appointmentDto.getVehicleId());

        Appointment appointment1 = null;
        try {
            appointment1 = appointmentRepository.save(appointment);
            addAddtionalInfo(appointment1);
//            Map<String, Object> map = Map.ofEntries(
//                    Map.entry("data", appointment1),
//                    Map.entry("vehicle", appointment1.getVehicleId())
//            );
//            restClient.post("/appointment/register",map, String.class);
        } catch (Exception e) {

            if (appointment1 != null) {
                appointmentRepository.delete(appointment1); // rollback
            }
            throw new RuntimeException("Error registering appointment remotely: " + e.getMessage(), e);
        }
        return appointment1;
    }

    @Override
    public Appointment update(String id, AppointmentDto appointmentDto) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()->new RuntimeException("Cita no encontrada"));

        appointment.setScheduledDate(appointmentDto.getScheduledDate());
        appointment.setStatus(appointmentDto.getStatus());
        appointment.setRequestedServiceIds(appointmentDto.getRequestedServiceIds());
        appointment.setCustomerId(appointmentDto.getCustomerId());
        appointment.setVehicleId(appointmentDto.getVehicleId());

        addAddtionalInfo(appointment);

        return appointment;
    }

    @Override
    public Appointment delete(String id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()->new RuntimeException("Cita no encontrada"));
        appointmentRepository.delete(appointment);
        try {
//            restClient.delete("/appointment/"+id);
        } catch (Exception e) {
            if (appointment != null) {
                appointmentRepository.save(appointment); // rollback
            }
            throw new RuntimeException("Error deleting vehicle remotely: " + e.getMessage(), e);
        }
        return appointment;
    }

    private void addAddtionalInfo(Appointment appointment){

        if(appointment != null){
            User customer = userRepository.findById(appointment.getCustomerId()).orElseThrow(() -> new RuntimeException("Cliente no encontrado " + appointment.getCustomerId()));
            Vehicle vehicle = vehicleRepository.findById(appointment.getVehicleId()).orElseThrow(() -> new RuntimeException("Vehiculo no encontrado" + appointment.getVehicleId()));
            List<Servicio> requestedServices = appointment.getRequestedServiceIds().stream()
                    .map(serviceId -> serviceRepository.findById(serviceId)
                            .orElseThrow(() -> new RuntimeException("Servicio no encontrado " + serviceId)))
                    .collect(Collectors.toList());
            appointment.setCustomer(customer);
            appointment.setVehicle(vehicle);
            appointment.setRequestedServices(requestedServices);
        }
    }
}
