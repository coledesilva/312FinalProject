package com.example.finalproject;
/**
 * This is the async task class to fetch the photo of the day from the NASA API
 * @authors: Cole & Jackson
 * @version: v1.0
 * @date: 12/11/2019
 */

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FetchAPOD {
    static final String BASE_URL = "https://api.nasa.gov";

    static final String TAG = "FetchAPOD";

    MainActivity mainActivity = null;

    public FetchAPOD(MainActivity mainActivity) { this.mainActivity = mainActivity; }

    /**
     * fetch function which starts execution of async task
     */
    public void fetchAPOD() {
        String url = contstructAPODString();
        Log.d(TAG, "fetchAPOD: " + url);
        FetchAPODAsync fetchAPOD = new FetchAPODAsync();
        fetchAPOD.execute(url);
    }

    /**
     * constructs the url for the APOD request
     * @return a string url of the APOD request
     */
    public String contstructAPODString(){
        String url = BASE_URL;

        url += "/planetary/apod";
        url += "?api_key="  + mainActivity.getString(R.string.API_KEY);

        return url;
    }

    class FetchAPODAsync extends AsyncTask<String, Void, String[]> {

        public FetchAPODAsync() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar progress = (ProgressBar) mainActivity.findViewById(R.id.progressBar);
            progress.setVisibility(View.VISIBLE);
        }


        @Override
        protected String[] doInBackground(String... strings) {
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

                Log.d(TAG, "doInBackground: " + jsonResponse);
                JSONObject jsonObject = new JSONObject(jsonResponse);

                return parsePhoto(jsonObject);
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
         * parses photo information from json response
         * @param jsonObject the base json object from the json response
         * @return a string array of the photo information
         */
        private String[] parsePhoto(JSONObject jsonObject){

            try{
                String photoURL = jsonObject.getString("url");
                String photoTitle = jsonObject.getString("title");
                String photoDate = jsonObject.getString("date");

                return new String[] {photoTitle, photoDate, photoURL};
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String[] s) {
            super.onPostExecute(s);
            /*
            ProgressBar progress = (ProgressBar) mainActivity.findViewById(R.id.homeScreenProgressBar);
            progress.setVisibility(View.GONE); */
            mainActivity.receivedAPOD(s);
        }
    }


}
