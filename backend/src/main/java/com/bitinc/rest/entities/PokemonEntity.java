package com.bitinc.rest.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Node("Pokemon")
public class PokemonEntity {
  @Id
  private long id;
  private String name;
  private String ability;
  private String type;
  private String imageUrl;
  private int height;
  private int weight;

/*
  @Relationship(type = "ACTED_IN", direction = INCOMING)
  private Set<PersonEntity> actors = new HashSet<>();
  @Relationship(type = "DIRECTED", direction = INCOMING)
  private Set<PersonEntity> directors = new HashSet<>();
*/
}
