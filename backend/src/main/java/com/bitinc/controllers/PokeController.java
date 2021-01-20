package com.bitinc.controllers;

import com.bitinc.entities.PokemonEntity;
import com.bitinc.services.PokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/pokemon")
public class PokeController {
  @Autowired
  private final PokeService pokeService;

  public PokeController(PokeService pokeService) {
    this.pokeService = pokeService;
  }

  @GetMapping("/{id}")
  public PokemonEntity getPokemonById(@PathVariable long id) {
    return pokeService.getPokemonById(id);
  }

  @GetMapping(value = { "", "/" }, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  List<PokemonEntity> getPokemons() {
    return pokeService.findAll();
  }

  @PutMapping
  PokemonEntity createOrUpdatePokemon(@RequestBody PokemonEntity newPokemon) {
    return pokeService.save(newPokemon);
  }
}
