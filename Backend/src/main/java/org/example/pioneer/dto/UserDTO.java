package org.example.pioneer.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;          // Identificador único del usuario
    private String username;  // Nombre de usuario
    private String lastName;  // Apellido
    private String email;     // Correo electrónico
}

