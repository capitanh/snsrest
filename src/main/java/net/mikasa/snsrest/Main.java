package net.mikasa.snsrest;

import net.mikasa.snsrest.verticle.*;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class Main extends AbstractVerticle {
	
	private Logger logger = LoggerFactory.getLogger("Main");
	
	public void start() {
		logger.info("Starting REST API verticles...");
		JsonObject config = config().getJsonObject("net.mikasa.snsrest.verticle.FrontVerticle");
		DeploymentOptions options = new DeploymentOptions().setConfig(config);
		vertx.deployVerticle(FrontVerticle.class.getCanonicalName(),options);
		vertx.deployVerticle(PushNotificationVerticle.class.getCanonicalName());
	}

}