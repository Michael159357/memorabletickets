package org.example.pioneer.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TicketBatchDTO {
    private Long id;
    private String creatorName;  // Nombre del creador del lote
    private int totalTickets;    // Cantidad total de tickets en el lote
    private LocalDateTime createdAt;  // Fecha de creación del lote
    private List<TicketDTO> tickets;  // Lista de tickets asociados al lote
}
