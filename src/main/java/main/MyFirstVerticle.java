// https://github.com/cescoffier/introduction-to-vert.x/blob/master/post-4/src/main/java/io/vertx/intro/first/MyFirstVerticle.java
//https://vertx.io/docs/guide-for-java-devs/#_a_word_on_vert_x_promise_future_objects_and_callbacks
package main;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;


public class MyFirstVerticle extends AbstractVerticle {
	static int funny = 99;
	@Override
	public void start(Promise<Void> promise) {
		Router router = Router.router(vertx);	
		Route route = router.route("/");
		
		route.handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.putHeader("content-type", "text/html");
			response.end("<h1>Hello!</h1>");
		});
		
		router.route("/assets/*").handler(StaticHandler.create("assets"));
		
		HttpServer server = vertx.createHttpServer();
		server.requestHandler(router::handle);
		server.listen(
				8888,
				result -> {
			if(result.succeeded()) {
				promise.complete();
			}
			else {
				promise.fail(result.cause());
			}
		});
	}
}
