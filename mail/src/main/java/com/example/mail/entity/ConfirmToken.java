package com.example.mail.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConfirmToken {

  private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, length = 8, nullable = false)
  private String key;

  private LocalDateTime expirationDate;

  @Column(nullable = false)
  private String email;

  private boolean expired;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createDate;

  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  private ConfirmToken(LocalDateTime expirationDate, boolean expired, String key, String email) {
    this.expirationDate = expirationDate;
    this.expired = expired;
    this.key = key;
    this.email = email;
  }

  public static ConfirmToken createEmailConfirmToken(String key, String email) {
    return new ConfirmToken(LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE),
        false, key, email);
  }

  /**
   * 토큰 사용으로 인한 만료
   */
  public void userToken() {
    expired = true;
  }

}
