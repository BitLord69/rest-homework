package com.bitinc.rest.repos;

import com.bitinc.rest.entities.PokemonEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;


public interface PokeRepo extends Neo4jRepository<PokemonEntity, Long> {
  @Query(""
    + "MATCH (p:Pokemon) WHERE p.name = $name RETURN p "
    + ":#{orderBy(#pageable)} SKIP $skip LIMIT $limit"
)
  Slice<PokemonEntity> findSliceByName(String name, Pageable pageable);

}
