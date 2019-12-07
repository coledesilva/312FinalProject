package com.example.finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchAPOD {
    static final String BASE_URL = "https://api.nasa.gov";

    static final String TAG = "NASAAPIHelperTag";

    MainActivity mainActivity = null;

    public FetchAPOD(MainActivity mainActivity) { this.mainActivity = mainActivity; }

    public void fetchAPOD() {
        String url = contstructAPODString();
        Log.d(TAG, "fetchAPOD: " + url);
        FetchAPODAsync fetchAPOD = new FetchAPODAsync();
        fetchAPOD.execute(url);
    }

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
            ProgressBar progress = (ProgressBar) mainActivity.findViewById(R.id.homeScreenProgressBar);
            progress.setVisibility(View.VISIBLE);
        }


        @Override
        protected String[] doInBackground(String... strings) {
            String url = strings[0];

            try{
                URL urlObj = new URL(url);

                HttpURLConnection connection1 = (HttpURLConnection) urlObj.openConnection();
                // if we get here then successfully opened URL over HTTP protocol

                String jsonResult = "";
                // char by char we are going to build the json string from an input stream
                InputStream in1 = connection1.getInputStream();
                InputStreamReader reader = new InputStreamReader(in1);
                int data = reader.read();
                while(data != -1) { // -1 is returned at end of input stream
                    jsonResult += (char) data;
                    data = reader.read();
                }

                JSONObject jsonObject = new JSONObject(jsonResult);

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
