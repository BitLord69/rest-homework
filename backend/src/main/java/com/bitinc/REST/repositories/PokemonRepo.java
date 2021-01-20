package com.bitinc.REST.repositories;

import com.bitinc.REST.entities.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepo extends JpaRepository<Pokemon, Long> {

}
