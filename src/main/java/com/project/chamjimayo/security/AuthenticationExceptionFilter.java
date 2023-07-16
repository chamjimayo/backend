package com.project.chamjimayo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.ErrorStatus;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.exception.InvalidTokenException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class AuthenticationExceptionFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (InvalidTokenException e) {
      ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.INVALID_TOKEN_EXCEPTION,
          e.getMessage());
      createApiErrorResponse(response, errorResponse);
    } catch (BadCredentialsException e) {
      ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.AUTHENTICATION_EXCEPTION,
          e.getMessage());
      createApiErrorResponse(response, errorResponse);
    }
  }

  private static void createApiErrorResponse(HttpServletResponse response, ErrorResponse errorResponse)
      throws IOException {
    ApiStandardResponse<ErrorResponse> apiResponse = ApiStandardResponse.fail(errorResponse);
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("utf-8");
    response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
  }
}
