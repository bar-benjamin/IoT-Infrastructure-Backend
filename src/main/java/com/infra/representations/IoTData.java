package com.infra.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class IoTData {
    @JsonProperty("iot_data")
    private Object iotData;

    public IoTData() {
    }

    public IoTData(Object iotData) {
        this.iotData = iotData;
    }

    public Object getIoTData() {
        return iotData;
    }

    public void setIoTData(Object iotData) {
        this.iotData = iotData;
    }

    @Override
    public String toString() {
        return "IoTData{" +
                "iot_data=" + iotData +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(iotData, o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iotData);
    }
}
