package org.example.pioneer.domain;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name; // Nombre del evento

    @Column(nullable = false, length = 500)
    private String description; // Descripción del evento

    @Column(nullable = false)
    private LocalDateTime eventDate; // Fecha y hora del evento

    @Column(nullable = false, length = 200)
    private String location; // Ubicación del evento

    @Column(nullable = false, length = 500)
    private String image = "/images/default-event-image.webp"; // Valor por defecto

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt; // Fecha de creación del evento

    @Column(nullable = false)
    private boolean isVisible = false;

    @Column(name = "creator_name", nullable = true) // Este campo debe estar mapeado
    private String creatorName;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt; // Fecha de última modificación

    // Relación con Usuario (Creador del Evento)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User creator; // Usuario que creó el evento


    @JsonProperty("creatorName")
    public String getCreatorName() {
        return this.creatorName;
    }

    // Setter para establecer el nombre del creador desde el objeto User
    public void setCreator(User creator) {
        this.creator = creator;
        this.creatorName = creator != null ? creator.getUsername() : null; // Establecer el nombre
    }

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketBatch> ticketBatches; // Lista de grupos de tickets asociados al evento

}
