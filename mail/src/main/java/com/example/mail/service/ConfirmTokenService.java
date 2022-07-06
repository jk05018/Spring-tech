package com.example.mail.service;

import com.example.mail.entity.ConfirmToken;
import com.example.mail.repository.ConfirmTokenRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.sound.midi.Receiver;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class ConfirmTokenService {

  private final ConfirmTokenRepository confirmTokenRepository;
  private EmailSenderService emailSenderService;

  /**
   * 이메일 인증 토큰 생성
   */
  public Long createEmailConfirmationToken(String userId, String receiverEmail) {
    Assert.hasText(userId, "userId는 필수 입니다.");
    Assert.hasText(receiverEmail, "receiverEmail은 필수 입니다.");

    ConfirmToken emailConfirmToken = ConfirmToken.createEmailConfirmToken(userId);
    confirmTokenRepository.save(emailConfirmToken);

    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(receiverEmail);
    mailMessage.setSubject("회원가입 이메일 인증");
    mailMessage.setText("http://localhost:8080/confirm-email?token=" + emailConfirmToken.getId());
    emailSenderService.sendMail(mailMessage);

    return emailConfirmToken.getId();
  }

  public ConfirmToken findByIdAndExpirationDateAfterAndExpired(String confirmTokenId) {
    Optional<ConfirmToken> confirmationToken = confirmTokenRepository.findByIdAndExpirationDateAfterAndExpired(
        confirmTokenId, LocalDateTime.now(), false);

    return confirmationToken.orElseThrow(() -> new RuntimeException("토큰을 찾을 수 없습니다"));
  }

}
