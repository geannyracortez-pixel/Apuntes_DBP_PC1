package com.utec.eventpass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketTypeResponse {

    private Long id;
    private String name;
    private Double price;
    private Integer capacity;
    private Integer availableTickets;
    private Long eventId;
}
