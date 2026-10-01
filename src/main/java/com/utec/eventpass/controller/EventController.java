package com.utec.eventpass.controller;

import com.utec.eventpass.dto.EventCreateRequest;
import com.utec.eventpass.dto.EventResponse;
import com.utec.eventpass.dto.TicketTypeCreateRequest;
import com.utec.eventpass.dto.TicketTypeResponse;
import com.utec.eventpass.service.EventService;
import com.utec.eventpass.service.TicketTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final TicketTypeService ticketTypeService;

    @PostMapping
    public ResponseEntity<EventResponse> create(@Valid @RequestBody EventCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> findAll() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @PostMapping("/{eventId}/ticket-types")
    public ResponseEntity<TicketTypeResponse> createTicketType(@PathVariable Long eventId,
                                                               @Valid @RequestBody TicketTypeCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketTypeService.create(eventId, request));
    }
}
