package org.example.pioneer.service;

import org.example.pioneer.domain.Event;
import org.example.pioneer.domain.User;
import org.example.pioneer.dto.EventCreateDTO;
import org.example.pioneer.dto.EventDTO;
import org.example.pioneer.mapper.EventMapper;
import org.example.pioneer.repository.EventRepository;
import org.example.pioneer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Crear un evento
     */
    public EventDTO createEvent(String name, String description, String eventDate, String location, Long userId, MultipartFile image) {
        // Verificar si el userId es nulo
        if (userId == null) {
            throw new RuntimeException("El ID del usuario no se recibió correctamente");
        }

        // Obtener al usuario desde la base de datos
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        System.out.println("Usuario encontrado: " + user.getId() + " - " + user.getUsername());

        // Crear el DTO del evento
        EventCreateDTO eventCreateDTO = new EventCreateDTO();
        eventCreateDTO.setName(name);
        eventCreateDTO.setDescription(description);
        eventCreateDTO.setEventDate(LocalDateTime.parse(eventDate));
        eventCreateDTO.setLocation(location);

        // Asignar una imagen por defecto inicialmente
        String imageUrl = "/image/Mosaic_event_default.webp";

        if (image != null && !image.isEmpty()) {
            // Guardar la imagen convertida y obtener la URL
            String savedImagePath = fileStorageService.storeFile(image);  // Aquí se guarda la imagen convertida a WEBP
            imageUrl = savedImagePath; // Ruta de la imagen guardada
            System.out.println("Imagen guardada: " + savedImagePath);
        } else {
            System.out.println("Imagen por defecto asignada.");
        }

        eventCreateDTO.setImage(imageUrl);  // Asignar la URL de la imagen

        // Mapeo del DTO a la entidad del evento
        Event event = eventMapper.toEvent(eventCreateDTO);
        System.out.println("Evento mapeado: " + event.getName() + ", " + event.getDescription());

        // Asociar el evento con el creador (usuario)
        event.setCreator(user);
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
        event.setVisible(false);

        // Guardar el evento en la base de datos
        Event savedEvent = eventRepository.save(event);

        // Retornar el DTO del evento guardado
        return eventMapper.toEventDTO(savedEvent);
    }


    /**
     * Obtener todos los eventos
     */
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(eventMapper::toEventDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener un evento por ID
     */
    public EventDTO getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        return eventMapper.toEventDTO(event);
    }

    /**
     * Actualizar un evento existente
     */
    public EventDTO updateEvent(Long eventId, EventCreateDTO eventRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        // Actualizar campos del evento con los valores del DTO
        event.setName(eventRequest.getName());
        event.setDescription(eventRequest.getDescription());
        event.setEventDate(eventRequest.getEventDate());
        event.setLocation(eventRequest.getLocation());
        if (eventRequest.getImage() != null && !eventRequest.getImage().isEmpty()) {
            event.setImage(eventRequest.getImage());
        }
        event.setUpdatedAt(LocalDateTime.now());

        Event updatedEvent = eventRepository.save(event);
        return eventMapper.toEventDTO(updatedEvent);
    }

    /**
     * Eliminar un evento por ID
     */
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        eventRepository.delete(event);
    }
}
