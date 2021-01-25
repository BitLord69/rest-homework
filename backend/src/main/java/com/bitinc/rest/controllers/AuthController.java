package com.bitinc.rest.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.bitinc.rest.repos.UserRepo;
import com.bitinc.rest.entities.ERoles;
import com.bitinc.rest.repos.RolesRepo;
import com.bitinc.rest.security.jwt.JwtUtils;
import org.springframework.http.ResponseEntity;
import com.bitinc.rest.entities.PokeUserEntity;
import com.bitinc.rest.entities.PokeRolesEntity;
import org.springframework.web.bind.annotation.*;
import com.bitinc.rest.payload.response.JwtResponse;
import com.bitinc.rest.payload.request.LoginRequest;
import com.bitinc.rest.payload.request.SignupRequest;
import com.bitinc.rest.payload.response.MessageResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import com.bitinc.rest.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.bitinc.rest.security.services.UserDetailsServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepo userRepository;

  @Autowired
  RolesRepo roleRepository;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
    List<String> roles = userDetails
                          .getAuthorities()
                          .stream()
                          .map(GrantedAuthority::getAuthority)
                          .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

    if (userRepository.findById(signUpRequest.getUsername()).isPresent()) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    Set<String> strRoles = signUpRequest.getRole();
    HashSet<PokeRolesEntity> roles = new HashSet<>();

    if (strRoles == null) {
      PokeRolesEntity userRole = roleRepository.findById(ERoles.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "ADMIN" -> {
            PokeRolesEntity adminRole = roleRepository.findById(ERoles.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
          }
          case "MODERATOR" -> {
            PokeRolesEntity modRole = roleRepository.findById(ERoles.ROLE_MODERATOR)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(modRole);
          }
          default -> {
            PokeRolesEntity userRole = roleRepository.findById(ERoles.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
          }
        }
      });
    }

    // Create new user's account
    PokeUserEntity user = new PokeUserEntity(signUpRequest.getUsername(),
        userDetailsService.getEncoder().encode(signUpRequest.getPassword()), roles);

    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @GetMapping("/whoami")
  public ResponseEntity whoAmI() {
    System.out.println("Hello from whoami!");
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    if (username.equals("anonymousUser")) {
      return ResponseEntity.ok(new NotLoggedInError());
    }
    return ResponseEntity.ok(userRepository.findById(username).get());
  }
}