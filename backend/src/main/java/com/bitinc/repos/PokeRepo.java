package com.bitinc.repos;

import com.bitinc.entities.PokemonEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface PokeRepo extends Neo4jRepository<PokemonEntity, Long> {
  PokemonEntity findOneById(long id);
}
