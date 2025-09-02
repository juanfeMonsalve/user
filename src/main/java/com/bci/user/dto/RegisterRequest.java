package com.bci.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "El username es obligatorio")
    private String username;

    @NotBlank(message = "El password es obligatorio")
    @Pattern(
            regexp = "^(?=.{8,12}$)(?!.*[^A-Za-z0-9])(?=(?:.*[A-Z]){1})(?!.*[A-Z].*[A-Z])(?=(?:.*\\d){2})(?!.*\\d.*\\d.*\\d)[A-Za-z0-9]+$",
            message = "La clave debe tener 8-12 caracteres, solo letras y números"
    )
    private String password;

    @NotBlank(message = "El role es obligatorio")
    private String role;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;
}