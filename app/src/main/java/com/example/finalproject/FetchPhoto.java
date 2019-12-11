package com.example.finalproject;
/**
 * This is the async task class to fetch a photo from the NASA API
 * @authors: Cole & Jackson
 * @version: v1.0
 * @date: 12/11/2019
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class FetchPhoto {
    private Activity activity;
    private boolean flag;
    private static final String TAG = "FetchPhoto";

    public FetchPhoto(Context context, boolean flag) {
        this.activity = (Activity) context;
        this.flag = flag;
    }

    /**
     * fetch photo which starts async task
     * @param photoURL string url of photo to fetch
     */
    public void fetchPhotoBitmap(String photoURL){
        FetchPhotoAsyncTask asyncTask = new FetchPhotoAsyncTask();
        asyncTask.execute(photoURL);
    }

    class FetchPhotoAsyncTask extends AsyncTask<String, Void, Bitmap> {

        public FetchPhotoAsyncTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar progress = (ProgressBar) activity.findViewById(R.id.progressBar);
            progress.setVisibility(View.VISIBLE);
        }


        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url = null;

            if(flag == true){
                String strUrl = strings[0];
                strUrl += "?api_key" + activity.getString(R.string.API_KEY);
                url = encodeURL(strUrl);
            }
            else {
                url = encodeURL(strings[0]);
            }

            Bitmap bitmap = null;

            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.connect();

                Log.d(TAG, "doInBackground: " + url.toString());
                InputStream in = urlConnection.getInputStream();

                return BitmapFactory.decodeStream(in);
                
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
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

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ProgressBar progress = (ProgressBar) activity.findViewById(R.id.progressBar);
            progress.setVisibility(View.GONE);


            FetchPhotoListener temp = (FetchPhotoListener) activity;
            temp.receivePhotoBitmap(bitmap);
        }
    }
}
