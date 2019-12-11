package com.example.finalproject;
/**
 * This is the scavenger hunt activity which displays the available scavenger hunts in a list view
 * @authors: Cole & Jackson
 * @version: v1.0
 * @date: 12/11/2019
 */

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
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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

    /**
     * populates the arraylist
     */
    private void populateArrayList(){
        HuntText huntText1 = new HuntText(getResources().getString(R.string.huntText1)
                ,getResources().getString(R.string.huntTitle1));
        HuntText huntText2 = new HuntText(getResources().getString(R.string.huntText2)
                ,getResources().getString(R.string.huntTitle2));
        HuntText huntText3 = new HuntText(getResources().getString(R.string.huntText3)
                ,getResources().getString(R.string.huntTitle3));
        HuntText huntText4 = new HuntText(getResources().getString(R.string.huntText4)
                ,getResources().getString(R.string.huntTitle4));
        HuntText huntText5 = new HuntText(getResources().getString(R.string.huntText5)
                ,getResources().getString(R.string.huntTitle5));
        HuntText huntText6 = new HuntText(getResources().getString(R.string.huntText6)
                ,getResources().getString(R.string.huntTitle6));
        HuntText huntText7 = new HuntText(getResources().getString(R.string.huntText7)
                ,getResources().getString(R.string.huntTitle7));
        huntList.add(huntText1);
        huntList.add(huntText2);
        huntList.add(huntText3);
        huntList.add(huntText4);
        huntList.add(huntText5);
        huntList.add(huntText6);
        huntList.add(huntText7);
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
