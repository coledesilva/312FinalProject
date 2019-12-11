package com.example.finalproject;
/**
 * This is the async task class to fetch a mars weather data from the NASA API
 * @authors: Cole & Jackson
 * @version: v1.0
 * @date: 12/11/2019
 */

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FetchMarsWeather {
    static final String TAG = "FetchMarsWeatherTag";
    static final String BASE_URL = "https://api.nasa.gov";
    MarsWeatherActivity marsWeatherActivity = null;


    public FetchMarsWeather(MarsWeatherActivity marsWeatherActivity) { this.marsWeatherActivity = marsWeatherActivity; }

    /**
     * fetch function to start async task
     */
    public void fetchWeather() {
        String url = constructURL();
        Log.d(TAG, "fetchWeather: " + url);

        FetchMarsWeatherAsyncTask async = new FetchMarsWeatherAsyncTask();
        async.execute(url);
    }

    /**
     * constructs url for get request from weather api
     * @return a string url for the request from the weather api
     */
    public String constructURL() {
        String url = BASE_URL;
        url += "/insight_weather";
        url += "/?api_key=" + marsWeatherActivity.getString(R.string.API_KEY);
        url += "&feedtype=json&ver=1.0";

        return url;
    }

    class FetchMarsWeatherAsyncTask extends AsyncTask<String, Void, WeatherData[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ProgressBar pg = (ProgressBar) marsWeatherActivity.findViewById(R.id.weatherProgressBar);
            pg.setVisibility(View.VISIBLE);
        }

        @Override
        protected WeatherData[] doInBackground(String... strings) {
            String url = strings[0];

            try{
                URL urlObj = new URL(url);

                HttpURLConnection connection1 = (HttpURLConnection) urlObj.openConnection();
                // if we get here then successfully opened URL over HTTP protocol
                connection1.connect();

                InputStream in1 = connection1.getInputStream();
                StringWriter writer = new StringWriter();
                IOUtils.copy(in1, writer, StandardCharsets.UTF_8);
                String jsonResponse = writer.toString();
                JSONObject jsonObject = new JSONObject(jsonResponse);
                Log.d(TAG, "doInBackground: " + jsonObject.toString());

                return parseWeather(jsonObject);
            }
            catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            catch(JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        /**
         * parses weather information from the json response
         * @param object the initial json object from the json response
         * @return an array of weather data models containing the necessary information
         */
        public WeatherData[] parseWeather(JSONObject object) {
            // creating array of weather data obj for 7 days
            WeatherData[] weatherData = new WeatherData[7];

            try{
                //getting the sol_key attributes
                JSONArray sol = object.getJSONArray("sol_keys");
                String[] solKeys = new String[sol.length()];
                for(int i = 0; i < solKeys.length; i++){
                    solKeys[i] = sol.getString(i);
                    Log.d(TAG, "parseWeather: " + solKeys[i]);
                }

                for(int i = 0; i < weatherData.length; i++){
                    WeatherData data = new WeatherData();
                    // setting sol date
                    data.setSolDate(solKeys[i]);

                    JSONObject specificSol = object.getJSONObject(solKeys[i]);

                    // getting temp data and setting it in data obj
                    JSONObject tempData = specificSol.getJSONObject("AT");
                    Log.d(TAG, "parseWeather: " + tempData.toString());

                    Double maxTemp = BigDecimal.valueOf(tempData.getDouble("mx")).doubleValue();
                    Log.d(TAG, "parseWeather: " + maxTemp);
                    data.setHighTemp(maxTemp);
                    Double avgTemp = BigDecimal.valueOf(tempData.getDouble("av")).doubleValue();
                    Log.d(TAG, "parseWeather: " + avgTemp);
                    data.setAvgTemp(avgTemp);
                    Double minTemp = BigDecimal.valueOf(tempData.getDouble("mn")).doubleValue();
                    Log.d(TAG, "parseWeather: " + minTemp);
                    data.setLowTemp(minTemp);

                    // getting, parsing, and setting earth date
                    String unparsedEarthDate = specificSol.getString("First_UTC");
                    String earthDate = parseDate(unparsedEarthDate);
                    data.setEarthDate(earthDate);

                    String season = specificSol.getString("Season");
                    data.setSeason(season);


                    weatherData[i] = data;
                }

                return weatherData;
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return null;

        }

        /**
         * parses the date into something easily readable
         * @param str the initial string of the date
         * @return a nice formatted date string
         */
        public String parseDate(String str) {
            String[] info = str.split("[T]{1}");

            String tmpDate = info[0];
            String[] unfinishedDate = tmpDate.split("[-]{1}");
            String result = returnStringMonth(unfinishedDate[1]) + " " + unfinishedDate[2] + ", " + unfinishedDate[0];
            return result;
        }

        /**
         * returns the string month
         * @param month "integer" version of month
         * @return string version of month
         */
        public String returnStringMonth(String month){
            switch(month) {
                case "01":
                    return "January";
                case "02":
                    return "February";
                case "03":
                    return "March";
                case "04":
                    return "April";
                case "05":
                    return "May";
                case "06":
                    return "June";
                case "07":
                    return "July";
                case "08":
                    return "August";
                case "09":
                    return "September";
                case "10":
                    return "October";
                case "11":
                    return "November";
                case "12":
                    return "December";
            }
            return "";
        }

        @Override
        protected void onPostExecute(WeatherData[] weatherData) {
            super.onPostExecute(weatherData);

            ProgressBar pg = (ProgressBar) marsWeatherActivity.findViewById(R.id.weatherProgressBar);
            pg.setVisibility(View.GONE);

            marsWeatherActivity.receivedWeatherData(weatherData);
        }
    }

}
