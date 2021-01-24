package com.bitinc.rest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

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
  private List<PokeRolesEntity> roles;
}
