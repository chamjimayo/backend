package com.project.chamjimayo.controller.dto;

import lombok.Getter;

@Getter
public enum ErrorCode {
  INVALID_PARAMETER("01"),
  NEED_MORE_PARAMETER("02"),
  USER_DUPLICATE_EXCEPTION("03"),
  USER_NICK_NAME_DUPLICATE_EXCEPTION("04"),
  AUTH_EXCEPTION("05"),
  INVALID_TOKEN_EXCEPTION("06"),
  AUTHENTICATION_EXCEPTION("07"),
  USER_NOT_FOUND_EXCEPTION("08"),
  SEARCH_NOT_FOUND("09"),
  API_NOT_FOUND("10"),
  JSON_NOT_FOUND("11"),
  METHOD_NOT_ALLOWED_EXCEPTION("12"),
  METHOD_ARGUMENT_NOT_VALID_EXCEPTION("13"),
  INTERNAL_SERVER_ERROR("14"),
  DATABASE_ERROR("15"),
  REVIEW_NOT_FOUND("16"),
  RESTROOM_NOT_FOUND("17");

  private final String code;

  ErrorCode(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return code;
  }
}
