package com.group8.code.dto;

import com.group8.code.validation.annotation.ValidScheduledDate;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceDto {

    private String status;
    private String date;
    private List<DetailDto> details;
    private String description;

    @Size(min=24,max = 24,message ="Id empleado invalido" )
    private String employeeId;
    @Size(min=24,max = 24,message ="Id vehiculo invalido" )
    private String vehicleId;
    @Size(min=24,max = 24,message ="Id cita invalido" )
    private String appointmentId;
}
