package com.springtech.loginservice.user.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class UserTest {

  final String name = "seunghan";
  final String email = "seunghan@google.com";
  final String picture = "picture";
  final Role guest = Role.GUEST;

  @Test
  void User_생성_테스트() {
    // when
    final User user = User.builder()
        .name(name)
        .email(email)
        .picture(picture)
        .role(guest)
        .build();

    // then
    assertThat(user)
        .extracting(User::getName, User::getEmail, User::getPicture,
            User::getRole)
        .isEqualTo(List.of(name, email, picture, guest));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void 이름_공백_User_생성_실패_테스트(String nullAndEmptyName) {
    Assertions.assertThatThrownBy(() -> User.builder()
            .name(nullAndEmptyName)
            .email(email)
            .picture(picture)
            .role(guest)
            .build())
        .isInstanceOf(IllegalArgumentException.class);
  }

  @ParameterizedTest
  @NullAndEmptySource
  void 이메일_공백_User_생성_실패_테스트(String nullAndEmptyEmail) {
    Assertions.assertThatThrownBy(() -> User.builder()
            .name(name)
            .email(nullAndEmptyEmail)
            .picture(picture)
            .role(guest)
            .build())
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void 역할_NULL_User_생성_실패_테스트() {
    Assertions.assertThatThrownBy(() -> User.builder()
            .name(name)
            .email(email)
            .picture(picture)
            .role(null)
            .build())
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void 이름_사진_update_테스트() {
    // given
    User user = User.builder()
        .name(name)
        .email(email)
        .picture(picture)
        .role(guest)
        .build();

    final String updateName = "update";
    final String updatePicture = "updatePicture";

    // when
    final User updateUser = user.update(updateName, updatePicture);

    // then
    assertThat(updateUser)
        .extracting(User::getName, User::getPicture)
        .isEqualTo(List.of(updateName, updatePicture));
  }
}
