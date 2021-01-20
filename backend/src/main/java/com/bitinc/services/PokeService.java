package com.bitinc.services;

import java.util.List;
import com.bitinc.repos.PokeRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.bitinc.models.PokeResultItem;
import com.bitinc.entities.PokemonEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PokeService {
  public List<PokeResultItem> results;
  private final RestTemplate restTemplate = new RestTemplate();

  @Autowired
  private PokeRepo pokeRepo;

  public PokemonEntity save(PokemonEntity newPokemon) {
    return pokeRepo.save(newPokemon);
  }

  public PokemonEntity getPokemonById(long id) {
    return pokeRepo.findOneById(id);
  }

  public List<PokemonEntity> findAll() {
    return pokeRepo.findAll();
  }
}
