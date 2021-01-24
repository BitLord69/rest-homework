package com.bitinc.rest.controllers;

import com.bitinc.rest.models.PokeResults;
import com.bitinc.rest.security.services.PokeService;
import com.bitinc.rest.entities.PokemonEntity;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<PokemonEntity> getPokemonById(@PathVariable long id) {
    var res = pokeService.getPokemonById(id);
    System.out.println("getPokemonById, res: " + res);
    return res;
  }

  @GetMapping(value = { "", "/" })
  ResponseEntity<PokeResults> getPokemonsList() {
//  PokeResults getPokemonsList(@RequestParam(value="offset", required=false) Integer page, @RequestParam(value="limit", required=false) Integer size) {
    return pokeService.getPokemonsList();
  }
}
