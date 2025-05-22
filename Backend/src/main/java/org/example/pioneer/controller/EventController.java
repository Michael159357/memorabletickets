package org.example.pioneer.controller;

import jakarta.validation.Valid;
import org.example.pioneer.config.JwtUtil;
import org.example.pioneer.dto.EventCreateDTO;
import org.example.pioneer.dto.EventDTO;
import org.example.pioneer.service.EventService;
import org.example.pioneer.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pioneer/events")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * Crear un nuevo evento
     */
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<EventDTO> createEvent(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("eventDate") String eventDate,
            @RequestParam("location") String location,
            @RequestHeader("Authorization") String token, // Recibir el token JWT
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            // Decodificar el token y obtener el userId
            String userId = jwtUtil.extractUserId(token);
            System.out.println("User ID extraído del token: " + userId); // Log del userId

            if (userId == null) {
                throw new RuntimeException("userId no encontrado en el token");
            }

            // Llamar al servicio para crear el evento
            EventDTO createdEvent = eventService.createEvent(name, description, eventDate, location, Long.valueOf(userId), image);
            return ResponseEntity.ok(createdEvent);
        } catch (Exception e) {
            System.err.println("Error al crear el evento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Obtener todos los eventos
     */
    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    /**
     * Obtener un evento por ID
     */
    @GetMapping("/{eventId}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long eventId) {
        EventDTO event = eventService.getEventById(eventId);
        return ResponseEntity.ok(event);
    }

    /**
     * Actualizar un evento existente
     */
    @PutMapping("/{eventId}")
    public ResponseEntity<EventDTO> updateEvent(
            @PathVariable Long eventId,
            @RequestBody @Valid EventCreateDTO eventRequest) {
        EventDTO updatedEvent = eventService.updateEvent(eventId, eventRequest);
        return ResponseEntity.ok(updatedEvent);
    }

    /**
     * Eliminar un evento por ID
     */
    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok("Evento eliminado correctamente");
    }
}
