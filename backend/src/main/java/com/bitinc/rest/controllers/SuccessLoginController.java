package com.bitinc.rest.controllers;

import com.bitinc.rest.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/successlogin")
public class SuccessLoginController{
  @Autowired
  private UserRepo userRepo;

  @GetMapping()
  public ResponseEntity loggedIn(){
    System.out.println("Hello from loggedInOK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    if (username.equals("anonymousUser")) {
      return ResponseEntity.ok(new NotLoggedInError());
    }
    return ResponseEntity.ok(userRepo.findById(username).get());
  }
}