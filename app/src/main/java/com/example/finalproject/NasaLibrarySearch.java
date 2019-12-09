package com.example.finalproject;

import android.os.AsyncTask;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class NasaLibrarySearch {
    static final String BASE_URL = "https://images-api.nasa.gov";
    ImageAndVideoActivity imageAndVideoActivity = null;
    static final String TAG = "ImageAndVideo";


    public NasaLibrarySearch(ImageAndVideoActivity imageAndVideoActivity) {
        this.imageAndVideoActivity = imageAndVideoActivity;
    }

    public void fetchImgOrVideo(){
        String url = constructImageAndVideoUrl();
        Log.d(TAG, "fetchVideo: " + url);
        NasaLibrarySearch.FetchImageAndVideoAsyncTask fetchVideo = new NasaLibrarySearch.FetchImageAndVideoAsyncTask();
        fetchVideo.execute(url);
    }
    private URL encodeURL(String url){
        URL urlEncode = null;
        try
        {
            urlEncode = new URL(url);
            URI uri = new URI(urlEncode.getProtocol(), urlEncode.getUserInfo(), urlEncode.getHost(),
                    urlEncode.getPort(), urlEncode.getPath(), urlEncode.getQuery(), urlEncode.getRef());
            urlEncode = uri.toURL();

        }
        catch (MalformedURLException ex){
            ex.printStackTrace();
        }
        catch (URISyntaxException ex){
            ex.printStackTrace();
        }
        finally {
            return urlEncode;
        }
    }

    public String constructImageAndVideoUrl(){
        String baseUrl = BASE_URL;
        baseUrl += "/search?q=apollo";
        return baseUrl;
    }

    class FetchImageAndVideoAsyncTask extends AsyncTask<String, Void, String[]> {
        public FetchImageAndVideoAsyncTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar progress = (ProgressBar) imageAndVideoActivity.findViewById(R.id.progressBar);
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            ProgressBar progress = (ProgressBar) imageAndVideoActivity.findViewById(R.id.progressBar);
            progress.setVisibility(View.GONE);
            imageAndVideoActivity.receivedVideo(strings);
        }

        private String[] parseVideo(JSONObject jsonObject){

            try{
                JSONArray items = jsonObject.getJSONArray("items");
                JSONObject data = items.getJSONObject(0);
                JSONObject links = data.getJSONObject("links");
                String videoURL = links.getString("href");
                Log.d(TAG,videoURL);
                String videoDate = jsonObject.getString("date_created");
                String videoDesc = jsonObject.getString("description");

                return new String[] {videoURL, videoDate, videoDesc};
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected String[] doInBackground(String... strings) {
            String url = strings[0];
            try{
                URL urlObj = encodeURL(url);

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
                Log.d(TAG,jsonResult);
                reader.close();
                return parseVideo(jsonObject);

            }
            catch (MalformedURLException ex){
                ex.printStackTrace();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
            catch (JSONException ex){
                ex.printStackTrace();
            }
            return null;
        }

    }


}