package com.bitinc.rest.configs;

import com.bitinc.rest.repos.UserRepo;
import com.bitinc.rest.entities.PokeUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class PokeUserDetailsService implements UserDetailsService {

  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
  public BCryptPasswordEncoder getEncoder() { return encoder; }

  @Autowired
  private UserRepo userRepo;

  @PostConstruct
  private void createDefaultUsers(){
    if (userRepo.findById("admin@bitlord69.se").isEmpty()) {
      addUser("admin@bitlord69.se", "123", true);
    }
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("In PokeUserDetailsService.loadUserByUsername................. username: " + username);

    Optional<PokeUserEntity> user = userRepo.findById(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User not found by id: " + username);
    }
    System.out.println("Wohooooo - User is found in PokeUserDetailsService.loadUserByUsername. User: " + user.get());

    return toUserDetails(user.get());
  }

  public PokeUserEntity addUser(String username, String password){
    PokeUserEntity user = new PokeUserEntity(username, encoder.encode(password));
    try {
      return userRepo.save(user);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  private UserDetails toUserDetails(PokeUserEntity user) {
    System.out.println("In PokeUserDetailsService.toUserDetails......");
    return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .roles("USER").build();
  }
}