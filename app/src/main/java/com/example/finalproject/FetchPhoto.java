package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchPhoto {
    private Activity activity;

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
            ProgressBar progress = (ProgressBar) activity.findViewById(R.id.homeScreenProgressBar);
            progress.setVisibility(View.VISIBLE);
        }


        @Override
        protected Bitmap doInBackground(String... strings) {

            Bitmap bitmap = null;

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)
                        url.openConnection();

                InputStream in = urlConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ProgressBar progress = (ProgressBar) activity.findViewById(R.id.homeScreenProgressBar);
            progress.setVisibility(View.GONE);

            FetchPhotoListener temp = (FetchPhotoListener) activity;
            temp.recievePhotoBitmap(bitmap);
        }
    }
}
