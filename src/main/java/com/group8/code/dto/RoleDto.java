package com.group8.code.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    @NotEmpty(message = "El nombre no puede estar vacío")
    private String name;

    private String description;


    @NotNull(message = "Debe tener al menos 1 permiso asignado")
    @Size(min=1, message = "Debe tener al menos 1 permiso asignado")
    private List<String> permissions;

    public void setName(String name) {
        this.name = name != null ? name.toUpperCase() : null;
    }
}
