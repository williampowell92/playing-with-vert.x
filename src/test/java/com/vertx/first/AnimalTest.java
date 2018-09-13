package com.vertx.first;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AnimalTest {
  private Animal firstAnimnal;
  private Animal secondAnimal;

  @Before
  public void setUp() {
    Animal.COUNTER.set(0);
  }

  @Test
  public void emptyConstructorWorks() {
    firstAnimnal = new Animal();

    assertTrue("Animal ID is not 0", firstAnimnal.getId() == 0);
  }

  @Test
  public void idsIncrementConcurrently() {
    firstAnimnal = new Animal();
    secondAnimal = new Animal();

    assertEquals("First animals ID is not 0", firstAnimnal.getId(), 0);
    assertEquals("Second animals ID is not 1", secondAnimal.getId(), 1);
  }

  @Test
  public void firstAnimnalCanBeCreatedWithSpecies() {
    firstAnimnal = new Animal("Dog", "Woof");

    assertEquals(firstAnimnal.getSpecies(), "Dog");
  }

  @Test
  public void firstAnimnalCanBeCreatedWithNoise() {
    firstAnimnal = new Animal("Dog", "Woof");

    assertEquals(firstAnimnal.getNoise(), "Woof");
  }

}