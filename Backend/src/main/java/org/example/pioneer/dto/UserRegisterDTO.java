package org.example.pioneer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.pioneer.enumrole.Role;

import java.util.List;

@Data
public class UserRegisterDTO {

    @NotBlank(message = "Username is required")
    @Size(max = 30, message = "Username cannot exceed 30 characters")
    private String username;

    @NotBlank(message = "Last name is required")
    @Size(max = 30, message = "Last name cannot exceed 30 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    // Los campos createdAt y updatedAt no se reciben en la solicitud, ya que se generan automáticamente.
}
