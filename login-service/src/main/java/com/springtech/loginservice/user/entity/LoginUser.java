package com.springtech.loginservice.user.entity;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  private String picture;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @Builder
  public LoginUser(String name, String email, String picture, Role role) {
    validateName(name);
    checkArgument(hasText(email), "유저 이메일은 공백일 수 없습니다.");
    checkArgument(nonNull(role), "유저는 반드시 하나의 역할을 가지고 있어야 한다.");

    this.name = name;
    this.email = email;
    this.picture = picture;
    this.role = role;
  }

  private void validateName(String name) {
    checkArgument(hasText(name), "유저 이름은 공백일 수 없습니다.");
  }

  public LoginUser update(String name, String picture) {
    validateName(name);

    this.name = name;
    this.picture = picture;

    return this;
  }

  public String getRoleKey() {
    return this.role.getKey();
  }
}
