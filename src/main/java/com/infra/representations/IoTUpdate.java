package com.infra.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IoTUpdate<?> ioTUpdate = (IoTUpdate<?>) o;
        return Objects.equals(iotData, ioTUpdate.iotData) && Objects.equals(timestamp, ioTUpdate.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iotData, timestamp);
    }
}
