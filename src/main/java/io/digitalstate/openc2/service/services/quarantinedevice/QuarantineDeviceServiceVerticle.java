package io.digitalstate.openc2.service.services.quarantinedevice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class QuarantineDeviceServiceVerticle extends AbstractVerticle {

    private static Logger logger = LoggerFactory.getLogger(QuarantineDeviceServiceVerticle.class);

    /**
     * Consumer Address namespace used by Event Bus
     */
    private static final String CONSUMER_ADDRESS_BASE = "openc2.action.contain";
    private static final String CONSUMER_ADDRESS = CONSUMER_ADDRESS_BASE + ".device";

    /**
     * Startup the Service
     * @param startFuture
     * @throws Exception
     */
    @Override
    public void start(Future<Void> startFuture) throws Exception {
        logger.info("Starting Quarantine Device Service Verticle: " + deploymentID());

        setupActionEventBusConsumer().setHandler(res -> {
            if (res.failed()) {
                logger.error("Unable to start Quarantine Device Service Verticle", res.cause());
                startFuture.fail(res.cause());
            } else {
                startFuture.complete();
                logger.info("Quarantine Device Service Verticle has successfully started: " + deploymentID());
            }
        });
    }

    /**
     * Register Event Bus Handler for consuming requests for the CONSUMER_ADDRESS
     * @return
     */
    private Future<Void> setupActionEventBusConsumer() {
        Future<Void> completionFuture = Future.future();

        MessageConsumer<JsonObject> consumer = vertx.eventBus().consumer(CONSUMER_ADDRESS);

        consumer.handler(message -> {
            //CALL THE ACTUAL WORKER!
            executeAction(message.body());

        }).exceptionHandler(e -> {
            logger.error("Unable to register consumer for " + CONSUMER_ADDRESS, e);
            completionFuture.fail(e);

        }).completionHandler(comp -> {
            logger.info("Consumer registered for " + CONSUMER_ADDRESS);
            completionFuture.complete();

        });

        return completionFuture;
    }

    /**
     * Execute work
     * @param message
     * @return
     */
    private Future<Void> executeAction(JsonObject message) {
        //@TODO CONSIDER REBUILDING THIS AS A EVENT BUS HANDLER SO OTHER VERTICLES CAN CONSUME
        Future<Void> completionFuture = Future.future();
        // DO WORK HERE

        return completionFuture;
    }

    /**
     * Return a response over the event bus to the Orchestrator
     * @param responseAddress
     * @param responseMessage
     * @return
     */
    private Future<Void> sendActionResponse(String responseAddress, Map<String, Object> responseMessage) {
        Future<Void> completionFuture = Future.future();
        // DO WORK HERE

        vertx.eventBus().publish(responseAddress, responseMessage);

        return completionFuture;
    }

}
