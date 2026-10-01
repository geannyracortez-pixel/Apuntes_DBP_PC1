package com.utec.eventpass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RegistrationResponse {

    private Long id;
    private LocalDateTime registrationDate;
    private String userEmail;
    private Long ticketTypeId;
    private String ticketTypeName;
    private String eventName;
}
