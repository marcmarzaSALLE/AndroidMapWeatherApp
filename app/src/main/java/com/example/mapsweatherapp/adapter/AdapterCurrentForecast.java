package com.example.mapsweatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mapsweatherapp.R;
import com.example.mapsweatherapp.model.WeatherForecast;

import java.util.ArrayList;

public class AdapterCurrentForecast extends RecyclerView.Adapter<AdapterCurrentForecast.ViewHolder>{
    private ArrayList<WeatherForecast> weatherForecasts=new ArrayList<>();
    private LayoutInflater inflater;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.box_recyclerview_forecast_current,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCurrentForecast.ViewHolder holder, int position) {
        holder.bindData(weatherForecasts.get(position));
    }

    @Override
    public int getItemCount() {
        return weatherForecasts.size();
    }

    public AdapterCurrentForecast(ArrayList<WeatherForecast> weatherForecasts, Context context){
        this.weatherForecasts = weatherForecasts;
        this.inflater=LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtDay,txtTemp,txtWind;
        ImageView imgWeather;
        public ViewHolder(View parent) {
            super(parent);
            txtDay = (TextView) parent.findViewById(R.id.txtViewDay);
            txtTemp = (TextView) parent.findViewById(R.id.txtViewTemp);
            txtWind = (TextView) parent.findViewById(R.id.txtViewDateShort);
            imgWeather = (ImageView) parent.findViewById(R.id.imageViewIconWeather);
        }
        void bindData(WeatherForecast weatherForecast){
            txtDay.setText(weatherForecast.getHourFormat());
            txtTemp.setText(Math.round(weatherForecast.getTemp())+"°C");
            Glide.with(itemView.getContext()).load("https://openweathermap.org/img/wn/"+weatherForecast.getIdIcon()+".png").into(imgWeather);
            txtWind.setText(weatherForecast.getDataShort());

        }
    }
}
