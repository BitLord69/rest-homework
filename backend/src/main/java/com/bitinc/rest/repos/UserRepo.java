package com.bitinc.rest.repos;

import com.bitinc.rest.entities.PokeUserEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepo extends Neo4jRepository<PokeUserEntity, String> {
}

