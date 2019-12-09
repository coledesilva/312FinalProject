package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.net.URI;
import java.net.URL;

public class ImageAndVideoActivity extends AppCompatActivity {
    MediaPlayer mp = null;
    private NasaMedia[] mediaInfo;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoView = (VideoView) findViewById(R.id.videoView);
        setContentView(R.layout.activity_image_and_video);
        NasaLibrarySearch fetchVideo = new NasaLibrarySearch(this);
        fetchVideo.fetchImgOrVideo();
    }

    public void receivedMedia(NasaMedia[] mediaInfo){
        if(mediaInfo != null) {
            this.mediaInfo = mediaInfo;

            if(mediaInfo[0].getMediaType().equalsIgnoreCase("video")){
                playVideo(videoView, mediaInfo[0].getMediaLink());
            }
        }
    }


    public void playVideo(View view, String url) {
        videoView.setVisibility(View.VISIBLE);

        // let's add controls to our videoview
        MediaController mc = new MediaController(this);
        // let mc know about its videoview
        // let the videoview know about its mediacontroller
        mc.setAnchorView(videoView);
        videoView.setMediaController(mc);
        // save state
        // 1. onPause()/onResume(): the app loses focus. save position with a field
        // 2. onSaveInstanceState()/onCreate(): rotate device, change config etc.
        // 3. SharedPreferences: save state across app sessions

        // we can play videos
        // 1. in our project (res/raw) (see notes on Google Drive for an example)
        //videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.sample_video);
        // 2. already on the user's device
        // 3. that we stream from a url (in this example)
        String urlToStream = url;
        // we need a Uri object to represent the url
        // when we learned about implicit intents we used Uri for a webpage
        Uri uri = Uri.parse(urlToStream);
        videoView.setVideoURI(uri);
        videoView.start();
    }
}
