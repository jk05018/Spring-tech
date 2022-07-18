package com.springtech.loginservice.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomOAuth2UserService customOAuth2UserService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .headers().frameOptions().disable()
        .and()
        .authorizeRequests()
        .antMatchers("/", "/h2-console/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .logout()
        .logoutSuccessUrl("/")
        .and()
        .oauth2Login()
        .userInfoEndpoint()
        .userService(customOAuth2UserService);
    return http.build();
  }

}
