package com.example.finalproject;
/**
 * This is the main activity where the NASA image of the day is displayed
 * It also provides navigation to other activities in this application
 *
 * Sources to cite:
 *      Source for Animation Help: https://www.tutorialspoint.com/android/android_animations.htm
 *
 * @authors: Cole & Jackson
 * @version: v1.0
 * @date: 12/11/2019
 */

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements FetchPhotoListener{
    private String[] APODInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FetchAPOD apiAPOD = new FetchAPOD(this);
        apiAPOD.fetchAPOD();

        Animation frombot = AnimationUtils.loadAnimation(this, R.anim.frombot);

        Button activityButton1 = (Button) findViewById(R.id.imagesAndVideos);
        activityButton1.startAnimation(frombot);

        Button activityButton3 = (Button) findViewById(R.id.marsWeather);
        activityButton3.startAnimation(frombot);

    }

    /**
     * onclick for the buttons on the main activity
     * @param v the view that has been clicked
     */
    public void onButtonClick(View v) {
        Button butt = (Button) v;
        switch(butt.getId()){
            case R.id.imagesAndVideos:
                Intent intent1 = new Intent(MainActivity.this, ImageAndVideoActivity.class);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.marsWeather:
                Intent intent3 = new Intent(MainActivity.this, MarsWeatherActivity.class);
                startActivity(intent3);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.scavengerHunt:
                Intent intent4 = new Intent(MainActivity.this, ScavengerHunt.class);
                startActivity(intent4);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }

    /**
     * received the APOD from the fetch apod async task
     * @param photoInfo string array of the apod info
     */
    public void receivedAPOD(String[] photoInfo){
        if(photoInfo != null) {
            this.APODInfo = photoInfo;
            String photoURLStr = photoInfo[2];

            FetchPhoto fetchingPhoto = new FetchPhoto(this, false);
            fetchingPhoto.fetchPhotoBitmap(photoURLStr);
        }
    }

    public MainActivity() {
        super();
    }

    /**
     * receive photo bitmap implementation from the FetchPhotoInterface
     * @param bitmap the bitmap returned from the fetch photo async task
     */
    @Override
    public void receivePhotoBitmap(Bitmap bitmap) {
        ImageView APODPhoto = (ImageView) findViewById(R.id.photoOfTheDay);
        APODPhoto.setImageBitmap(bitmap);

        String photoTitle = APODInfo[0];
        String photoDate = APODInfo[1];
        TextView titleAndDate = findViewById(R.id.titleAndDate);
        titleAndDate.setTextColor(getResources().getColor(R.color.white));
        titleAndDate.setText(photoTitle + "\n" + photoDate);
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        titleAndDate.setAnimation(fadeIn);
    }
}

