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

public class AdapterForecastDays extends RecyclerView.Adapter<AdapterForecastDays.ViewHolder>{
    private ArrayList<WeatherForecast>weatherForecasts=new ArrayList<>();
    private LayoutInflater inflater;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.box_recyclerview_forecast_days,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(weatherForecasts.get(position));
    }

    @Override
    public int getItemCount() {
        return weatherForecasts.size();
    }

    public AdapterForecastDays(ArrayList<WeatherForecast> weatherForecasts, Context context) {
        this.weatherForecasts = weatherForecasts;
        this.inflater=LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtDateMain,txtTemp;
        ImageView imgWeather;
        public ViewHolder(View parent) {
            super(parent);
            txtDateMain = (TextView) parent.findViewById(R.id.txtDateMain);
            txtTemp = (TextView) parent.findViewById(R.id.txtTemp);
            imgWeather = (ImageView) parent.findViewById(R.id.imgIconWeather);
        }
        void bindData(final WeatherForecast weatherForecast){
            Glide.with(itemView.getContext()).load("https://openweathermap.org/img/wn/"+weatherForecast.getIdIcon()+".png").into(imgWeather);
            txtDateMain.setText(weatherForecast.getDateWeather());
            txtTemp.setText(weatherForecast.getTempMaxMin());
        }
    }
}
