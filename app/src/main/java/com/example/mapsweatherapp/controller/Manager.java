package com.example.mapsweatherapp.controller;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.mapsweatherapp.R;
import com.example.mapsweatherapp.model.Ubication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Manager {
    private ArrayList<Ubication> ubications;


    public Manager() {
        this.ubications = new ArrayList<>();
    }

    public ArrayList<Ubication> loadDataSharedPreferences(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("sharedUbications", MODE_PRIVATE);
        String listTask = sharedPref.getString("sharedUbication", null);
        Gson gson = new Gson();
        this.ubications = gson.fromJson(listTask, new TypeToken<ArrayList<Ubication>>() {
        }.getType());
        if (this.ubications == null) {
            this.ubications = new ArrayList<>();
        }
        return this.ubications;
    }



    public void saveDataSharedPreferences(Context context, Ubication ubication) {
        loadDataSharedPreferences(context);
        boolean exist = false;
        if (!this.ubications.isEmpty()) {
            for (Ubication u : this.ubications) {
                if (u.getName().equals(ubication.getName())) {
                    exist = true;
                    break;
                }
            }
        }
        if (!exist) {
            this.ubications.add(ubication);
        }
        SharedPreferences sharedPref = context.getSharedPreferences("sharedUbications", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(this.ubications);
        editor.putString("sharedUbication", json);
        editor.apply();
    }


    public Map<String, Integer> getDataMapIcon() {
        Map<String, Integer> map = new HashMap<String, Integer>();


        map.put("thunderstorm with light rain", R.drawable.thunderstorm);
        map.put("thunderstorm with rain", R.drawable.thunderstorm);
        map.put("thunderstorm with heavy rain", R.drawable.thunderstorm);
        map.put("light thunderstorm", R.drawable.thunderstorm);
        map.put("heavy thunderstorm", R.drawable.thunderstorm);
        map.put("ragged thunderstorm", R.drawable.thunderstorm);
        map.put("thunderstorm with light drizzle", R.drawable.thunderstorm);
        map.put("thunderstorm with drizzle", R.drawable.thunderstorm);
        map.put("thunderstorm with heavy drizzle", R.drawable.thunderstorm);
        map.put("light intensity drizzle", R.drawable.rain);
        map.put("drizzle", R.drawable.rain);
        map.put("heavy intensity drizzle", R.drawable.rain);
        map.put("light intensity drizzle rain", R.drawable.rain);
        map.put("drizzle rain", R.drawable.rain);
        map.put("heavy intensity drizzle rain", R.drawable.rain);
        map.put("shower rain and drizzle", R.drawable.rain);
        map.put("heavy shower rain and drizzle", R.drawable.rain);
        map.put("shower drizzle", R.drawable.rain);
        map.put("light rain", R.drawable.rain);
        map.put("moderate rain", R.drawable.rain);
        map.put("heavy intensity rain", R.drawable.rain);
        map.put("very heavy rain", R.drawable.rain);
        map.put("extreme rain", R.drawable.rain);
        map.put("freezing rain", R.drawable.rain);
        map.put("light intensity shower rain", R.drawable.rain);
        map.put("heavy intensity shower rain", R.drawable.rain);
        map.put("ragged shower rain", R.drawable.rain);
        map.put("light snow", R.drawable.snow);
        map.put("snow", R.drawable.snow);
        map.put("heavy snow", R.drawable.snow);
        map.put("sleet", R.drawable.snow);
        map.put("light shower sleet", R.drawable.snow);
        map.put("shower sleet", R.drawable.snow);
        map.put("light rain and snow", R.drawable.snow);
        map.put("rain and snow", R.drawable.snow);
        map.put("light shower snow", R.drawable.snow);
        map.put("shower snow", R.drawable.snow);
        map.put("heavy shower snow", R.drawable.snow);
        map.put("mist", R.drawable.mist);
        map.put("smoke", R.drawable.mist);
        map.put("haze", R.drawable.mist);
        map.put("sand/dust whirls", R.drawable.mist);
        map.put("fog", R.drawable.mist);
        map.put("sand", R.drawable.mist);
        map.put("volcanic ash", R.drawable.mist);
        map.put("squalls", R.drawable.mist);
        map.put("tornado", R.drawable.mist);
        map.put("clear sky", R.drawable.clear_sky);
        map.put("few clouds", R.drawable.few_clouds);
        map.put("scattered clouds", R.drawable.few_clouds);
        map.put("broken clouds", R.drawable.few_clouds);
        map.put("overcast clouds", R.drawable.few_clouds);
        return map;
    }

    public Map<String, Integer> getColorWeather() {
        Map<String, Integer> map = new HashMap<String, Integer>();


        map.put("thunderstorm with light rain", R.drawable.color_thunderstorm);
        map.put("thunderstorm with rain", R.drawable.color_thunderstorm);
        map.put("thunderstorm with heavy rain", R.drawable.color_thunderstorm);
        map.put("light thunderstorm", R.drawable.color_thunderstorm);
        map.put("heavy thunderstorm", R.drawable.color_thunderstorm);
        map.put("ragged thunderstorm", R.drawable.color_thunderstorm);
        map.put("thunderstorm with light drizzle", R.drawable.color_thunderstorm);
        map.put("thunderstorm with drizzle", R.drawable.color_thunderstorm);
        map.put("thunderstorm with heavy drizzle", R.drawable.color_thunderstorm);
        map.put("light intensity drizzle", R.drawable.color_rain);
        map.put("drizzle", R.drawable.color_rain);
        map.put("heavy intensity drizzle", R.drawable.color_rain);
        map.put("light intensity drizzle rain", R.drawable.color_rain);
        map.put("drizzle rain", R.drawable.color_rain);
        map.put("heavy intensity drizzle rain", R.drawable.color_rain);
        map.put("shower rain and drizzle", R.drawable.color_rain);
        map.put("heavy shower rain and drizzle", R.drawable.color_rain);
        map.put("shower drizzle", R.drawable.color_rain);
        map.put("light rain", R.drawable.color_rain);
        map.put("moderate rain", R.drawable.color_rain);
        map.put("heavy intensity rain", R.drawable.color_rain);
        map.put("very heavy rain", R.drawable.color_rain);
        map.put("extreme rain", R.drawable.color_rain);
        map.put("freezing rain", R.drawable.color_rain);
        map.put("light intensity shower rain", R.drawable.color_rain);
        map.put("heavy intensity shower rain", R.drawable.color_rain);
        map.put("ragged shower rain", R.drawable.color_rain);
        map.put("light snow", R.drawable.color_snow);
        map.put("snow", R.drawable.color_snow);
        map.put("heavy snow", R.drawable.color_snow);
        map.put("sleet", R.drawable.color_snow);
        map.put("light shower sleet", R.drawable.color_snow);
        map.put("shower sleet", R.drawable.color_snow);
        map.put("light rain and snow", R.drawable.color_snow);
        map.put("rain and snow", R.drawable.color_snow);
        map.put("light shower snow", R.drawable.color_snow);
        map.put("shower snow", R.drawable.color_snow);
        map.put("heavy shower snow", R.drawable.color_snow);
        map.put("mist", R.drawable.color_mist);
        map.put("smoke", R.drawable.color_mist);
        map.put("haze", R.drawable.color_mist);
        map.put("sand/dust whirls", R.drawable.color_mist);
        map.put("fog", R.drawable.color_mist);
        map.put("sand", R.drawable.color_mist);
        map.put("volcanic ash", R.drawable.color_mist);
        map.put("squalls", R.drawable.color_mist);
        map.put("tornado", R.drawable.color_mist);
        map.put("clear sky", R.drawable.color_clearsky);
        map.put("few clouds", R.drawable.color_fewclouds);
        map.put("scattered clouds", R.drawable.color_fewclouds);
        map.put("broken clouds", R.drawable.color_fewclouds);
        map.put("overcast clouds", R.drawable.color_fewclouds);
        return map;
    }
}
