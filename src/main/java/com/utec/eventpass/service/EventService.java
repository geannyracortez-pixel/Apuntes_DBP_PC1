package com.utec.eventpass.service;

import com.utec.eventpass.dto.EventCreateRequest;
import com.utec.eventpass.dto.EventResponse;
import com.utec.eventpass.entity.Event;
import com.utec.eventpass.exception.ResourceNotFoundException;
import com.utec.eventpass.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public EventResponse create(EventCreateRequest request) {
        Event event = new Event();
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setLocation(request.getLocation());
        return toResponse(eventRepository.save(event));
    }

    public List<EventResponse> findAll() {
        return eventRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public EventResponse findById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con id " + id));
        return toResponse(event);
    }

    // Convierte la entidad Event en el DTO EventResponse
    private EventResponse toResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getEventDate(),
                event.getLocation()
        );
    }
}
