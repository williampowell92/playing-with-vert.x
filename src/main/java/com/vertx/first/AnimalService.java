package com.vertx.first;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import java.util.LinkedHashMap;
import java.util.Map;

public class AnimalService {

  static Map<Integer, Animal> animals = new LinkedHashMap<>();

  void seed() {
    Animal dog = new Animal("Dog", "Woof");
    animals.put(dog.getId(), dog);
    Animal cat = new Animal("Cat", "Meow");
    animals.put(cat.getId(), cat);
  }

  void getAll(RoutingContext routingContext) {
    routingContext.response()
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(animals.values()));
  }

  void create(RoutingContext routingContext) {
    final Animal animal = Json.decodeValue(routingContext.getBodyAsString(), Animal.class);
    animals.put(animal.getId(), animal);
    routingContext.response()
        .setStatusCode(201)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(animal));
  }

  void deleteOne(RoutingContext routingContext) {
    String id = routingContext.request().getParam("id");

    if (id == null) {
      routingContext.response().setStatusCode(400).end();
    } else {
      Integer idAsInteger = Integer.valueOf(id);
      animals.remove(idAsInteger);
    }

    routingContext.response().setStatusCode(204).end();
  }

}
