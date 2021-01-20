package com.bitinc.REST.controllers;


import com.bitinc.REST.entities.Pokemon;
import com.bitinc.REST.models.PokeResultItem;
import com.bitinc.REST.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/rest/v1/pokemon")
class PokemonController {
  @Autowired
  private PokemonService pokeService;

//  @PostConstruct
//  public void init() {
//    pokeService.getPokemonById(25);
//  }

  @GetMapping("/hello")
  public String helloWorld() {
    return "<h1>Hello world</h1>";
  }

  @GetMapping("")
  public List<Pokemon> getAll() {
    return pokeService.getAllPokemon();
  }

  @GetMapping("/{id}")
    public Pokemon getPokemonById(@PathVariable long id) {
      return pokeService.getPokemonById(id);
  }
}
