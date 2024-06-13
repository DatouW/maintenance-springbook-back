package com.group8.code.dto;

import com.group8.code.validation.annotation.DateTimeFormat;
import com.group8.code.validation.annotation.DateTimeRange;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    @NotEmpty(message = "Debe asignar una fecha y hora para la cita")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm", message = "El formato de fecha y hora debe ser dd/MM/yyyy HH:mm")
    @DateTimeRange(minMinutes = 5, message = "Fecha y hora invalida")
    private String scheduledDate;
    private String status;
    @NotEmpty(message = "Debe tener al menos un servicio")
    @Size(min = 1, message = "Debe tener al menos un servicio")
    private List<String> requestedServiceIds;
    @Size(min=24,max = 24,message ="Id cliente invalido" )
    private String customerId;
    @Size(min=24,max = 24,message ="Id vehiculo invalido" )
    private String vehicleId;
}
