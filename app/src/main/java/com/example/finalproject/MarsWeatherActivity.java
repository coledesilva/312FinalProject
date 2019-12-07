package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MarsWeatherActivity extends AppCompatActivity {
    private static final String TAG = "MarsWeatherActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars_weather);

        FetchMarsWeather fetchWeather = new FetchMarsWeather(this);
        fetchWeather.fetchWeather();
    }

    public void recievedWeatherData(WeatherData[] weatherData) {
        for(int i = 0; i < weatherData.length; i++) {
            Log.d(TAG, "recievedWeatherData: " + weatherData[i]);
        }
    }
}
