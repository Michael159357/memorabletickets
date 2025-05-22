package org.example.pioneer.controller;

import org.example.pioneer.dto.TicketBatchDTO;
import org.example.pioneer.dto.TicketDTO;
import org.example.pioneer.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pioneer/tickets")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    /**
     * Crear un lote de tickets
     */
    @PostMapping("/batch")
    public ResponseEntity<TicketBatchDTO> createTicketBatch(@RequestBody TicketBatchDTO ticketBatchRequest) {
        TicketBatchDTO createdBatch = ticketService.createTicketBatch(ticketBatchRequest);
        return ResponseEntity.ok(createdBatch);
    }

    /**
     * Obtener detalles de un ticket específico
     */
    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long ticketId) {
        TicketDTO ticket = ticketService.getTicketById(ticketId);
        return ResponseEntity.ok(ticket);
    }

    /**
     * Obtener todos los tickets por lote
     */
    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<TicketDTO>> getTicketsByBatch(@PathVariable Long batchId) {
        List<TicketDTO> tickets = ticketService.getTicketsByBatch(batchId);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Obtener tickets por usuario
     */
    @GetMapping("/user/{username}")
    public ResponseEntity<List<TicketDTO>> getTicketsByUser(@PathVariable String username) {
        List<TicketDTO> tickets = ticketService.getTicketsByUser(username);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Actualizar un ticket
     */
    @PutMapping("/{ticketId}")
    public ResponseEntity<TicketDTO> updateTicket(
            @PathVariable Long ticketId,
            @RequestBody TicketDTO ticketDTO) {
        TicketDTO updatedTicket = ticketService.updateTicket(ticketId, ticketDTO);
        return ResponseEntity.ok(updatedTicket);
    }
}
