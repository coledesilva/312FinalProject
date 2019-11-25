package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private String[] APODInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NASAAPI apiAPOD = new NASAAPI(this);
        apiAPOD.fetchAPOD();
    }

    public void receivedAPOD(String[] photoInfo){
        if(photoInfo != null) {
            this.APODInfo = photoInfo;

            String photoTitle = photoInfo[0];
            String photoDate = photoInfo[1];
            String photoURLStr = photoInfo[2];

            NASAAPI fetchingPhoto = new NASAAPI(this);
            fetchingPhoto.fetchPhotoBitmap(photoURLStr);

            TextView APODTitleAndDate = (TextView) findViewById(R.id.titleAndDate);
            APODTitleAndDate.setText(photoTitle + "\n" + photoDate);

        }
    }

    public void receivedPhotoBitmap(Bitmap bitmap){

        ImageView APODPhoto = (ImageView) findViewById(R.id.photoOfTheDay);
        APODPhoto.setImageBitmap(bitmap);
    }


}

