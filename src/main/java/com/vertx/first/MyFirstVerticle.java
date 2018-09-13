package com.vertx.first;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyFirstVerticle extends AbstractVerticle {

  private Map<Integer, Animal> animals = new LinkedHashMap<>();

  private void seedAnimals() {
    Animal dog = new Animal("Dog", "Woof");
    animals.put(dog.getId(), dog);
    Animal cat = new Animal("Cat", "Meow");
    animals.put(cat.getId(), cat);
  }

  private void getAnimals(RoutingContext routingContext) {
    routingContext.response()
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(animals.values()));
  }

  private void createAnimal(RoutingContext routingContext) {
    final Animal animal = Json.decodeValue(routingContext.getBodyAsString(), Animal.class);
    animals.put(animal.getId(), animal);
    routingContext.response()
        .setStatusCode(201)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(animal));
  }

  private void killAnimal(RoutingContext routingContext) {
    String id = routingContext.request().getParam("id");

    if (id == null) {
      routingContext.response().setStatusCode(400).end();
    } else {
      Integer idAsInteger = Integer.valueOf(id);
      animals.remove(idAsInteger);
    }

    routingContext.response().setStatusCode(204).end();
  }

  @Override
  public void start(Future<Void> future) {

    seedAnimals();

    Router router = Router.router(vertx);

    router.route("/").handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      response
          .putHeader("content-type", "text/html")
          .end("<h1>Welcome to the world's worst API</h1>");
    });

    router.route("/assets/*").handler(StaticHandler.create("assets"));

    router.get("/api/animals").handler(this::getAnimals);
    router.route("/api/animals*").handler(BodyHandler.create());
    router.post("/api/animals").handler(this::createAnimal);
    router.delete("/api/animals/:id").handler(this::killAnimal);

    vertx
        .createHttpServer()
        .requestHandler(router::accept)
        .listen(
          config().getInteger("http.port", 8080),
          result -> {
            if (result.succeeded()) {
              future.complete();
            } else {
              future.fail(result.cause());
            }
          }
        );
  }

}
