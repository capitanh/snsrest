package net.mikasa.snsrest.verticle;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.Router;

public class FrontVerticle extends AbstractVerticle {
	Logger logger = LoggerFactory.getLogger("FrontVerticle");
	
	public void start() {
		
		logger.info("Starting front verticle...");

		int port = config().getInteger("port");
		final String PUSH_NOTIFICATION_URI = config().getString("pushNotificationURI");
		final String PLATFORM_ENDPOINT_URI = config().getString("platformEndpointURI");
		final String PUSH_NOTIFICATION_FILE_PROCESS_URI = config().getString("pushNotificationFileProcessURI");
		final String PUSH_NOTIFICATION_FEEDBACK_URI = config().getString("pushNotificationFeedbackURI");
		final String PLATFORM_FEEDBACK_URI = config().getString("platformFeedbackURI");
		
		HttpServer server = vertx.createHttpServer();
		Router router = Router.router(vertx);
		
		listen(router,"post",PUSH_NOTIFICATION_URI,"PushNotificationPOSTHandler");
		listen(router,"put", PLATFORM_ENDPOINT_URI,"PlatformEndpointPUTHandler");
		listen(router,"post",PUSH_NOTIFICATION_FILE_PROCESS_URI,"PushNotificationFileProcessPOSTHandler");
		listen(router,"post",PUSH_NOTIFICATION_FEEDBACK_URI,"PushNotificationFeedbackPOSTHandler");
		listen(router,"post",PLATFORM_FEEDBACK_URI,"PlatformFeedbackPOSTHandler");
		
		//Reject any other request
		/*router.noMatch(new Handler<HttpServerRequest>() {
			public void handle(HttpServerRequest req) {
				logger.info("Invalid method received, returning 501...");
        req.response().setStatusCode(501).end();
			}			
		});
		*/

		server.requestHandler(router::accept).listen(port,"localhost");
		logger.info("Webserver started, listening on port: " + port);
	}
	
	private void listen(Router router, String method, String uri, final String address) {
		
		Handler<HttpServerRequest> handler = new Handler<HttpServerRequest>(){
			public void handle(final HttpServerRequest req) {
				req.bodyHandler(new Handler<Buffer>() {
					public void handle(Buffer event) {
						final String message = event.toString();
						final JsonObject jsonMessage = new JsonObject(message);
						vertx.eventBus().send(address, jsonMessage);
						req.response().setStatusCode(200).end(message);						
					}
				});
			}
		};
		
		if(method.equals("post")) router.route(HttpMethod.POST,uri);
		//if(method.equals("put")) router.put(uri,handler);
		
		logger.info("REST Service listening on " + uri);

	}

}
