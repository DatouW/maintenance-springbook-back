package com.group8.code.service;

import com.group8.code.domain.Maintenance;
import com.group8.code.dto.DetailDto;
import com.group8.code.dto.MaintenanceDto;

import java.util.List;

public interface MaintenanceService {

    List<Maintenance> getAll();

    List<Maintenance> getAllByEmployee(String id);

    List<Maintenance> getAllNotCompleted();

    Maintenance getByAppointment(String id);

    Maintenance getById(String id);

    Maintenance create(MaintenanceDto maintenanceDto);

    Maintenance addDetails(String id, List<DetailDto> detailDtos);

    Maintenance saveStatus(String id,String description, List<DetailDto> details);

    Maintenance update(String id, MaintenanceDto maintenanceDto);

    Maintenance deleteMaintenance(String id);
}
