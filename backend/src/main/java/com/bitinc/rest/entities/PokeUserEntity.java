package com.bitinc.rest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.HashSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Node("PokeUser")
public class PokeUserEntity {
  @Id
  String username;

  @JsonIgnore
  String password;

  @Relationship(type = "HAS_ROLE", direction = Relationship.Direction.OUTGOING)
  private HashSet<PokeRolesEntity> roles;

  public static boolean isUser(Principal principal){
    org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();
    return u.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"));
  }

  public static boolean isModerator(Principal principal){
    org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();
    return u.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_MODERATOR"));
  }

  public static boolean isAdmin(Principal principal){
    org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();
    return u.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
  }
}
