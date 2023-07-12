package com.project.chamjimayo.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
  private String authId;

  public LoginRequest(String authId) {
    this.authId = authId;
  }
}
