package com.springtech.loginservice.config.auth;

import com.springtech.loginservice.user.entity.Role;
import com.springtech.loginservice.user.entity.User;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

  private Map<String, Object> attributes;
  private String nameAttributeKey;
  private String name;
  private String email;
  private String picture;

  @Builder
  public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name,
      String email, String picture) {
    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
    this.name = name;
    this.email = email;
    this.picture = picture;
  }

  public static OAuthAttributes of(String registrationId, String userNameAttributeName,
      Map<String, Object> attributes) {
    if ("github".equals(registrationId)) {
      return ofGithub("login", attributes);
    }
    if ("kakao".equals(registrationId)) {
      return ofKakao("nickname", attributes);
    }
    if ("naver".equals(registrationId)) {
      return ofNaver("name", attributes);
    }
    return ofGoogle(userNameAttributeName, attributes);
  }

  private static OAuthAttributes ofKakao(String userNameAttributeName,
      Map<String, Object> attributes) {
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

    return OAuthAttributes.builder()
        .name((String) kakaoProfile.get("nickname"))
        .email((String) kakaoAccount.get("email"))
        .picture((String) kakaoProfile.get("profile_image_url"))
        .attributes(attributes)
        .nameAttributeKey(userNameAttributeName)
        .build();
  }

  private static OAuthAttributes ofGithub(String userNameAttributeName,
      Map<String, Object> attributes) {

    System.out.println("email is " + (String) attributes.get("email"));
    return OAuthAttributes.builder()
        .name((String) attributes.get("login"))
        .email((String) attributes.get("email"))
        .picture((String) attributes.get("avatar_url"))
        .attributes(attributes)
        .nameAttributeKey(userNameAttributeName)
        .build();
  }

  private static OAuthAttributes ofGoogle(String userNameAttributeName,
      Map<String, Object> attributes) {
    return OAuthAttributes.builder()
        .name((String) attributes.get("name"))
        .email((String) attributes.get("email"))
        .picture((String) attributes.get("picture"))
        .attributes(attributes)
        .nameAttributeKey(userNameAttributeName)
        .build();
  }

  private static OAuthAttributes ofNaver(String userNameAttributeName,
      Map<String, Object> attributes) {
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");

    return OAuthAttributes.builder()
        .name((String) response.get("name"))
        .email((String) response.get("email"))
        .picture((String) response.get("profileImage"))
        .attributes(response)
        .nameAttributeKey(userNameAttributeName)
        .build();
  }

  public User toEntity() {
    return User.builder()
        .name(name)
        .email(email)
        .picture(picture)
        .role(Role.GUEST)
        .build();
  }
}
