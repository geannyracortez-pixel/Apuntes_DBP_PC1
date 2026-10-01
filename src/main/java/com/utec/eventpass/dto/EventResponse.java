package com.utec.eventpass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EventResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDate eventDate;
    private String location;
}
