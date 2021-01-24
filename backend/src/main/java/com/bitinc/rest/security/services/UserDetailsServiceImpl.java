package com.bitinc.rest.security.services;

import com.bitinc.rest.repos.UserRepo;
import org.springframework.stereotype.Service;
import com.bitinc.rest.entities.PokeUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.PostConstruct;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepo userRepo;

  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
  public BCryptPasswordEncoder getEncoder() { return encoder; }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    PokeUserEntity user = userRepo.findById(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }

  @PostConstruct
  private void createDefaultUsers(){
    if (userRepo.findById("admin@bitlord69.se").isEmpty()) {
      addUser("admin@bitlord69.se", "123");
    }
  }

  public PokeUserEntity addUser(String username, String password){
    PokeUserEntity user = new PokeUserEntity(username, encoder.encode(password), null);
    try {
      return userRepo.save(user);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

}