package org.example.pioneer.service;

import org.example.pioneer.dto.TicketBatchDTO;
import org.example.pioneer.dto.TicketDTO;
import org.example.pioneer.mapper.TicketBatchMapper;
import org.example.pioneer.mapper.TicketMapper;
import org.example.pioneer.repository.TicketBatchRepository;
import org.example.pioneer.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketBatchRepository ticketBatchRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private TicketBatchMapper ticketBatchMapper;

    public TicketBatchDTO createTicketBatch(TicketBatchDTO ticketBatchDTO) {
        var ticketBatch = ticketBatchMapper.toTicketBatch(ticketBatchDTO);
        ticketBatch.setCreatedAt(LocalDateTime.now());

        var savedBatch = ticketBatchRepository.save(ticketBatch);

        for (int i = 0; i < ticketBatchDTO.getTotalTickets(); i++) {
            var ticket = new org.example.pioneer.domain.Ticket();
            ticket.setQrCode(UUID.randomUUID().toString());
            ticket.setCreatedAt(LocalDateTime.now());
            ticket.setUpdatedAt(LocalDateTime.now());
            ticket.setTicketBatch(savedBatch);

            ticketRepository.save(ticket);
        }

        return ticketBatchMapper.toTicketBatchDTO(savedBatch);
    }

    public TicketDTO getTicketById(Long ticketId) {
        var ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
        return ticketMapper.toTicketDTO(ticket);
    }

    public List<TicketDTO> getTicketsByBatch(Long batchId) {
        return ticketRepository.findByTicketBatchId(batchId)
                .stream()
                .map(ticketMapper::toTicketDTO)
                .collect(Collectors.toList());
    }

    public List<TicketDTO> getTicketsByUser(String username) {
        return ticketRepository.findByHolderName(username)
                .stream()
                .map(ticketMapper::toTicketDTO)
                .collect(Collectors.toList());
    }

    public TicketDTO updateTicket(Long ticketId, TicketDTO ticketDTO) {
        var ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

        ticket.setBackground(ticketDTO.getBackground());
        ticket.setPosition(ticketDTO.getPosition());
        ticket.setColor(ticketDTO.getColor());
        ticket.setUpdatedAt(LocalDateTime.now());

        var updatedTicket = ticketRepository.save(ticket);
        return ticketMapper.toTicketDTO(updatedTicket);
    }
}
