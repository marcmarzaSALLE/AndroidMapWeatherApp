package com.example.mapsweatherapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.mapsweatherapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    NavigationBarView navigationView;
    public static EditText edtLocation;
    public static Button btnSearch;

    public static Bundle myBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        syncronizeWidget();

        openFragment(new MapsFragment());
        navigationView.setItemIconTintList(null);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.id_maps:
                        openFragment(new MapsFragment());
                    Log.d("MAPA","MAPAS");
                        return true;
                    case R.id.id_weather:
                        openFragment(new WeatherFragment());
                        return true;
                }
                return false;
            }

        });

    }

    private void syncronizeWidget(){
        navigationView = (BottomNavigationView) findViewById(R.id.navigationView);

    }

    private void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentLayout, fragment).commit();
    }

}