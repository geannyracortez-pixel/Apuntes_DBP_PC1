package com.utec.eventpass.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    private Integer capacity;

    private Integer availableTickets;

    // Muchos tipos de entrada pertenecen a UN evento
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
