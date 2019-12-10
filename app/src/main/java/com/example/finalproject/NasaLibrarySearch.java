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

    class FetchImageAndVideoAsyncTask extends AsyncTask<String, Void, NasaMedia[]> {
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
        protected void onPostExecute(NasaMedia[] media) {
            super.onPostExecute(media);
            ProgressBar progress = (ProgressBar) imageAndVideoActivity.findViewById(R.id.progressBar);
            progress.setVisibility(View.GONE);
            imageAndVideoActivity.receivedMedia(media);
        }

        private NasaMedia[] parseVideo(JSONObject jsonObject){
            try{
                JSONObject metadata = jsonObject.getJSONObject("metadata");
                int numHits = metadata.getInt("total_hits");

                // limits it so we only get 10 items
                if(numHits > 10) {
                    numHits = 10;
                }
                NasaMedia[] media = new NasaMedia[numHits];

                // for the 10 items create a new nasamedia obj
                for(int i = 0; i < numHits; i++){
                    NasaMedia mediaObj = new NasaMedia();
                    // get to the right spot in the JSON
                    JSONObject collection = jsonObject.getJSONObject("collection");
                    JSONArray items = collection.getJSONArray("items");

                    // add to the nasamedia obj
                    JSONArray data = items.getJSONArray(0);
                    mediaObj.setDescription(data.getString(0));
                    Log.d(TAG, "parseVideo: " + data.getString(0));

                    mediaObj.setTitle(data.getString(3));
                    Log.d(TAG, "parseVideo: " + data.getString(3));

                    mediaObj.setMediaType(data.getString(6));
                    Log.d(TAG, "parseVideo: " + data.getString(6));

                    String mediaLink = items.getString(0);
                    mediaObj.setMediaLink(returnMedia(mediaLink, data.getString(6)));

                    // add the nasamedia object to the result output
                    media[i] = mediaObj;
                }

                return media;
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        private String returnMedia(String url, String mediaType) {
            try{
                URL urlObj = encodeURL(url);

                HttpURLConnection connection1 = (HttpURLConnection) urlObj.openConnection();
                // if we get here then successfully opened URL over HTTP protocol

                String jsonResult = "";
                //char by char we are going to build the json string from an input stream
                InputStream in1 = connection1.getInputStream();
                InputStreamReader reader = new InputStreamReader(in1);
                int data = reader.read();
                while(data != -1) { // -1 is returned at end of input stream
                    jsonResult += (char) data;
                    data = reader.read();
                }
                JSONArray jsonArray = new JSONArray(jsonResult);
                reader.close();

                if(mediaType.equalsIgnoreCase("video")) {
                    return jsonArray.getString(2);
                }
                else if(mediaType.equalsIgnoreCase("image")) {
                    return jsonArray.getString(1);
                }
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
            return "";
        }

        @Override
        protected NasaMedia[] doInBackground(String... strings) {
            String url = strings[0];
            try{
                URL urlObj = encodeURL(url);

                HttpURLConnection connection1 = (HttpURLConnection) urlObj.openConnection();
                // if we get here then successfully opened URL over HTTP protocol


                // this below is what is making it load forever...
                // need to figure out a better way to get inputstream to read faster instead
                // of char by char.
                String jsonResult = "";
                //char by char we are going to build the json string from an input stream
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