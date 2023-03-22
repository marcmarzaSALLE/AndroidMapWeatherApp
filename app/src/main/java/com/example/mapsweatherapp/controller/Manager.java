package com.example.mapsweatherapp.controller;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


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


    public void saveDataSharedPreferencesCurrentUbication(Context context, Ubication ubication) {
        SharedPreferences sharedPref = context.getSharedPreferences("sharedUbicationsCurrent", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ubication);
        editor.putString("sharedUbicationCurrent", json);
        Log.wtf("sharedUbicationCurrent", json);
        editor.apply();
    }

    public Ubication getCurrentUbication(Context context){
        Gson gson = new Gson();
        SharedPreferences sharedPref = context.getSharedPreferences("sharedUbicationsCurrent", MODE_PRIVATE);
        String ubicationJson = sharedPref.getString("sharedUbicationCurrent", null);
        return gson.fromJson(ubicationJson, Ubication.class);
    }


}
