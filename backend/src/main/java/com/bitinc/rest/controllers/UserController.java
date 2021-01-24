package com.bitinc.rest.controllers;

import com.bitinc.rest.repos.UserRepo;
import org.springframework.http.HttpStatus;
import com.bitinc.rest.entities.PokeUserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RestController
public class UserController {
  @Autowired
  private UserRepo userRepo;

  @GetMapping("/rest/v1/user/")
  public ResponseEntity<List<PokeUserEntity>> getAllUsers() {
    return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
  }

  @GetMapping("/rest/v1/user/{id}")
  public ResponseEntity<PokeUserEntity> getUserById(@PathVariable String id) {
    var res = userRepo.findById(id);
    System.out.println("getUserById, res: " + res);
    return new ResponseEntity<>(res.get(), HttpStatus.OK);
  }

  @GetMapping("/api/whoami")
  public ResponseEntity whoAmI() {
    System.out.println("Hello from whoami!");
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    if (username.equals("anonymousUser")) {
      return ResponseEntity.ok(new NotLoggedInError());
    }
    return ResponseEntity.ok(userRepo.findById(username).get());
  }

  @PostMapping("/api/register")
  ResponseEntity addPost(@RequestBody PokeUserEntity user) {
    userRepo.save(user);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  public boolean isCurrentUser(String userName) {
    PokeUserEntity user = (PokeUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return (userName.equals(user.getUsername()));
  }

}
