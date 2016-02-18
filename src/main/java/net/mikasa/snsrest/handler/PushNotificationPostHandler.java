package net.mikasa.snsrest.handler;

import net.mikasa.snsclient.SNSClient;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.Vertx;

public class PushNotificationPostHandler implements Handler<Message<JsonObject>>{
	Logger logger = LoggerFactory.getLogger("PushNotificationPostHandler");
	/*
	private Container container;
	public PushNotificationPostHandler(Container container) {
		this.container=container;
	}*/
	
	@Override
	public void handle(Message<JsonObject> message) {
		logger.info("PushNotification Handler Answering, got data: " + message.body());
		String platform = message.body().getString("platform");
		String serverApiKey = message.body().getString("serverApiKey");
		String token = message.body().getString("platformToken");
		String applicationName = message.body().getString("applicationName");
		String payload = message.body().getString("payload");
		
		SNSClient snsClient = new SNSClient(serverApiKey,applicationName,token);
		snsClient.publish(payload);
	}

}
