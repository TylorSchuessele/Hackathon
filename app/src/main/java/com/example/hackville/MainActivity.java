package com.example.hackville;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements CallBackInterface {

    public static FragmentManager fragmentManager;
    public LinearLayout container;
    public TextToSpeech tts;
    public static float TTS_PITCH = 1f; // The pitch for the tts bot

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Intent intent = new Intent(this, ChooseTopicActivity.class);
        startActivity(intent);
        */
        // Initialize Text to speech
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                    tts.setPitch(TTS_PITCH);
                }

            }
        });

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

    public void onPause(){
        if(tts !=null){
            tts.stop();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if(tts !=null){
            tts.stop();
        }
        super.onDestroy();
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

    /**
     * Play audio version of message
     * @param message The message you want to be said
     */
    @Override
    public void textToSpeech(String message) {
        Log.d("MainActivity", message);
        tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
    }
}
