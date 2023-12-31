package com.project.chamjimayo.security;

import com.project.chamjimayo.repository.domain.entity.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
  private final Long id;
  private final Collection<GrantedAuthority> authorities = new ArrayList<>();

  private CustomUserDetails(Long id, Role role) {
    this.id = id;
    authorities.add(new SimpleGrantedAuthority(role.toString()));
  }

  public static CustomUserDetails create(Long id, Role role) {
    return new CustomUserDetails(id, role);
  }

  public Long getId() {
    return id;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.unmodifiableCollection(authorities);
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}
