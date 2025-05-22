package org.example.pioneer.repository;

import org.example.pioneer.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Busca tickets por el ID del usuario relacionado
    List<Ticket> findByHolderName(String holderName);
    List<Ticket> findByTicketBatchId(Long batchId);
}

