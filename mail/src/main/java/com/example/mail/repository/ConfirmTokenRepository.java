package com.example.mail.repository;

import com.example.mail.entity.ConfirmToken;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmTokenRepository extends JpaRepository<ConfirmToken, Long> {

  Optional<ConfirmToken> findByIdAndExpirationDateAfterAndExpired(Long id, LocalDateTime now, boolean expired);

}
