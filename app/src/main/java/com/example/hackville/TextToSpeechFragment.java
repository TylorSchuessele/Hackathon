package com.example.hackville;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextToSpeechFragment extends Fragment {

    //TTS string
    public final String TTS_MESSAGE = "This button will read the text currently on the screen. Green button = Ok";

    //Views
    public ImageButton btnSpeaker;
    public Button btnOk;

    CallBackInterface callBackInterface;
    public void setCallBackInterface(CallBackInterface callBackInterface){
        this.callBackInterface = callBackInterface;
    }

    public TextToSpeechFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.layout_text_to_speech, container, false);

        //Init views
        btnSpeaker = view.findViewById(R.id.button_speak);
        btnSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackInterface.textToSpeech(TTS_MESSAGE);
            }
        });

        btnOk = view.findViewById(R.id.button_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackInterface.startLoginFragment();
            }
        });

        return view;
    }
}
