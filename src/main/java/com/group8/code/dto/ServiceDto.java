package com.group8.code.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {

    @NotEmpty(message = "El nombre no puede estar vacío")
    private String name;

    private String description;

    public void setName(String name) {
        this.name = name != null ? name.toUpperCase() : null;
    }
}
