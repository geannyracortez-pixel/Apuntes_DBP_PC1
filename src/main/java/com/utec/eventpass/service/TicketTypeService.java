package com.utec.eventpass.service;

import com.utec.eventpass.dto.TicketTypeCreateRequest;
import com.utec.eventpass.dto.TicketTypeResponse;
import com.utec.eventpass.entity.Event;
import com.utec.eventpass.entity.TicketType;
import com.utec.eventpass.exception.ResourceNotFoundException;
import com.utec.eventpass.repository.EventRepository;
import com.utec.eventpass.repository.TicketTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;
    private final EventRepository eventRepository;

    public TicketTypeResponse create(Long eventId, TicketTypeCreateRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con id " + eventId));

        TicketType ticketType = new TicketType();
        ticketType.setName(request.getName());
        ticketType.setPrice(request.getPrice());
        ticketType.setCapacity(request.getCapacity());
        ticketType.setAvailableTickets(request.getCapacity()); // al inicio todas estan disponibles
        ticketType.setEvent(event);

        return toResponse(ticketTypeRepository.save(ticketType));
    }

    private TicketTypeResponse toResponse(TicketType ticketType) {
        return new TicketTypeResponse(
                ticketType.getId(),
                ticketType.getName(),
                ticketType.getPrice(),
                ticketType.getCapacity(),
                ticketType.getAvailableTickets(),
                ticketType.getEvent().getId()
        );
    }
}
