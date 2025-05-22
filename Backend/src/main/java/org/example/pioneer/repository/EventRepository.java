package org.example.pioneer.repository;

import org.example.pioneer.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByIsVisible(boolean isVisible);
}
