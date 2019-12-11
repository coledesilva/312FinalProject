package com.example.finalproject;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;


public class NasaLibrarySearch {
    static final String BASE_URL = "https://images-api.nasa.gov";
    ImageAndVideoActivity imageAndVideoActivity = null;
    static final String TAG = "ImageAndVideo";


    public NasaLibrarySearch(ImageAndVideoActivity imageAndVideoActivity) {
        this.imageAndVideoActivity = imageAndVideoActivity;
    }

    public void fetchImgOrVideo(String searchStr){
        String url = constructImageAndVideoUrl(searchStr);
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

    public String constructImageAndVideoUrl(String searchStr){
        String baseUrl = BASE_URL;
        baseUrl += "/search?q=" + searchStr;

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
            Log.d(TAG, "onPostExecute: starting execute");
        }

        @Override
        protected void onPostExecute(NasaMedia[] media) {
            super.onPostExecute(media);
            ProgressBar progress = (ProgressBar) imageAndVideoActivity.findViewById(R.id.progressBar);
            progress.setVisibility(View.GONE);
            Log.d(TAG, "onPostExecute: done executing");
            imageAndVideoActivity.receivedMedia(media);
        }

        private NasaMedia[] parseVideo(JSONObject jsonObject){
            try{
                JSONObject collection = jsonObject.getJSONObject("collection");
                JSONObject metadata = collection.getJSONObject("metadata");
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

                    JSONArray items = collection.getJSONArray("items");

                    JSONObject tempItems = items.getJSONObject(i);
                    JSONArray tempData = tempItems.getJSONArray("data");
                    JSONObject data = tempData.getJSONObject(0);

                    mediaObj.setDescription(data.getString("description"));

                    mediaObj.setTitle(data.getString("title"));

                    mediaObj.setMediaType(data.getString("media_type"));

                    String nasaId = data.getString("nasa_id");

                    mediaObj.setMediaLink(returnMedia(nasaId, data.getString("media_type")));

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

        private String returnMedia(String nasaId, String mediaType) {
            try{
                String manifestUrl = BASE_URL;
                manifestUrl += "/asset/" + nasaId;
                URL urlObj = encodeURL(manifestUrl);

                Log.d(TAG, "returnMedia: " + urlObj.toString());
                HttpURLConnection connection1 = (HttpURLConnection) urlObj.openConnection();
                // if we get here then successfully opened URL over HTTP protocol

                InputStream in1 = connection1.getInputStream();
                StringWriter writer = new StringWriter();
                IOUtils.copy(in1, writer, StandardCharsets.UTF_8);
                String jsonResponse = writer.toString();
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONObject collection = jsonObject.getJSONObject("collection");
                JSONArray items = collection.getJSONArray("items");

                for(int i = 0; i < items.length(); i++){
                    JSONObject tempItems = items.getJSONObject(i);
                    String response = tempItems.getString("href");

                    if((response.endsWith("small.jpg") || response.endsWith("medium.jpg"))&& mediaType.equalsIgnoreCase("image")){
                        Log.d(TAG, "returnMedia: " + response);
                        Log.d(TAG, "returnMedia: is image");
                        return response;
                    }
                    else if(response.endsWith(".mp4") && mediaType.equalsIgnoreCase("video")){
                        Log.d(TAG, "returnMedia: " + response);
                        Log.d(TAG, "returnMedia: is video");
                        return response;
                    }
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
                connection1.connect();

                InputStream in1 = connection1.getInputStream();
                StringWriter writer = new StringWriter();
                IOUtils.copy(in1, writer, StandardCharsets.UTF_8);
                String jsonResponse = writer.toString();

                Log.d(TAG, "doInBackground: " + jsonResponse);
                JSONObject jsonObject = new JSONObject(jsonResponse);

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