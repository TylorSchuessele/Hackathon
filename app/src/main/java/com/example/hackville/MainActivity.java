package com.example.hackville;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CallBackInterface {

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
        textToSpeechFragment.setCallBackInterface(this);

        fragmentTransaction.add(R.id.container, textToSpeechFragment, null);
        fragmentTransaction.commit();

        //fragmentManager.beginTransaction().replace(R.id.container, new LoginPage(), null).commit();
        fragmentManager.beginTransaction().add(R.id.container, new LoginPage(), null).commit();

    }

    @Override
    public void testCallback() {
        fragmentManager.beginTransaction().replace(R.id.container, new LoginPage()).commit();
    }

    @Override
    public void yestTest() {
        Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noTest() {
        Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
    }
}
