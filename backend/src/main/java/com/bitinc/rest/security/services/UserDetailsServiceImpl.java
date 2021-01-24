package com.bitinc.rest.security.services;

import com.bitinc.rest.repos.UserRepo;
import com.bitinc.rest.entities.ERoles;
import com.bitinc.rest.repos.RolesRepo;
import org.springframework.stereotype.Service;
import com.bitinc.rest.entities.PokeUserEntity;
import com.bitinc.rest.entities.PokeRolesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;

import javax.annotation.PostConstruct;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepo userRepo;

  @Autowired
  RolesRepo rolesRepo;

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
  private void createStartupEntities(){
    Optional<PokeRolesEntity> userRoleOpt = rolesRepo.findById(ERoles.ROLE_USER);
    Optional<PokeRolesEntity> moderatorRoleOpt = rolesRepo.findById(ERoles.ROLE_MODERATOR);
    Optional<PokeRolesEntity> adminRoleOpt = rolesRepo.findById(ERoles.ROLE_ADMIN);

    PokeRolesEntity userRole;
    PokeRolesEntity moderatorRole;
    PokeRolesEntity adminRole;

    if (userRoleOpt.isEmpty()) {
      userRole = rolesRepo.save(new PokeRolesEntity(ERoles.ROLE_USER));
    } else {
      userRole = userRoleOpt.get();
    }

    if (moderatorRoleOpt.isEmpty()) {
      moderatorRole = rolesRepo.save(new PokeRolesEntity(ERoles.ROLE_MODERATOR));
    } else {
      moderatorRole = moderatorRoleOpt.get();
    }

    if (adminRoleOpt.isEmpty()) {
      adminRole = rolesRepo.save(new PokeRolesEntity(ERoles.ROLE_ADMIN));
    } else {
      adminRole = adminRoleOpt.get();
    }

    HashSet<PokeRolesEntity> roles = new HashSet<>();
    roles.add(userRole);
    roles.add(moderatorRole);
    roles.add(adminRole);

    if (userRepo.findById("admin@bitlord69.se").isEmpty()) {
      addUser("admin@bitlord69.se", "123", roles);
    }

    roles.remove(adminRole);
    if (userRepo.findById("moderator@bitlord69.se").isEmpty()) {
      addUser("moderator@bitlord69.se", "123", roles);
    }

    roles.remove(moderatorRole);
    if (userRepo.findById("user@bitlord69.se").isEmpty()) {
      addUser("user@bitlord69.se", "123", roles);
    }
  }

  public PokeUserEntity addUser(String username, String password, HashSet<PokeRolesEntity> roles){
    PokeUserEntity user = new PokeUserEntity(username, encoder.encode(password), roles);
    try {
      return userRepo.save(user);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

}