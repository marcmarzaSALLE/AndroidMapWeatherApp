package com.example.mapsweatherapp.model;

public class WeatherUbication {
    String name;
    String weather;
    String description;
    double temp;

    int humidity;
    double lat;
    double lon;
    double windSpeed;
    int maxTemp;
    int minTemp;


    public WeatherUbication(String name,double lon,double lat ,String weather, String description, double temp, int humidity, double windSpeed, int maxTemp, int minTemp) {
        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.weather = weather;
        this.description = description;
        this.temp = temp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getName() {
        return name;
    }

    public String getWeather() {
        return weather;
    }

    public String getDescription() {
        return description;
    }

    public double getTemp() {
        return temp;
    }
}
