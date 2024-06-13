package com.group8.code.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {
    @Size(min = 6, max = 20,message = "El nombre de usuario debe tener entre 6 y 20 caracteres")
    private String username;

    @Size(min = 7, max=50,message = "La contraseña debe tener entre 7 y 50 caracteres")
    private String password;
}
