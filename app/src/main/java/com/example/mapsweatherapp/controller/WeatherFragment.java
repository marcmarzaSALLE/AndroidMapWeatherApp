package com.example.mapsweatherapp.controller;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mapsweatherapp.R;
import com.example.mapsweatherapp.adapter.AdapterCurrentForecast;
import com.example.mapsweatherapp.adapter.AdapterForecastDays;
import com.example.mapsweatherapp.model.Ubication;
import com.example.mapsweatherapp.model.WeatherForecast;
import com.example.mapsweatherapp.model.WeatherUbication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeatherFragment extends Fragment {
    Manager manager;
    ArrayList<Ubication>ubications;
    private Ubication currentUbication;
    private RequestQueue requestQueue;

    TextView txtViewLocation, txtViewTemp, txtViewDescriptionTemp;
    ImageButton btnAddLocation,btnCurrentUbication;
    RecyclerView recyclerViewForecastDays, recyclerViewCurrent;
    ListView listViewUbications;
    LinearLayout linearLayoutTemp,linearLayoutForecastDays, linearLayoutCurrent,linearLayoutForecastText,linearLayoutCurrentText, linearLayoutGeneral;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewFragment = inflater.inflate(R.layout.fragment_weather, container, false);
        syncronizedData(viewFragment);
        viewLinearGeneralGone();
        getCurrentUbication();
        addDataWeatherUbications(currentUbication);
        btnCurrentUbication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentUbication();
                addDataWeatherUbications(currentUbication);
            }
        });
        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUbicationList();
            }
        });

        return viewFragment;
    }

    private void syncronizedData(View view) {
        manager = new Manager();
        txtViewLocation = (TextView) view.findViewById(R.id.txtViewLocation);
        txtViewTemp = (TextView) view.findViewById(R.id.txtViewTemp);
        txtViewDescriptionTemp = (TextView) view.findViewById(R.id.txtViewDescriptionTemp);
        btnAddLocation = (ImageButton) view.findViewById(R.id.btnSearchLocation);
        recyclerViewForecastDays = (RecyclerView) view.findViewById(R.id.recyclerViewForecastDays);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCurrent = (RecyclerView) view.findViewById(R.id.recyclerViewCurrent);
        recyclerViewCurrent.setLayoutManager(linearLayoutManager);
        ubications = manager.loadDataSharedPreferences(requireContext());
        listViewUbications = (ListView) view.findViewById(R.id.listViewUbication);
        listViewUbications.setVisibility(View.GONE);
        linearLayoutTemp = (LinearLayout) view.findViewById(R.id.linearLayoutTemp);
        linearLayoutCurrent = (LinearLayout) view.findViewById(R.id.linearLayoutForecastCurrent);
        linearLayoutForecastDays = (LinearLayout) view.findViewById(R.id.linearLayoutForecastDays);
        linearLayoutForecastText = (LinearLayout) view.findViewById(R.id.linearLayoutForecastText);
        linearLayoutCurrentText = (LinearLayout) view.findViewById(R.id.linearLayoutForecastCurrentText);
        linearLayoutGeneral = (LinearLayout) view.findViewById(R.id.linearLayoutGeneral);
        btnCurrentUbication = (ImageButton) view.findViewById(R.id.btnCurrentLocation);
    }

    private void syncronizedLinearLayout(){

    }

    private void viewLinearGeneralGone() {
       linearLayoutGeneral.setVisibility(View.GONE);
    }

    private void viewLinearGeneralVisible() {
        linearLayoutGeneral.setVisibility(View.VISIBLE);
    }

    private void getCurrentUbication() {
        currentUbication = manager.getCurrentUbication(requireContext());
    }

    private void getLocations() {

    }


    private void addDataWeatherUbications(Ubication ubication) {
        requestQueue = Volley.newRequestQueue(requireContext());
        String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + ubication.getLatitude() + "&lon=" + ubication.getLongitude() + "&appid=dfc3e877446ddea4b96406258478f302&units=metric";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    String name = response.getJSONObject("city").getString("name");
                    String main = response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main");
                    double temp = response.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp");
                    double tempMin = response.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp_min");
                    double tempMax = response.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp_max");
                    JSONArray jsonArray = response.getJSONArray("list");
                    ArrayList<WeatherForecast> weatherForecasts = new ArrayList<>();
                    WeatherForecast weatherForecast;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String idIcon = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
                        double temp2 = jsonObject.getJSONObject("main").getDouble("temp");
                        double tempMin2 = jsonObject.getJSONObject("main").getDouble("temp_min");
                        double tempMax2 = jsonObject.getJSONObject("main").getDouble("temp_max");
                        String main2 = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
                        double wind2 = jsonObject.getJSONObject("wind").getDouble("speed");
                        String dateHour = jsonObject.getString("dt_txt");
                        weatherForecast = new WeatherForecast(idIcon, dateHour, main2, temp2, tempMin2, tempMax2, wind2);
                        weatherForecasts.add(weatherForecast);
                    }
                    WeatherUbication weatherUbication = new WeatherUbication(name, main, temp, tempMax, tempMin, weatherForecasts);
                    WeatherFragment.this.addWeatherUbicationObject(weatherUbication);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    public void addWeatherUbicationObject(WeatherUbication weatherUbication) {
        txtViewLocation.setText(weatherUbication.getName());
        txtViewTemp.setText(Math.round(weatherUbication.getTemp()) + "°");
        txtViewDescriptionTemp.setText(weatherUbication.getMain());
        ArrayList<WeatherForecast> weatherForecastsAuxCurrent = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        String localDateString = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int count = 0;
        for (WeatherForecast wf : weatherUbication.getWeatherForecasts()) {
            if (wf.getDateDay().equals(localDateString)) {
                count++;
                weatherForecastsAuxCurrent.add(wf);
            }else{
                if (count != 5) {
                    count++;
                    weatherForecastsAuxCurrent.add(wf);
                }
            }

        }
        AdapterCurrentForecast adapterCurrentForecast = new AdapterCurrentForecast(weatherForecastsAuxCurrent, requireContext());
        recyclerViewCurrent.setAdapter(adapterCurrentForecast);

        ArrayList<WeatherForecast> weatherForecastsAuxDays = new ArrayList<>();
        int counter = 0;
        for (WeatherForecast wf : weatherUbication.getWeatherForecasts()) {
            if (!wf.getDateDay().equals(localDateString)) {
                if(wf.getHour().equals("12:00:00")){
                    if(counter != 3){
                        counter++;
                        weatherForecastsAuxDays.add(wf);
                    }
                }
            }
        }
        AdapterForecastDays adapterForecastDays = new AdapterForecastDays(weatherForecastsAuxDays, requireContext());
        recyclerViewForecastDays.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewForecastDays.setAdapter(adapterForecastDays);
        viewLinearGeneralVisible();
    }

    private void viewGoneLinearLayout(){
        linearLayoutTemp.setVisibility(View.GONE);
        linearLayoutCurrent.setVisibility(View.GONE);
        linearLayoutForecastDays.setVisibility(View.GONE);
        linearLayoutForecastText.setVisibility(View.GONE);
        linearLayoutCurrentText.setVisibility(View.GONE);

    }

    private void viewVisibleLinearLayout(){
        linearLayoutTemp.setVisibility(View.VISIBLE);
        linearLayoutCurrent.setVisibility(View.VISIBLE);
        linearLayoutForecastDays.setVisibility(View.VISIBLE);
        linearLayoutForecastText.setVisibility(View.VISIBLE);
        linearLayoutCurrentText.setVisibility(View.VISIBLE);
        listViewUbications.setVisibility(View.GONE);
    }
    public void addUbicationList(){
        viewGoneLinearLayout();
        listViewUbications.setVisibility(View.VISIBLE);
        List<String> list = new ArrayList<>();
        for(Ubication u: ubications){
            list.add(u.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, list);
        listViewUbications.setAdapter(adapter);
        listViewUbications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentUbication = ubications.get(position);
                addDataWeatherUbications(currentUbication);
                viewVisibleLinearLayout();
            }
        });
    }

}