package com.bitinc.rest.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/whoami")
//  @ResponseBody
  public String currentUserName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();
    return currentPrincipalName;
  }

  @GetMapping("/user")
//  @Secured({ "ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN" })
//  @RolesAllowed(value = {"USER", "MODERATOR", "ADMIN"})
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/mod")
//  @Secured({ "ROLE_MODERATOR", "ROLE_ADMIN" })
  @PreAuthorize("hasRole('MODERATOR')")
  public String moderatorAccess() {
    return "Moderator (and admin) only content.";
  }

  @GetMapping("/admin")
//  @Secured({"ROLE_ADMIN"})
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin only content";
  }
}