package com.springtech.loginservice.web;

import com.springtech.loginservice.config.auth.LoginUser;
import com.springtech.loginservice.config.auth.SessionUser;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

  private final HttpSession httpSession;

  @GetMapping("/")
  public String index(Model model, @LoginUser SessionUser user) {

    if (user != null) {
      model.addAttribute("userName", user.getName());
    }

    return "index";
  }

}
