package com.group8.code.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotEmpty(message = "El nombre no puede estar vacío")
    private String firstName;

    @NotEmpty(message = "El apellido no puede estar vacío")
    private String lastName;

    @Email(message = "El correo electrónico no es válido")
    private String email;

    @Pattern(regexp = "^\\d{7,}$", message = "El número de teléfono debe tener al menos 7 dígitos")
    private String phone;

    @NotEmpty(message = "La dirección no puede estar vacía")
    private String address;

    private String position;

    @NotEmpty(message = "El rol no puede estar vacío")
    private String roleId;

}