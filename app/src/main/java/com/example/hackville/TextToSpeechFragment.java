package com.example.hackville;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
    private final String TTS_MESSAGE = "This button will read the text currently on the screen. Green button = Ok";

    //Views
    private ImageButton btnSpeaker;
    private Button btnOk;

    //Holds the current view
    private View view;

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

        view =  inflater.inflate(R.layout.layout_text_to_speech, container, false);

        //Init views
        btnSpeaker = view.findViewById(R.id.button_speak);
        btnSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackInterface.stopTTS();
                callBackInterface.textToSpeech(TTS_MESSAGE);
            }
        });

        btnOk = view.findViewById(R.id.button_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackInterface.goToLogin();
            }
        });

        return view;
    }
}
