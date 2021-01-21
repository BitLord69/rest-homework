package com.bitinc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.bitinc.models.PokeResults;
import com.bitinc.repos.PokeRepo;
import org.springframework.data.domain.Pageable;
import com.bitinc.models.PokeResultItem;
import com.bitinc.entities.PokemonEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PokeService {
  private final static String REMOTE_URL = "https://pokeapi.co/api/v2/";

  public List<PokeResultItem> results;
  private final RestTemplate restTemplate = new RestTemplate();

  @Autowired
  private PokeRepo pokeRepo;

  public PokemonEntity save(PokemonEntity newPokemon) {
    return pokeRepo.save(newPokemon);
  }

  public PokemonEntity getPokemonById(long id) {
    Optional<PokemonEntity> optional = pokeRepo.findById(id);
    if (optional.isPresent()) {
      System.out.println("Hittade Pokemon med id " + id + " i Neo, returnerar....");
      return optional.get();
    }

    System.out.println("Hittade *inte* Pokemon med id " + id + " i Neo, söker online!");

    Map pokeMap = restTemplate.getForObject(REMOTE_URL + "pokemon/" + id, Map.class);
    if (pokeMap == null) return null;

    String pokeId = String.format("%03d", (int)pokeMap.get("id"));

    List<String> abilities = ((List<Map<String, Object>>)pokeMap.get("abilities"))
        .stream()
        .map(a -> {
          Map<String, Object> ability = (Map<String, Object>) a.get("ability");
          return (String)ability.get("name");
        }).collect(Collectors.toList());

    List<String> types = ((List<Map<String, Object>>) pokeMap.get("types"))
        .stream()
        .map(t -> {
          Map<String, Object> ability = (Map<String, Object>) t.get("type");
          return (String) ability.get("name");
        }).collect(Collectors.toList());

    String imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/" + pokeId + ".png";

    PokemonEntity pokemon = new PokemonEntity(
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

  public PokeResults getPokemonsList() {
//  public PokeResults getPokemonsList(Pageable pageable) {
    int limit = 100;
    PokeResults results = new PokeResults();
    ArrayList<PokeResultItem> resultList = new ArrayList<>();

    // Check how many pokemons are in the remote api; do this by getting one pokemon
    String query = REMOTE_URL + "pokemon/?limit=1 &offset=0";
    Map response = restTemplate.getForObject(query, Map.class);

    int entriesInRemote = (int) response.get("count");
    long entriesInCache = (long) pokeRepo.count();

    System.out.printf("\nentriesInRemote: %d, entriesInCache: %d\n", entriesInRemote, entriesInCache);

    if (entriesInRemote > 0 &&  entriesInRemote != entriesInCache) {
      Pattern pattern = Pattern.compile("pokemon\\/(\\d+)");

      System.out.println("Cache is not up to date, fetching from remote...");

      String next = REMOTE_URL + "pokemon/?limit=" + limit + "&offset=0";

      do {
        Map res = restTemplate.getForObject(next, Map.class);
        next = (String) res.get("next");

        List respList = (List) res.get("results");
        System.out.printf("next: %s respList.size: %d\n", next, respList.size());
        respList.forEach(o -> {
          PokeResultItem pi = new PokeResultItem((Map<String, String>)o);
          String url = pi.getUrl();
          long id = -1;

           Matcher matcher = pattern.matcher(url);
          if (matcher.find())
          {
            id = Long.parseLong(matcher.group(1));
            getPokemonById(id);
          }

          resultList.add(pi);
        });
      } while (next != null);

      System.out.printf("Finished caching... list contains %d items", resultList.size());
    } else
    {
      List<PokemonEntity> l = pokeRepo.findAll();
      System.out.printf("Getting results from cache.... l.size: %d", l.size());

      l.forEach(o -> {
        PokeResultItem pi = new PokeResultItem(o.getName(), REMOTE_URL + "/pokemon/" + o.getId());
        resultList.add(pi);
      });
    }

    PokeResults pr = new PokeResults();
    pr.setResults(resultList);
    return pr;
  }
}
