package com.example.mapsweatherapp.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class WeatherForecast {
    private String date;

    private String idIcon;
    private String main;

    private String dateString;
    private double temp;
    private double maxTemp;
    private double minTemp;

    private double wind;

    public WeatherForecast(String idIcon,String date, String main,double temp ,double maxTemp, double minTemp, double wind) {
        this.idIcon = idIcon;
        this.date = date;
        this.main = main;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.temp = temp;
        this.wind = wind;
    }

    public String getIdIcon() {
        return idIcon;
    }

    public String getMain() {
        return main;
    }

    public double getTemp() {
        return temp;
    }

    public double getWind() {
        return wind;
    }

    public String getDate() {
        return date;
    }

    public String getWeather() {
        return main;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public String getDateDay(){
        String[]dateSplit = getDate().split(" ");
        return dateSplit[0];
    }

    public String getDateWeather(){
        String msg="";
        String[]dateSplit = getDate().split(" ");
        LocalDate localDate = LocalDate.parse(dateSplit[0]);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        LocalDate today = LocalDate.now();
        if(localDate.equals(today)){
            msg = "Today " + getWeather();
        }else if(localDate.equals(today.plusDays(1))){
            msg = "Tomorrow " + getWeather();

        }else{
            String firstLetter = dayOfWeek.toString().substring(0,1);
            String rest = dayOfWeek.toString().substring(1).toLowerCase();
            String day = firstLetter + rest;
            msg = day + " " + getWeather();
        }
        return msg;
    }

    public String getTempMaxMin(){
        return  Math.round(getMaxTemp()) + "º / " + Math.round(getMinTemp())+ "º";
    }

    public String getHour(){
        String[]dateSplit = getDate().split(" ");
        return dateSplit[1];
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }


    public String getDataShort(){
        String msg="";
        String[]dateSplit = getDate().split(" ");
        LocalDate localDate = LocalDate.parse(dateSplit[0]);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        LocalDate today = LocalDate.now();
        if(!localDate.equals(today)){
            String firstLetter = dayOfWeek.toString().substring(0,1);
            String rest = dayOfWeek.toString().substring(1,3).toLowerCase();
            msg = firstLetter + rest;
        }else{
            msg = "Today";
        }
        return msg;
    }


    public String getHourFormat(){
        String[]dateSplit = getDate().split(" ");
        LocalTime localTime = LocalTime.parse(dateSplit[1]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return localTime.format(formatter);
    }


    public int getWindKm(){
        return (int) Math.round(getWind() * 3.6);
    }
}
