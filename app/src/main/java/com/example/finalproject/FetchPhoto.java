package com.example.finalproject;

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
    private static final String TAG = "FetchPhoto";

    public FetchPhoto(Context context) { this.activity = (Activity) context; }
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

            Bitmap bitmap = null;

            try {
                URL url = encodeURL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)
                        url.openConnection();
                
                Log.d(TAG, "doInBackground: " + url.toString());
                InputStream in = urlConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(in);
                if(bitmap == null) {
                    Log.d(TAG, "doInBackground: bit is null :/");
                }
                
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
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
            temp.recievePhotoBitmap(bitmap);
        }
    }
}
