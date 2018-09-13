package com.vertx.first;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class MyFirstVerticle extends AbstractVerticle {

  private AnimalService animalService = new AnimalService();

  @Override
  public void start(Future<Void> future) {

    Router router = Router.router(vertx);

    router.route("/").handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      response
          .putHeader("content-type", "text/html")
          .end("<h1>Welcome to the world's worst API</h1>");
    });

    router.route("/assets/*").handler(StaticHandler.create("assets"));

    router.get("/api/animals").handler(animalService::getAll);
    router.route("/api/animals*").handler(BodyHandler.create());
    router.post("/api/animals").handler(animalService::create);
    router.delete("/api/animals/:id").handler(animalService::deleteOne);

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
