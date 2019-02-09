package com.example.hackville;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.LinkedList;

public class ChooseTopicActivity extends AppCompatActivity {

    // RecyclerView of interests
    private RecyclerView recyclerView;

    // adapter for interests
    private InterestListAdapter mAdapter;

    // List of interests
    private final LinkedList<String> interests = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_topic);


        String[] interestsArray = getResources().getStringArray(R.array.interests);
        for (int i = 0; i < interestsArray.length; i++) {
            interests.addLast(interestsArray[i]);

        }

        // Connects recyclerView
        recyclerView = (RecyclerView) findViewById(R.id._recyclerView);

        mAdapter = new InterestListAdapter(this, interests);

        recyclerView.setAdapter(mAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}
