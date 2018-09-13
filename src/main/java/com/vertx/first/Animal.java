package com.vertx.first;

import java.util.concurrent.atomic.AtomicInteger;

public class Animal {

  static final AtomicInteger COUNTER = new AtomicInteger();

  private final int id;

  private String species;

  private String noise;

  public Animal(String species, String noise) {
    this.id = COUNTER.getAndIncrement();
    this.species = species;
    this.noise = noise;
  }

  public Animal() {
    this.id = COUNTER.getAndIncrement();
  }

  public int getId() {
    return id;
  }

  public String getSpecies() {
    return species;
  }

  public String getNoise() {
    return noise;
  }

  public void setSpecies(String species) {
    this.species = species;
  }

  public void setNoise(String noise) {
    this.noise = noise;
  }

}
