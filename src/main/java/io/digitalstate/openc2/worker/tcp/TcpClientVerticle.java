package io.digitalstate.openc2.worker.tcp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.Json;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpClientVerticle extends AbstractVerticle {

    private static Logger logger = LoggerFactory.getLogger(TcpClientVerticle.class);

    //@TODO Add future support to allow a TCP Server to allow the client to get its credentials from another client/server

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        logger.info("Starting Tcp Client Verticle");

        NetClientOptions options = new NetClientOptions()
                .setReconnectAttempts(6)
                .setReconnectInterval(10000)
                .setConnectTimeout(100000)
                .setIdleTimeout(100000);

        //@TODO add these into Verticle configs
        int port = 4321;
        String host = "localhost";

        //@TODO Add circuit breaker support to allow multiple attempts
        connectToTcpClient(options, host, port);

        startFuture.complete();
        logger.info("Tcp Server Client has started");
    }

    private void connectToTcpClient(NetClientOptions tcpClientOptions, String host, int port){
        NetClient tcpClient = vertx.createNetClient(tcpClientOptions);

        tcpClient.connect(port, host, res -> {
            if (res.succeeded()) {
                logger.info("Connected to TCP server: " + res.result().remoteAddress());

                NetSocket socket = res.result();

                socket.handler(data->{
                    String sourceAddress = res.result().remoteAddress().host() + ":" + res.result().remoteAddress().port();

                    logger.info("Received message from TCP server: " + sourceAddress);
                    try {
                        TcpMessage message = Json.decodeValue(data, TcpMessage.class);

                        DeliveryOptions dataDeliveryOpts = new DeliveryOptions()
                                .addHeader("source-tcp-server", sourceAddress);

                        vertx.eventBus().publish(message.getAddress(), message.getMessage(), dataDeliveryOpts);

                    } catch (IllegalArgumentException e){
                        logger.error("Unable to parse data received from TCP server: " + sourceAddress, e);
                    }
                });

            } else {
                logger.error("Failed to connect to TCP Server", res.cause());
            }
        });
    }
}
