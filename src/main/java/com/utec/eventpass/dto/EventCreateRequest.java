package com.utec.eventpass.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventCreateRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    private String description;

    @NotNull(message = "La fecha del evento es obligatoria")
    private LocalDate eventDate;

    @NotBlank(message = "La ubicacion es obligatoria")
    private String location;
}
