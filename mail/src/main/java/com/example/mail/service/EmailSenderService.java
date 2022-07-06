package com.example.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * JavaMailSender 객체를 사용하여 Async 방식으로 이메일 보냄
 */
@Service
@RequiredArgsConstructor
public class EmailSenderService {

  private final JavaMailSender mailSender;

  @Async
  public void sendMail(SimpleMailMessage email) {
    mailSender.send(email);
  }

}
