package com.example.finalproject;

import androidx.annotation.NonNull;

public class WeatherData {
    private int solDate;
    private double highTemp;
    private double lowTemp;
    private String season;
    private String earthDate;

    public WeatherData() {
        this.solDate = 0;
        this.highTemp = 0;
        this.lowTemp = 0;
        this.season = "";
        this.earthDate = "";
    }

    public WeatherData(int solDate, double highTemp, double lowTemp, String season, String earthDate) {
        this.solDate = solDate;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.season = season;
        this.earthDate = earthDate;
    }

    public int getSolDate() {
        return solDate;
    }

    public void setSolDate(int solDate) {
        this.solDate = solDate;
    }

    public double getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(double highTemp) {
        this.highTemp = highTemp;
    }

    public double getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(double lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getEarthDate() {
        return earthDate;
    }

    public void setEarthDate(String earthDate) {
        this.earthDate = earthDate;
    }

    @NonNull
    @Override
    public String toString() {
        String result = "Sol Date: " + this.solDate + "\n"
                + "High Temp: " + this.highTemp + "\n"
                + "Low Temp: " + this.lowTemp + "\n"
                + "Season: " + this.season + "\n"
                + "Earth Date: " + this.earthDate + "\n";

        return result;
    }
}
