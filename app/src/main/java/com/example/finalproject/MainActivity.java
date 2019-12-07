/**
 *
 * Source for Animation Help: https://www.tutorialspoint.com/android/android_animations.htm
 *
 */
package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String[] APODInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NASAAPI apiAPOD = new NASAAPI(this);
        apiAPOD.fetchAPOD();

        Animation frombot = AnimationUtils.loadAnimation(this, R.anim.frombot);

        Button activityButton1 = (Button) findViewById(R.id.imagesAndVideos);
        activityButton1.startAnimation(frombot);

        Button activityButton2 = (Button) findViewById(R.id.astroids);
        activityButton2.startAnimation(frombot);

        Button activityButton3 = (Button) findViewById(R.id.marsWeather);
        activityButton3.startAnimation(frombot);

    }

    public void onButtonClick(View v) {
        Button butt = (Button) v;
        switch(butt.getId()){
            case R.id.imagesAndVideos:
                Intent intent1 = new Intent(MainActivity.this, ImageAndVideoActivity.class);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.astroids:
                Intent intent2 = new Intent(MainActivity.this, AsteroidWatcherActivity.class);
                startActivity(intent2);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.marsWeather:
                Intent intent3 = new Intent(MainActivity.this, MarsWeatherActivity.class);
                startActivity(intent3);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
        }
    }


    public void receivedAPOD(String[] photoInfo){
        if(photoInfo != null) {
            this.APODInfo = photoInfo;
            String photoURLStr = photoInfo[2];

            NASAAPI fetchingPhoto = new NASAAPI(this);
            fetchingPhoto.fetchPhotoBitmap(photoURLStr);
        }
    }

    public void receivedPhotoBitmap(Bitmap bitmap){
        ImageView APODPhoto = (ImageView) findViewById(R.id.photoOfTheDay);
        APODPhoto.setImageBitmap(bitmap);

        String photoTitle = APODInfo[0];
        String photoDate = APODInfo[1];
        TextView titleAndDate = findViewById(R.id.titleAndDate);
        titleAndDate.setText(photoTitle + "\n" + photoDate);
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        titleAndDate.setAnimation(fadeIn);
    }


}

