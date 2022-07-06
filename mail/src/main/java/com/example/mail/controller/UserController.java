package com.example.mail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("confirm-email")
  public String viewConfirmEmail(@Valid @RequestParam String token){
    userService.confirmEmail(token);
  }

}
