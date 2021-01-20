package com.bitinc.REST.services;

import com.bitinc.REST.entities.Pokemon;
import com.bitinc.REST.models.PokeResultItem;
import com.bitinc.REST.repositories.PokemonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PokemonService {
  public List<PokeResultItem> results;
  private final RestTemplate restTemplate = new RestTemplate();

  @Autowired
  private PokemonRepo pokeRepo;

  public List<Pokemon> getAllPokemon() {
    ArrayList<Pokemon> list = new ArrayList<>();

    for (int i = 1; i < 152; i++) {
      list.add(getPokemonById(i));
    }
    return list;
  }

  public Pokemon getPokemonById(long id) {
    Optional<Pokemon> optional = pokeRepo.findById(id);
    if (optional.isPresent()) {
      return optional.get();
    }

    Map<String, Object> pokeMap = restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/" + id, Map.class);
    if (pokeMap == null) return null;

    String pokeId = String.format("%03d", (int)pokeMap.get("id"));

    List<String> abilities = ((List<Map<String, Object>>) pokeMap.get("abilities"))
        .stream()
        .map(a -> {
          Map<String, Object> ability = (Map<String, Object>) a.get("ability");
          return (String) ability.get("name");
        }).collect(Collectors.toList());

    List<String> types = ((List<Map<String, Object>>) pokeMap.get("types"))
        .stream()
        .map(t -> {
          Map<String, Object> ability = (Map<String, Object>) t.get("type");
          return (String) ability.get("name");
        }).collect(Collectors.toList());

    String imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/" + pokeId + ".png";

    Pokemon pokemon = new Pokemon(
        (int)pokeMap.get("id"),
        (String)pokeMap.get("name"),
        abilities.get(0),
        types.get(0),
        imageUrl,
        (int)pokeMap.get("height"),
        (int)pokeMap.get("weight")
        );

    pokeRepo.save(pokemon);

    return pokemon;
  }
}
