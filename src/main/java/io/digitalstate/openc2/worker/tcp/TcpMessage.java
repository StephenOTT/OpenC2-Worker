package io.digitalstate.openc2.worker.tcp;

import io.vertx.core.json.JsonObject;

public class TcpMessage {

    private final String address;
    private final JsonObject message;

    public TcpMessage(String address, JsonObject message) {
        this.address = address;
        this.message = message;
    }

    public String getAddress() {
        return address;
    }

    public JsonObject getMessage() {
        return message;
    }
}
