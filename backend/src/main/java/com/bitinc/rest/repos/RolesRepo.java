package com.bitinc.rest.repos;

import com.bitinc.rest.entities.ERoles;
import com.bitinc.rest.entities.PokeRolesEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface RolesRepo extends Neo4jRepository<PokeRolesEntity, ERoles> {
//  Optional<PokeRolesEntity> findByName(ERoles name);
}
