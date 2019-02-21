package io.digitalstate.openc2.worker;

import io.digitalstate.openc2.worker.tcp.TcpClientVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

    private static Logger logger = LoggerFactory.getLogger(MainVerticle.class);

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        logger.info("Starting OpenC2 Worker Main Verticle");

        startFuture.complete();
        logger.info("OpenC2 Worker Main Verticle has started");

        startTcpClient();

    }

    public void startTcpClient(){
        DeploymentOptions options = new DeploymentOptions();
        vertx.deployVerticle(TcpClientVerticle.class, options, res ->{
            if (res.failed()){
                logger.error("TCP Client Verticle Failed to start: ", res.cause());
            }
        });
    }
}
