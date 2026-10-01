package com.utec.eventpass.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime registrationDate;

    // Muchas inscripciones pueden ser de UN usuario
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Muchas inscripciones pueden ser de UN tipo de entrada
    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;
}
