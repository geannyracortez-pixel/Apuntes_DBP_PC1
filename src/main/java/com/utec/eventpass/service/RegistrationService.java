package com.utec.eventpass.service;

import com.utec.eventpass.dto.RegistrationRequest;
import com.utec.eventpass.dto.RegistrationResponse;
import com.utec.eventpass.entity.Registration;
import com.utec.eventpass.entity.TicketType;
import com.utec.eventpass.entity.User;
import com.utec.eventpass.exception.DuplicateResourceException;
import com.utec.eventpass.exception.NoAvailabilityException;
import com.utec.eventpass.exception.ResourceNotFoundException;
import com.utec.eventpass.repository.RegistrationRepository;
import com.utec.eventpass.repository.TicketTypeRepository;
import com.utec.eventpass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final UserRepository userRepository;

    // OPERACION PRINCIPAL DEL NEGOCIO: inscribirse
    @Transactional // si algo falla a la mitad, no se guarda nada
    public RegistrationResponse register(String userEmail, RegistrationRequest request) {
        // 0. Usuario autenticado (viene del token JWT)
        User user = findUser(userEmail);

        // 1. El TicketType debe existir
        TicketType ticketType = ticketTypeRepository.findById(request.getTicketTypeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tipo de entrada no encontrado con id " + request.getTicketTypeId()));

        // 2. El Event relacionado debe existir
        if (ticketType.getEvent() == null) {
            throw new ResourceNotFoundException("El tipo de entrada no tiene un evento asociado");
        }

        // 3. Deben quedar entradas disponibles
        if (ticketType.getAvailableTickets() <= 0) {
            throw new NoAvailabilityException("No quedan entradas disponibles para este tipo de entrada");
        }

        // 4. El usuario no debe estar inscrito antes con ese mismo tipo de entrada
        if (registrationRepository.existsByUserIdAndTicketTypeId(user.getId(), ticketType.getId())) {
            throw new DuplicateResourceException("Ya estas inscrito con este tipo de entrada");
        }

        // Todo correcto: creamos la inscripcion
        Registration registration = new Registration();
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setUser(user);
        registration.setTicketType(ticketType);

        // Reducimos en 1 las entradas disponibles
        ticketType.setAvailableTickets(ticketType.getAvailableTickets() - 1);

        // Guardamos los cambios
        ticketTypeRepository.save(ticketType);
        Registration saved = registrationRepository.save(registration);

        return toResponse(saved);
    }

    public List<RegistrationResponse> findMyRegistrations(String userEmail) {
        User user = findUser(userEmail);
        return registrationRepository.findByUserId(user.getId()).stream()
                .map(this::toResponse)
                .toList();
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    private RegistrationResponse toResponse(Registration registration) {
        return new RegistrationResponse(
                registration.getId(),
                registration.getRegistrationDate(),
                registration.getUser().getEmail(),
                registration.getTicketType().getId(),
                registration.getTicketType().getName(),
                registration.getTicketType().getEvent().getName()
        );
    }
}
