package com.bitinc.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class PokeResultItem {
  private String name;
  private String url;

  public PokeResultItem(Map<String, String> values) {
    this.name = values.get("name");
    this.url = values.get("url");
  }
}
