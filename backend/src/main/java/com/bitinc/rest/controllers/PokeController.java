package com.bitinc.rest.controllers;

import com.bitinc.rest.models.PokeResultItem;
import com.bitinc.rest.models.PokeResults;
import com.bitinc.rest.services.PokeService;
import com.bitinc.rest.entities.PokemonEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/pokemon")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PokeController {
  @Autowired
  private final PokeService pokeService;

  public PokeController(PokeService pokeService) {
    this.pokeService = pokeService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<PokemonEntity> getPokemonById(@PathVariable long id) {
    var res = pokeService.getPokemonById(id);
    System.out.println("getPokemonById, res: " + res);
    return res;
  }

  @GetMapping(value = { "", "/" })
  ResponseEntity<List<PokeResultItem>> getPokemonsList() {
//  PokeResults getPokemonsList(@RequestParam(value="offset", required=false) Integer page, @RequestParam(value="limit", required=false) Integer size) {
    return pokeService.getPokemonsList();
  }

  @PutMapping
  ResponseEntity<PokemonEntity> createOrUpdatePokemon(@RequestBody PokemonEntity newPokemon) {
    return pokeService.save(newPokemon);
  }

//  @PostMapping
//  PokemonEntity addPost(@RequestBody PokemonEntity newPokemon) {
//    return pokeService.save(newPokemon);
//  }

  /* Gör DELETE också! */
}
