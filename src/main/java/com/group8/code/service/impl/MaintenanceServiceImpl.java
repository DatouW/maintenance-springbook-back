package com.group8.code.service.impl;


import com.group8.code.domain.*;
import com.group8.code.dto.DetailDto;
import com.group8.code.dto.MaintenanceDto;
import com.group8.code.enums.Status;
import com.group8.code.repository.*;
import com.group8.code.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ServiceRepository serviceRepository;
//    @Autowired
//    private RestClient restClient;

    @Override
    public List<Maintenance> getAll() {
        List<Maintenance> maintenances = maintenanceRepository.findAll();
        maintenances.forEach(this::setRelatedEntities);
        return maintenances;
    }

    @Override
    public List<Maintenance> getAllByEmployee(String id) {
        List<Maintenance> maintenances = maintenanceRepository.findAllByEmployeeId(id).orElse(new ArrayList<>());
        maintenances.forEach(this::setRelatedEntities);
        return maintenances;
    }

    @Override
    public List<Maintenance> getAllNotCompleted() {
        List<Maintenance> maintenances = maintenanceRepository
                .findAllByStatusIn(Arrays.asList(Status.PENDING.toString(),Status.IN_PROGRESS.toString()))
                .orElse(new ArrayList<>());
        System.out.println(maintenances);
        maintenances.forEach(this::setRelatedEntities);
        return maintenances;
    }

    @Override
    public Maintenance getByAppointment(String id) {
        System.out.println("appoint mainte");
        Maintenance maintenance = maintenanceRepository.findByAppointmentId(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + id));
        setRelatedEntities(maintenance);
        return maintenance;
    }

    @Override
    public Maintenance getById(String id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + id));
        setRelatedEntities(maintenance);
        return maintenance;
    }

    @Override
    public Maintenance create(MaintenanceDto maintenanceDto) {
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        Maintenance maintenance = Maintenance.builder()
                .date(formattedDateTime)
                .status(Status.IN_PROGRESS.toString())
                .employeeId(maintenanceDto.getEmployeeId())
                .appointmentId(maintenanceDto.getAppointmentId())
                .vehicleId(maintenanceDto.getVehicleId())
                .build();
        Appointment appointment = appointmentRepository.findById(maintenanceDto.getAppointmentId()).orElseThrow(() -> new RuntimeException("Cita no encontrada " + maintenanceDto.getAppointmentId()));
        appointment.setStatus(Status.IN_PROGRESS.toString());

        //
        List<String> serviceIds = appointment.getRequestedServiceIds();
        List<Detail> details = new ArrayList<>();

        // encontrar servicio a traves de id
        for (String serviceId : serviceIds) {
            Servicio service = serviceRepository.findById(serviceId).orElse(null);
            if(service != null){
                details.add(new Detail(service.getId(),service.getName(),0));
            }
        }

        //
        maintenance.setDetails(details);

        Maintenance maintenance1 = null;
        try {
            appointmentRepository.save(appointment);
            maintenance1 = maintenanceRepository.save(maintenance);
            setRelatedEntities(maintenance1);
            Map<String, Object> map = Map.ofEntries(
                    Map.entry("data", maintenance1),
                    Map.entry("vehicle", maintenance1.getVehicleId()),
                    Map.entry("appointment", maintenance1.getAppointmentId()),
                    Map.entry("date", maintenance1.getDate())
            );
//            restClient.post("/maintenance/register", map, String.class);
        } catch (Exception e) {
            if (maintenance1 != null) {
                maintenanceRepository.delete(maintenance1); // rollback
                appointment.setStatus(Status.PENDING.toString());
                appointmentRepository.save(appointment);
            }
            throw new RuntimeException("Error registering appointment remotely: " + e.getMessage(), e);
        }
        return maintenance1;
    }

    @Override
    public Maintenance addDetails(String id, List<DetailDto> detailDtos){
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + id));
        maintenance.setDetails(detailDtos.stream()
                .map(detailInput -> new Detail(detailInput.getId(),detailInput.getDescription(), detailInput.getCost()))
                .collect(Collectors.toList()));
        maintenanceRepository.save(maintenance);
        setRelatedEntities(maintenance);
        return maintenance;
    }

    @Override
    public Maintenance saveStatus(String id,String description, List<DetailDto> details){
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + id));

        Appointment appointment = appointmentRepository.findById(maintenance.getAppointmentId()).orElseThrow(() -> new RuntimeException("Cita no encontrada " + maintenance.getAppointmentId()));
        appointment.setStatus(Status.COMPLETED.toString());
        appointmentRepository.save(appointment);

        maintenance.setStatus(Status.COMPLETED.toString());
        maintenance.setDescription(description);
        maintenance.setDetails(details.stream()
                .map(detailInput -> new Detail(detailInput.getId(),detailInput.getDescription(), detailInput.getCost()))
                .collect(Collectors.toList()));
        maintenanceRepository.save(maintenance);

        return maintenance;
    }

    @Override
    public Maintenance update(String id, MaintenanceDto maintenanceDto) {
        Maintenance existingMaintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + id));

        existingMaintenance.setDate(maintenanceDto.getDate());
        existingMaintenance.setStatus(maintenanceDto.getStatus());
        existingMaintenance.setDetails(maintenanceDto.getDetails().stream()
                .map(detailInput -> new Detail(detailInput.getId(), detailInput.getDescription(), detailInput.getCost()))
                .collect(Collectors.toList()));
        existingMaintenance.setEmployeeId(maintenanceDto.getEmployeeId());
        existingMaintenance.setAppointmentId(maintenanceDto.getAppointmentId());
        existingMaintenance.setVehicleId(maintenanceDto.getVehicleId());

        maintenanceRepository.save(existingMaintenance);
        setRelatedEntities(existingMaintenance);

        return existingMaintenance;
    }

    @Override
    public Maintenance deleteMaintenance(String id) {
        Maintenance existingMaintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + id));

        maintenanceRepository.deleteById(id);
        try {
//            restClient.delete("/maintenance/"+id);
        } catch (Exception e) {
            if (existingMaintenance != null) {
                maintenanceRepository.save(existingMaintenance); // rollback
            }
            throw new RuntimeException("Error deleting Maintenance remotely: " + e.getMessage(), e);
        }
        return existingMaintenance;
    }

    private void setRelatedEntities(Maintenance maintenance) {
        if(maintenance != null){
            User employee = userRepository.findById(maintenance.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found with id: " + maintenance.getEmployeeId()));
            Vehicle vehicle = vehicleRepository.findById(maintenance.getVehicleId())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + maintenance.getVehicleId()));
            Appointment appointment = appointmentRepository.findById(maintenance.getAppointmentId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + maintenance.getAppointmentId()));

            maintenance.setEmployee(employee);
            maintenance.setVehicle(vehicle);
            maintenance.setAppointment(appointment);
        }
    }
}
