package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Pokemons")
public class Pokemon {
  @Id
  private long id;
  private String name;
  private String ability;
  private String type;
  private String imageUrl;
  private int height;
  private int weight;
}
