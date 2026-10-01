package com.utec.eventpass.repository;

import com.utec.eventpass.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsByUserIdAndTicketTypeId(Long userId, Long ticketTypeId);

    List<Registration> findByUserId(Long userId);
}
