package com.bitinc.rest.repos;

import com.bitinc.rest.entities.ERoles;
import com.bitinc.rest.entities.PokeRolesEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface RolesRepo extends Neo4jRepository<PokeRolesEntity, Long> {
  Optional<PokeRolesEntity> findByName(ERoles name);
}
