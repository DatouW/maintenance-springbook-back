package com.group8.code.dto;

import com.group8.code.validation.annotation.ValidYear;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {
    @Size(min = 6, max = 10,message = "La placa tener tener al menos 6 caracteres")
    private String licensePlate;
    @NotEmpty(message = "La marca no puede estar vacía")
    private String brand;
    @NotEmpty(message = "El modelo no puede estar vacío")
    private String model;
    @ValidYear
    private int year;
    @Size(min=13,max = 13,message ="VIN invalido" )
    private String vin;
    @Size(min=24,max = 24,message ="Id Cliente invalido" )
    private String customerId;
}
