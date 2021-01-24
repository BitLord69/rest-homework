package com.bitinc.rest.security.services;

import com.bitinc.rest.repos.UserRepo;
import com.bitinc.rest.entities.PokeUserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.bitinc.rest.configs.PokeUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl extends BaseService {
  private static final long serialVersionUID = 1L;

  private final String username;

  @JsonIgnore
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private PokeUserDetailsService myUserDetailsService;

  public UserDetailsImpl(String username, String password,
                         Collection<? extends GrantedAuthority> authorities) {
    this.username = username;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(PokeUserEntity user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

    return new UserDetailsImpl(
        user.getUsername(),
        user.getPassword(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }


  public PokeUserEntity findCurrentUser() {
    // the login session is stored between page reloads,
    // and we can access the current authenticated user with this
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepo.findById(username).get();
  }

  public PokeUserEntity registerUser(PokeUserEntity user) {
    return myUserDetailsService.addUser(user.getUsername(), user.getPassword());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(username, user.username);
  }
}
