package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ScavengerHunt extends AppCompatActivity {
    ListView listView;
    List<HuntText> huntList = new ArrayList<HuntText>();//the notelist
    ArrayAdapter<HuntText> arrayAdapter;//array adapter for notelist

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GridLayout gridLayout = new GridLayout(this);//layout for main activity
        setContentView(gridLayout);
        gridLayout.setColumnCount(1);
        listView = createListView(gridLayout);
        populateArrayList();
        arrayAdapter= new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // the view for each item as a row in the listview
                huntList // data source
        );
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//listener for the note list
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ScavengerHunt.this,HuntPage.class);
                HuntText clickedText = huntList.get(position);
                intent.putExtra("position", position);
                intent.putExtra("content", clickedText.getHuntMessage());
                startActivity(intent);
            }
        });

    }
    /**
     * This function sets the layout parameters for the listview and adds it to the view
     * @param gridLayout the layout of main activity
     * @return the set up list View
     */
    private ListView createListView(GridLayout gridLayout) {
        GridLayout.LayoutParams listParams = new GridLayout.LayoutParams();
        listParams.width = 0;
        listParams.height = 0;
        listParams.rowSpec = GridLayout.spec(1,1,5f);
        listParams.columnSpec=GridLayout.spec(0,1,1f);
        ListView listView = new ListView(this);
        listView.setLayoutParams(listParams);
        gridLayout.addView(listView);
        return listView;
    }

    private void populateArrayList(){
        HuntText huntText1 = new HuntText(getResources().getString(R.string.huntText1)
                ,getResources().getString(R.string.huntTitle1));
        huntList.add(huntText1);
    }

    @Override
    /**
     * handling if user presses the android back button
     */
    public void onBackPressed() {
        super.onBackPressed();
        ScavengerHunt.this.finish();
    }


}
