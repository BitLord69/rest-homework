package com.bitinc.rest.controllers;

import com.bitinc.rest.repos.UserRepo;
import org.springframework.http.HttpStatus;
import com.bitinc.rest.entities.PokeUserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

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
}
