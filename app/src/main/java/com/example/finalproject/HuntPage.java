package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HuntPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hunt_page);
        Intent intent = getIntent();
        TextView huntTextView;
        if(intent!=null) {
            String content = intent.getStringExtra("content");
            huntTextView = (TextView)findViewById(R.id.huntText);
            huntTextView.setText(content);
        }
    }
}
