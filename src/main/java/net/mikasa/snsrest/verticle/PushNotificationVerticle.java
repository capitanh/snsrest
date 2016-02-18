package net.mikasa.snsrest.verticle;

import net.mikasa.snsrest.handler.PushNotificationPostHandler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.AbstractVerticle;

public class PushNotificationVerticle extends AbstractVerticle {
	Logger logger = LoggerFactory.getLogger("PushNotificationVerticle");
	public void start() {
		
		//JsonObject config = container.config();
		logger.info("Starting PushNotification Verticle");
		PushNotificationPostHandler POSThandler = new PushNotificationPostHandler();
		vertx.eventBus().consumer("PushNotificationPOSTHandler",POSThandler);
	}

}
