package org.example.pioneer.mapper;

import org.example.pioneer.domain.Event;
import org.example.pioneer.dto.EventCreateDTO;
import org.example.pioneer.dto.EventDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDTO toEventDTO(Event event);

    Event toEvent(EventCreateDTO eventCreateDTO);

    EventCreateDTO toEventCreateDTO(Event event);
}

