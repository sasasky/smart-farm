package com.example.myapplication.entity;

public class landInfo {
    private int landId;
    private int temperature;
    private int humidity;
    private int light;
    private int weather;

    public landInfo() {
    }

    public landInfo(int landId, int temperature, int humidity, int light, int weather) {
        this.landId = landId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.light = light;
        this.weather = weather;
    }

    public int getlandId() {
        return landId;
    }

    public void setlandId(int landId) {
        this.landId = landId;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getWeather() {
        return weather;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }
}
