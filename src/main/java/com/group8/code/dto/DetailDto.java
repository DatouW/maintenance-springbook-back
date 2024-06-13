package com.group8.code.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailDto {
    @Size(min=24,max = 24,message ="Id invalido" )
    private String id;
    @NotEmpty(message = "La descripcion no puede estar vacía")
    private String description;
    @Min(0)
    private float cost;
}
