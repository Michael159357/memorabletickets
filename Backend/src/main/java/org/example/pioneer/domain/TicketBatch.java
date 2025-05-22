package org.example.pioneer.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ticket_batches")
public class TicketBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String eventName;

    @Column(nullable = false)
    private int totalTickets;

    @Column(nullable = false)
    private boolean isPublic;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String creatorName;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @OneToMany(mappedBy = "ticketBatch", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Ticket> tickets;
}
