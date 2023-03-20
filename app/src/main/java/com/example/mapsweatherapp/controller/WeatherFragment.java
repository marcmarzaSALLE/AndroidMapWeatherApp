package com.example.mapsweatherapp.controller;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mapsweatherapp.R;
import com.example.mapsweatherapp.model.Ubication;
import com.example.mapsweatherapp.model.WeatherUbication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class WeatherFragment extends Fragment {
    private Manager manager;
    private ArrayList<Ubication> ubications;
    private ArrayList<WeatherUbication> weatherUbications, weatherUbicationsAux;
    private RequestQueue requestQueue;
    Spinner spinner;
    TextView txtName, txtWeatherGrades, txtWind, txtHumidity, txtDescription, txtMaxMinTemp, txtWindText, txtHumidityText;
    ImageView imgWeather, imgWind, imgHumidity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewFragment = inflater.inflate(R.layout.fragment_weather, container, false);
        viewFragment.setVisibility(View.INVISIBLE);
        requestQueue = Volley.newRequestQueue(requireContext());

        syncronizedData(viewFragment);
        getLocations();
        addDataWeatherUbications();
        addDataSpinner();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewFragment.setVisibility(View.VISIBLE);
                addDataWidgets(position, viewFragment);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return viewFragment;
    }

    private void syncronizedData(View view) {
        manager = new Manager();
        spinner = view.findViewById(R.id.spinner);
        txtName = view.findViewById(R.id.txtViewLocation);
        txtWind = view.findViewById(R.id.txtViewWind);
        txtWeatherGrades = view.findViewById(R.id.txtViewGrades);
        txtDescription = view.findViewById(R.id.txtViewDescription);
        txtHumidity = view.findViewById(R.id.txtViewHumidity);
        imgWeather = view.findViewById(R.id.imgWeather);
        txtMaxMinTemp = view.findViewById(R.id.txtViewMaxMinTemp);
        txtWindText = view.findViewById(R.id.txtViewWindTXT);
        txtHumidityText = view.findViewById(R.id.txtViewHumidityTXT);
        imgWind = view.findViewById(R.id.imgViewWind);
        imgHumidity = view.findViewById(R.id.imgViewHumidity);
        weatherUbications = new ArrayList<>();
        weatherUbicationsAux = new ArrayList<>();
    }

    private void getLocations() {
        ubications = manager.loadDataSharedPreferences(requireContext());
    }


    private void addDataWeatherUbications() {
        for (int i = 0; i < ubications.size(); i++) {
            String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + ubications.get(i).getLatitude() + "&lon=" + ubications.get(i).getLongitude() + "&appid=de46ba611be3cefe2486a2d32d4a89f5&units=metric";
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String name = response.getString("name");
                        double lat = response.getJSONObject("coord").getDouble("lat");
                        double lon = response.getJSONObject("coord").getDouble("lon");
                        String weather = response.getJSONArray("weather").getJSONObject(0).getString("main");
                        String description = response.getJSONArray("weather").getJSONObject(0).getString("description");
                        double temp = response.getJSONObject("main").getDouble("temp");
                        int humidity = response.getJSONObject("main").getInt("humidity");
                        double speedWind = response.getJSONObject("wind").getDouble("speed");
                        int maxTemp = response.getJSONObject("main").getInt("temp_max");
                        int minTemp = response.getJSONObject("main").getInt("temp_min");
                        //AGafar vent, graus, humitat
                        WeatherFragment.this.addWeatherUbicationObject(new WeatherUbication(name, lon, lat, weather, description, temp, humidity, speedWind, maxTemp, minTemp));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.wtf("ERROR", error.toString());
                }
            });


            requestQueue.add(stringRequest);
        }

    }

    public void addWeatherUbicationObject(WeatherUbication weatherUbication) {
        weatherUbicationsAux.add(weatherUbication);
        if (weatherUbicationsAux.size() == ubications.size()) {
            weatherUbications = sortWeatherUbications();
            addDataSpinner();
        }
    }

    private void addDataSpinner() {
        ArrayList<String> list = new ArrayList<String>();
        for (WeatherUbication weatherUbication : weatherUbications) {
            list.add(weatherUbication.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), R.layout.spinner_item, list);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
    }

    private void addDataWidgets(int position, View viewFragment) {
        txtName.setText(weatherUbications.get(position).getName());
        txtDescription.setText(weatherUbications.get(position).getDescription().toUpperCase());
        txtWind.setText(weatherUbications.get(position).getWindSpeed() + " m/s");
        txtWeatherGrades.setText(Math.round(weatherUbications.get(position).getTemp()) + "°C");
        txtHumidity.setText(weatherUbications.get(position).getHumidity() + "%");
        txtMaxMinTemp.setText(weatherUbications.get(position).getMaxTemp() + "°/" + weatherUbications.get(position).getMinTemp() + "°");

        Map<String, Integer> hashMap = manager.getDataMapIcon();
        imgWeather.setImageResource(hashMap.get(weatherUbications.get(position).getDescription()));
        Map<String, Integer> hashMapColor = manager.getColorWeather();
        viewFragment.setBackgroundResource(hashMapColor.get(weatherUbications.get(position).getDescription()));
        spinner.setPopupBackgroundDrawable(ContextCompat.getDrawable(requireContext(), hashMapColor.get(weatherUbications.get(position).getDescription())));
        if (hashMapColor.get(weatherUbications.get(position).getDescription()) == R.drawable.color_thunderstorm || hashMapColor.get(weatherUbications.get(position).getDescription()) == R.drawable.color_rain) {
            setColorWhiteWidget();
        } else {
            setColorBlackWidget();
        }
    }

    private void setColorWhiteWidget() {
        txtName.setTextColor(Color.WHITE);
        txtWind.setTextColor(Color.WHITE);
        txtWeatherGrades.setTextColor(Color.WHITE);
        txtDescription.setTextColor(Color.WHITE);
        txtHumidity.setTextColor(Color.WHITE);
        txtMaxMinTemp.setTextColor(Color.WHITE);
        txtHumidityText.setTextColor(Color.WHITE);
        txtWindText.setTextColor(Color.WHITE);
        imgHumidity.setImageResource(R.drawable.icon_humidity_white);
        imgWind.setImageResource(R.drawable.icon_wind_white);
    }

    private void setColorBlackWidget() {
        txtName.setTextColor(Color.BLACK);
        txtWind.setTextColor(Color.BLACK);
        txtWeatherGrades.setTextColor(Color.BLACK);
        txtDescription.setTextColor(Color.BLACK);
        txtHumidity.setTextColor(Color.BLACK);
        txtMaxMinTemp.setTextColor(Color.BLACK);
        txtHumidityText.setTextColor(Color.BLACK);
        txtWindText.setTextColor(Color.BLACK);
        imgHumidity.setImageResource(R.drawable.icon_humidity);
        imgWind.setImageResource(R.drawable.wind_icon);
    }

    private ArrayList<WeatherUbication> sortWeatherUbications() {
        ArrayList<WeatherUbication> weather = new ArrayList<>();
        for (int i = 0; i < ubications.size(); i++) {
            for (int j = 0; j < weatherUbicationsAux.size(); j++) {

                double latUbication = (double)Math.round(ubications.get(i).getLatitude()*1000)/1000;
                double lonUbication = (double)Math.round(ubications.get(i).getLongitude()*1000)/1000;
                double latWeather =(double)Math.round(weatherUbicationsAux.get(j).getLat()*1000)/1000;
                double lonWeather =(double)Math.round(weatherUbicationsAux.get(j).getLon()*1000)/1000;
                Log.wtf("LAT", ubications.get(i).getLatitude() + " " + weatherUbicationsAux.get(j).getLat());
                if(latUbication == latWeather && lonUbication == lonWeather) {
                    weather.add(weatherUbicationsAux.get(j));
                }
            }
        }
        return weather;
    }

}