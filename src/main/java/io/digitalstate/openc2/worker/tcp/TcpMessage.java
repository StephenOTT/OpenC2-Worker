package io.digitalstate.openc2.worker.tcp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class TcpMessage {

    private final String address;
    private final Map<String, Object> message;

    @JsonCreator
    public TcpMessage(
            @JsonProperty("address") String address,
            @JsonProperty("message") Map<String, Object> message) {

        this.address = address;
        this.message = message;
    }

    public String getAddress() {
        return address;
    }

    public Map<String, Object> getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "TcpMessage{" +
                "address='" + address + '\'' +
                ", message=" + message +
                '}';
    }
}
