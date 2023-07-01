package com.infra.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class IoTUpdate<T> {
    @JsonProperty
    private T iotData;

    @JsonProperty
    private String timestamp;

    public IoTUpdate() {}

    public IoTUpdate(T iotData, Instant timestamp) {
        this.iotData = iotData;
        this.timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault()).format(timestamp);
    }

    public T getIotData() {
        return iotData;
    }

    public void setIotData(T iotData) {
        this.iotData = iotData;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
