package com.example.finalproject;
/**
 * This is the Weather Data model which stores weather information about a particular solar day
 * @authors: Cole & Jackson
 * @version: v1.0
 * @date: 12/11/2019
 */

import androidx.annotation.NonNull;

public class WeatherData {
    private String solDate;
    private double highTemp;
    private double avgTemp;
    private double lowTemp;
    private String season;
    private String earthDate;

    public WeatherData() {
        this.solDate = "";
        this.highTemp = 0;
        this.avgTemp = 0;
        this.lowTemp = 0;
        this.season = "";
        this.earthDate = "";
    }

    public WeatherData(String solDate, double highTemp, double avgTemp, double lowTemp, String season, String earthDate) {
        this.solDate = solDate;
        this.highTemp = highTemp;
        this.avgTemp = avgTemp;
        this.lowTemp = lowTemp;
        this.season = season;
        this.earthDate = earthDate;
    }

    public String getSolDate() {
        return solDate;
    }

    public void setSolDate(String solDate) {
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

    public double getAvgTemp() { return avgTemp; }

    public void setAvgTemp(double avgTemp) { this.avgTemp = avgTemp; }

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
