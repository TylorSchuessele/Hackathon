package com.example.hackville;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    public LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.container) != null) {

            if(savedInstanceState != null) {
                return;
            }
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TextToSpeechFragment textToSpeechFragment = new TextToSpeechFragment();
        fragmentTransaction.add(R.id.container, textToSpeechFragment, null);
        fragmentTransaction.commit();

    }
}
