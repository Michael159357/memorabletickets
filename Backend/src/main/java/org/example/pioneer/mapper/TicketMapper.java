package org.example.pioneer.mapper;

import org.example.pioneer.domain.Ticket;
import org.example.pioneer.dto.TicketDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketDTO toTicketDTO(Ticket ticket);

    Ticket toTicket(TicketDTO ticketDTO);
}

