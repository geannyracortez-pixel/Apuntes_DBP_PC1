package com.utec.eventpass.controller;

import com.utec.eventpass.dto.RegistrationRequest;
import com.utec.eventpass.dto.RegistrationResponse;
import com.utec.eventpass.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    // Authentication lo llena Spring con el usuario que puso el filtro JWT.
    // authentication.getName() devuelve el email del usuario autenticado.
    @PostMapping
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequest request,
                                                         Authentication authentication) {
        RegistrationResponse response = registrationService.register(authentication.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<List<RegistrationResponse>> myRegistrations(Authentication authentication) {
        return ResponseEntity.ok(registrationService.findMyRegistrations(authentication.getName()));
    }
}
