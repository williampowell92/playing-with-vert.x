package com.vertx.first;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class MyFirstVerticle extends AbstractVerticle {

  @Override
  public void start(Future<Void> future) {
    vertx
        .createHttpServer()
        .requestHandler(r -> {
          r.response().end("<h1>Look at me, I'm Mr Meeseeks!</h1>");
        })
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
