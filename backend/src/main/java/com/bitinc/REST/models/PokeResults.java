package com.bitinc.REST.models;

import lombok.Data;

import java.util.List;

@Data
public class PokeResults {
  private List<PokeResultItem> results;
}
