package com.example.mapsweatherapp.model;

import java.util.ArrayList;

public class WeatherUbication {
    private String name;
    private String main;
    private double temp;
    private double maxTemp;
    private double minTemp;

    private ArrayList<WeatherForecast> weatherForecasts = new ArrayList<>();


    public WeatherUbication(String name, String main, double temp, double maxTemp, double minTemp, ArrayList<WeatherForecast> weatherForecasts) {
        this.name = name;
        this.main = main;
        this.temp = temp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.weatherForecasts = weatherForecasts;
    }

    public ArrayList<WeatherForecast> getWeatherForecasts() {
        return weatherForecasts;
    }


    public double getMaxTemp() {
        return maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }


    public String getName() {
        return name;
    }


    public String getMain() {
        return main;
    }

    public double getTemp() {
        return temp;
    }
}
