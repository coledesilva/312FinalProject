package com.example.finalproject;

import android.os.AsyncTask;

public class FetchMarsWeather {
    static final String BASE_URL = "https://api.nasa.gov";
    MarsWeatherActivity marsWeatherActivity = null;

    public FetchMarsWeather(MarsWeatherActivity marsWeatherActivity) { this.marsWeatherActivity = marsWeatherActivity; }

    class FetchMarsWeatherAsyncTask extends AsyncTask<String, Void, WeatherData[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected WeatherData[] doInBackground(String... strings) {
            return new WeatherData[0];
        }

        @Override
        protected void onPostExecute(WeatherData[] weatherData) {
            super.onPostExecute(weatherData);
        }
    }
}
