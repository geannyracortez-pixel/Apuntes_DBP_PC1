package com.utec.eventpass.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RegistrationRequest {

    @NotNull(message = "El ticketTypeId es obligatorio")
    @Positive(message = "El ticketTypeId debe ser mayor a 0")
    private Long ticketTypeId;
}
