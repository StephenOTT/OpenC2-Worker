package io.digitalstate.openc2.worker.tcp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.json.JsonObject;

public class TcpMessage {

    private final String address;
    private final Object message;
    private final Object metadata;

    @JsonCreator
    public TcpMessage(
            @JsonProperty("address") String address,
            @JsonProperty("message") Object message,
            @JsonProperty("metadata") Object metadata) {

        this.address = address;
        this.message = message;
        this.metadata = metadata;
    }

    public String getAddress() {
        return address;
    }

    public JsonObject getMessage() {
        return JsonObject.mapFrom(message);
    }

    public JsonObject getmetadata() {
        return JsonObject.mapFrom(metadata);
    }

    public JsonObject toJsonObject() {
        return JsonObject.mapFrom(this);
    }
}
