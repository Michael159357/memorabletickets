package org.example.pioneer.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDTO {

    private Long id; // Identificador único del evento

    private String name; // Nombre del evento

    private String description; // Descripción del evento

    private LocalDateTime eventDate; // Fecha y hora del evento

    private String location; // Ubicación del evento

    private String image; // Imagen asociada al evento

    private String creatorName; // Nombre del usuario que creó el evento

    private boolean visible; // Indica si el evento es visible o no
}
