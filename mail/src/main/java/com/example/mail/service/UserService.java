package com.example.mail.service;

import com.example.mail.entity.ConfirmToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserInfoRepository userInfoRepository;
  private final ConfirmTokenService confirmTokenService;

  public void confirmEmail(String token){
    ConfirmToken findConfirmToken = confirmTokenService.findByIdAndExpirationDateAfterAndExpired(
        token);
    UserInfo findUserInfo = userInfoRepository.findById(findConfirmToken.getUserId());
    findConfirmToken.userToken(); // 토큰 만료 로직 구현해주면 된다. ex) expired 값을 true로 변경
    findUserInfo.emailVerifiedSuccess();// 유저의 이메일 인증 값 변경 로직을 구현해주면 된다. ex) emailVerified 값을 true로 변경

  }

}
