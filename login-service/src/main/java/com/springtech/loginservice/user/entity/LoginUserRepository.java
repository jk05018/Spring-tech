package com.springtech.loginservice.user.entity;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginUserRepository extends JpaRepository<LoginUser, Long> {

  Optional<LoginUser> findByEmail(String email);
}
