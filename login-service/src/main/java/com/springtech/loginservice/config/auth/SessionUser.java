package com.springtech.loginservice.config.auth;

import com.springtech.loginservice.user.entity.User;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {

  // TODO Session을 JWT 토큰으로 변경
  private String name;
  private String email;
  private String picture;

  public SessionUser(final User user) {
    this.name = user.getName();
    this.picture = user.getPicture();
    this.email = user.getEmail();
  }
}
