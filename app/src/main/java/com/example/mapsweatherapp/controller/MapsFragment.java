package com.example.mapsweatherapp.controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;

import com.example.mapsweatherapp.model.Ubication;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mapsweatherapp.R;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap googleMap;
    EditText edtLocation;
    Button btnSearch;
    private double latitude, longitude;

    private Manager manager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        syncronizedWidget(view);
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    String nameCity = getCityByLatLong(latitude, longitude);
                    savaCitySharedPreferences(nameCity,latitude,longitude);
                    saveCurrentLocationSharedPreferences(nameCity,latitude,longitude);
                    onMapReady(googleMap);
                    Log.wtf("MapsFragment", "Latitude: " + latitude + " Longitude: " + longitude);
                } else {
                    edtLocation.setError("This location does not exist");
                }
            });
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableVisibilityKeyBoard();
                String location = edtLocation.getText().toString();
                if (location.matches("")) {
                    edtLocation.setError("Please enter a location");
                } else {
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    setLocation(geocoder, location);
                }
            }
        });

        return view;
    }

    private void syncronizedWidget(View view) {
        manager = new Manager();
        edtLocation = view.findViewById(R.id.edtLocation);
        btnSearch = view.findViewById(R.id.buttonSearch);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapsFragment);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }


    private void setLocation(Geocoder geocoder, String location) {
        try {
            List<Address> geoResults = geocoder.getFromLocationName(location, 1);
            if (geoResults.size() > 0) {
                latitude = geoResults.get(0).getLatitude();
                longitude = geoResults.get(0).getLongitude();
                setLatitudeLongitudeMap(location, latitude, longitude);
                savaCitySharedPreferences(getCityByLatLong(latitude,longitude),latitude,longitude);
            } else {
                edtLocation.setError("This location does not exist");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onMapReady( GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("LOCATION");
        if(this.googleMap != null){
            this.googleMap.addMarker(markerOptions);
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
            this.googleMap.animateCamera(cameraUpdate);
        }else{
            Log.wtf("MapsFragment", "GoogleMap is null");
        }


    }

    private void setLatitudeLongitudeMap(String mapLocation, double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(mapLocation);
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        googleMap.animateCamera(cameraUpdate);
    }

    private void disableVisibilityKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(edtLocation.getWindowToken(), 0);
    }

    private String getCityByLatLong(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return addresses.get(0).getAddressLine(0);
    }

    private void savaCitySharedPreferences(String name, double latitude, double longitude){
        Ubication ubication = new Ubication(name, latitude, longitude);
        this.manager.saveDataSharedPreferences(requireContext(), ubication);
    }
    private void saveCurrentLocationSharedPreferences(String name, double latitude, double longitude){
        Ubication ubication = new Ubication(name, latitude, longitude);
        this.manager.saveDataSharedPreferencesCurrentUbication(requireContext(), ubication);
    }

}