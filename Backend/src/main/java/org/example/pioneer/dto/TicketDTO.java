package org.example.pioneer.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketDTO {
    private Long id;
    private String qrCode;
    private String holderName;  // Nombre del propietario del ticket
    private String background;  // Fondo del ticket
    private String position;    // Posición del ticket
    private String color;       // Color del ticket
    private String status;      // Estado del ticket (Disponible, Transferido, etc.)
    private String dynamicKey;  // Llave dinámica para seguridad
    private LocalDateTime nextKeyUpdate;  // Fecha de la próxima actualización de la llave
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
