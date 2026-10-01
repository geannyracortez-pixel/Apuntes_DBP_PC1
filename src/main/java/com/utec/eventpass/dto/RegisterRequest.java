package com.utec.eventpass.dto;

import com.utec.eventpass.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no es valido")
    private String email;

    @NotBlank(message = "La contrasena es obligatoria")
    @Size(min = 8, message = "La contrasena debe tener minimo 8 caracteres")
    private String password;

    // Opcional: USER o ADMIN. Si no se envia, sera USER.
    // (Solo para el simulacro; en un sistema real nadie elige ser ADMIN)
    private Role role;
}
