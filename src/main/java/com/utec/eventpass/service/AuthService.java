package com.utec.eventpass.service;

import com.utec.eventpass.dto.LoginRequest;
import com.utec.eventpass.dto.RegisterRequest;
import com.utec.eventpass.dto.TokenResponse;
import com.utec.eventpass.entity.Role;
import com.utec.eventpass.entity.User;
import com.utec.eventpass.exception.DuplicateResourceException;
import com.utec.eventpass.exception.UnauthorizedException;
import com.utec.eventpass.repository.UserRepository;
import com.utec.eventpass.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public TokenResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Ya existe un usuario con ese email");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // se guarda cifrada con BCrypt
        user.setRole(request.getRole() != null ? request.getRole() : Role.USER);
        userRepository.save(user);

        return new TokenResponse(jwtService.generateToken(user.getEmail()));
    }

    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Email o contrasena incorrectos"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Email o contrasena incorrectos");
        }

        return new TokenResponse(jwtService.generateToken(user.getEmail()));
    }
}
