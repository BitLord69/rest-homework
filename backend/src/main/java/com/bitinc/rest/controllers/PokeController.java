package com.bitinc.controllers;

import com.bitinc.models.PokeResults;
import com.bitinc.services.PokeService;
import com.bitinc.entities.PokemonEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

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
  PokeResults getPokemonsList() {
//  PokeResults getPokemonsList(@RequestParam(value="offset", required=false) Integer page, @RequestParam(value="limit", required=false) Integer size) {
    return pokeService.getPokemonsList();
  }

  @PutMapping
  PokemonEntity createOrUpdatePokemon(@RequestBody PokemonEntity newPokemon) {
    return pokeService.save(newPokemon);
  }

//  @PostMapping
//  PokemonEntity addPost(@RequestBody PokemonEntity newPokemon) {
//    return pokeService.save(newPokemon);
//  }

  /* Gör DELETE också! */
}
