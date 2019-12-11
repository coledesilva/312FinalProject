package com.example.finalproject;
/**
 * This is the Mars Weather activity which displays the mars weather info
 * @authors: Cole & Jackson
 * @version: v1.0
 * @date: 12/11/2019
 */

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MarsWeatherActivity extends AppCompatActivity {
    private static final String TAG = "MarsWeatherActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars_weather);

        FetchMarsWeather fetchWeather = new FetchMarsWeather(this);
        fetchWeather.fetchWeather();
    }

    /**
     * received weather data function which gets weather data array from fetch mars weather async task
     * @param weatherData array of weather data objects
     */
    public void receivedWeatherData(WeatherData[] weatherData) {
        int end = weatherData.length - 1;

        TextView currSolDate = (TextView) findViewById(R.id.currentDateSol);
        currSolDate.setText(getString(R.string.sol_text) + " " + weatherData[end].getSolDate());
        TextView currEarthDate = (TextView) findViewById(R.id.currEarthDate);
        currEarthDate.setText(weatherData[end].getEarthDate());

        TextView currSolLow = (TextView) findViewById(R.id.currSolLowTemp);
        int low = (int) Math.rint(weatherData[end].getLowTemp());
        currSolLow.setText(getString(R.string.low_temp) + " " + low + getString(R.string.degrees_f));

        TextView currSolAvg = (TextView) findViewById(R.id.currSolAvgTemp);
        int avg = (int) Math.rint(weatherData[end].getAvgTemp());
        currSolAvg.setText(getString(R.string.avg_temp) + " " + avg + getString(R.string.degrees_f));

        TextView currSolHigh = (TextView) findViewById(R.id.currSolHighTemp);
        int high = (int) Math.rint(weatherData[end].getHighTemp());
        currSolHigh.setText(getString(R.string.high_temp) + " " + high + getString(R.string.degrees_f));

        final WeatherData[] daysLeft = new WeatherData[end];
        for(int i = 0; i < end; i++){
            daysLeft[i] = weatherData[i];
        }

        ListView listView = (ListView) findViewById(R.id.weatherListView);
        ArrayAdapter<WeatherData> arrayAdapter = new ArrayAdapter<WeatherData>(this, R.layout.weather_row, R.id.solDate_row, daysLeft){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView currSol = (TextView) view.findViewById(R.id.solDate_row);
                currSol.setText(getString(R.string.sol_text) + " " + daysLeft[position].getSolDate());

                TextView currEarth = (TextView) view.findViewById(R.id.earthDate_row);
                String[] shortDateInfo = daysLeft[position].getEarthDate().split("[,]{1}");
                currEarth.setText(shortDateInfo[0]);

                TextView highTemp = (TextView) view.findViewById(R.id.solHighTemp_row);
                int high = (int) Math.rint(daysLeft[position].getHighTemp());
                highTemp.setText(getString(R.string.high_temp) + " " + high + getString(R.string.degrees_f));

                TextView lowTemp = (TextView) view.findViewById(R.id.solLowTemp_row);
                int low = (int) Math.rint(daysLeft[position].getLowTemp());
                lowTemp.setText(getString(R.string.low_temp) + " " + low + getString(R.string.degrees_f));

                return view;
            }
        };
        listView.setAdapter(arrayAdapter);
    }
}
