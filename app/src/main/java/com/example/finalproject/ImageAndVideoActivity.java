package com.example.finalproject;
/**
 * This is the image and video activity where images and video media are displayed in a list view from the nasa API
 * @authors: Cole & Jackson
 * @version: v1.0
 * @date: 12/11/2019
 */

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;


public class ImageAndVideoActivity extends AppCompatActivity {
    private static final String TAG = "ImageAndVideoActivity";
    private NasaMedia[] mediaInfo = null;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_and_video);

        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.GONE);

        listView = (ListView) findViewById(R.id.listOfResults);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NasaMedia media = (NasaMedia) adapterView.getItemAtPosition(i);
                Log.d(TAG, "onItemClick: " + media.getMediaLink() + " " + media.getMediaType());
                Intent intent = new Intent(ImageAndVideoActivity.this, DisplayMediaActivity.class);
                intent.putExtra("mediaObj", media);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return true; // so the onItemClick() callback is not also called
            }
        });

        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText input = (EditText) findViewById(R.id.searchInput);

                String strInput = input.getText().toString();
                if(strInput.equals("") || strInput.equals(" ")) {
                    Snackbar.make(listView, R.string.search_error, Snackbar.LENGTH_SHORT).show();
                }
                else{
                    NasaLibrarySearch fetchVideo = new NasaLibrarySearch(ImageAndVideoActivity.this);
                    fetchVideo.fetchImgOrVideo(strInput);
                }
            }
        });
    }

    /**
     * received media function which gets nasa media array from nasa library search
     * @param mediaInfoArr an array of nasa media objects
     */
    public void receivedMedia(NasaMedia[] mediaInfoArr){
        if(mediaInfoArr != null && mediaInfoArr.length > 0) {
            this.mediaInfo = mediaInfoArr;

            ArrayAdapter<NasaMedia> arrayAdapter = new ArrayAdapter<NasaMedia>(this, R.layout.nasa_media_row, R.id.media_title, mediaInfo){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    // need to write code in here
                    View view = super.getView(position, convertView, parent);

                    NasaMedia mediaObj = mediaInfo[position];

                    TextView title = (TextView) view.findViewById(R.id.media_title); // don't forget the view.
                    title.setText(mediaObj.getTitle());

                    TextView description = (TextView) view.findViewById(R.id.media_desc); // don't forget the view.
                    if (mediaObj.getDescription().length() > 80){
                        String smallDesc = "";
                        for(int i = 0; i < 80; i++) {
                            smallDesc += mediaObj.getDescription().charAt(i);
                        }
                        smallDesc += "...";
                        description.setText(smallDesc);
                    }
                    else {
                        description.setText(mediaObj.getDescription());
                    }

                    TextView mediaType = (TextView) view.findViewById(R.id.media_type); // don't forget the view.
                    mediaType.setText(mediaObj.getMediaType());

                    return view;
                }
            };

            listView.setAdapter(arrayAdapter);
        }
        else {
            Snackbar.make(listView, R.string.no_search_results, Snackbar.LENGTH_SHORT).show();
        }
    }
}
