package org.example.pioneer.repository;

import org.example.pioneer.domain.TicketBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketBatchRepository extends JpaRepository<TicketBatch, Long> {
    boolean existsByEventId(Long eventId);
}
