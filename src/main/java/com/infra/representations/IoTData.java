package com.infra.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IoTData<T> {
    @JsonProperty("iot_data")
    private T iotData;

    public IoTData() {
    }

    public IoTData(T iotData) {
        this.iotData = iotData;
    }

    public T getIoTData() {
        return iotData;
    }

    public void setIoTData(T iotData) {
        this.iotData = iotData;
    }

    @Override
    public String toString() {
        return "IoTData{" +
                "iot_data=" + iotData +
                '}';
    }
}
