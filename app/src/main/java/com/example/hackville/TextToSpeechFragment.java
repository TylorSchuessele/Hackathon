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

/**
 * A simple {@link Fragment} subclass.
 */
public class TextToSpeechFragment extends Fragment {

    private Button btnYes;
    private Button btnNo;

    CallBackInterface callBackInterface;

    public void setCallBackInterface(CallBackInterface callBackInterface){
        this.callBackInterface = callBackInterface;
    }

    private View.OnClickListener loginListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            //MainActivity.fragmentManager.beginTransaction().replace(R.id.container, new LoginPage(), null).commit();
//            android.app.Fragment test = getActivity().getFragmentManager().findFragmentById(R.id.container);
//            getActivity().getFragmentManager().beginTransaction().remove(test).commit();
            if (callBackInterface != null){
//                callBackInterface.callBackMethod();
                if (view.getId() == R.id.button_yes){
                    //callBackInterface.yestTest();
                    callBackInterface.textToSpeech("Yes");
                }else{
                    //callBackInterface.noTest();
                    callBackInterface.textToSpeech("No");
                }

                //callBackInterface.testCallback();
            }
        }
    };

    public TextToSpeechFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.layout_text_to_speech, container, false);

        btnYes = view.findViewById(R.id.button_yes);
        btnNo = view.findViewById(R.id.button_no);

        btnYes.setOnClickListener(loginListener);
        btnNo.setOnClickListener(loginListener);

        return view;
    }
}
