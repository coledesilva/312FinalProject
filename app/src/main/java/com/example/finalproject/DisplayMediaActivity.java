package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class DisplayMediaActivity extends AppCompatActivity implements FetchPhotoListener{
    private static final String TAG = "DisplayMediaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_media);
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.GONE);

        TextView title = (TextView) findViewById(R.id.mediaTitleDisplay);
        TextView desc = (TextView) findViewById(R.id.mediaDescDisplay);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        VideoView videoView = (VideoView) findViewById(R.id.videoView);

        Intent intent = getIntent();
        if(intent != null){
            NasaMedia media = (NasaMedia) intent.getSerializableExtra("mediaObj");

            if(media.getMediaType().equalsIgnoreCase("video")){
                title.setText(media.getTitle());
                desc.setText(media.getDescription());
                imageView.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);

                playVideo(media.getMediaLink());
            }
            else if(media.getMediaType().equalsIgnoreCase("image")){
                videoView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);

                FetchPhoto fetchingPhoto = new FetchPhoto(this, true);
                String stringToSend = media.getMediaLink();
                StringBuilder urlBuilder = new StringBuilder(stringToSend);
                urlBuilder.insert(4,'s');
                fetchingPhoto.fetchPhotoBitmap(urlBuilder.toString());

                title.setText(media.getTitle());
                desc.setMovementMethod(new ScrollingMovementMethod());
                desc.setText(media.getDescription());
            }
        }
    }

    @Override
    public void recievePhotoBitmap(Bitmap bitmap) {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        if(bitmap == null) {
            Log.d(TAG, "recievePhotoBitmap: bit is null");
        }

        imageView.setImageBitmap(bitmap);
    }

    public void playVideo(String videoUrl) {
        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        // let's add controls to our videoview
        MediaController mc = new MediaController(this);
        // let mc know about its videoview
        // let the videoview know about its mediacontroller
        mc.setAnchorView(videoView);
        videoView.setMediaController(mc);

        Uri uri = Uri.parse(videoUrl);
        videoView.setVideoURI(uri);
        videoView.start();
    }

}
