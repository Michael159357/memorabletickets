package org.example.pioneer.mapper;

import org.example.pioneer.domain.TicketBatch;
import org.example.pioneer.dto.TicketBatchDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketBatchMapper {
    TicketBatchDTO toTicketBatchDTO(TicketBatch ticketBatch);

    TicketBatch toTicketBatch(TicketBatchDTO ticketBatchDTO);
}

