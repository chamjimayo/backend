package com.project.chamjimayo.security.config;

import com.project.chamjimayo.security.AuthIdProvider;
import com.project.chamjimayo.security.CustomUserDetailsService;
import com.project.chamjimayo.security.LoginSuccessHandler;
import com.project.chamjimayo.security.filter.JwtAuthenticationFilter;
import com.project.chamjimayo.security.JwtTokenProvider;
import com.project.chamjimayo.security.filter.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomUserDetailsService customUserDetailsService;
  private final JwtTokenProvider jwtTokenProvider;

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter(jwtTokenProvider, customUserDetailsService);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .csrf().disable()
        .headers().frameOptions().disable()
        .and()
        .httpBasic().disable()
        .formLogin().disable();

    http.authorizeRequests(auth -> auth
            .antMatchers("/api/signup").permitAll()
            .antMatchers("/api/login").permitAll()
        );

    http.authenticationProvider(new AuthIdProvider(customUserDetailsService));

    http.userDetailsService(customUserDetailsService);

    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    http.apply(new CustomDsl());

    return http.build();
  }
  public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) {
      AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

      LoginFilter loginFilter = new LoginFilter();
      loginFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler(jwtTokenProvider));
      loginFilter.setAuthenticationManager(authenticationManager);

      http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);
    }
  }
}
