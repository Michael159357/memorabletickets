package org.example.pioneer.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tickets", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"qrCode"})
})
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String qrCode;

    @Column(nullable = false)
    private String holderName;

    @Column(nullable = true)
    private String background;

    @Column(nullable = true)
    private String position;

    @Column(nullable = true)
    private String color;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "ticket_batch_id", nullable = false)
    private TicketBatch ticketBatch;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String dynamicKey;

    @Column(nullable = false)
    private LocalDateTime nextKeyUpdate;
}
