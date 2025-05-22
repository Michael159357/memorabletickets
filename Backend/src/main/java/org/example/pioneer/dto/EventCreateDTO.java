package org.example.pioneer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventCreateDTO {

    @NotNull(message = "El nombre del evento es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre del evento debe tener entre 3 y 100 caracteres")
    private String name;

    @NotNull(message = "La descripción del evento es obligatoria")
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    private String description;

    @NotNull(message = "La fecha del evento es obligatoria")
    private LocalDateTime eventDate;

    @NotNull(message = "La ubicación del evento es obligatoria")
    private String location;

    private String image;

}
