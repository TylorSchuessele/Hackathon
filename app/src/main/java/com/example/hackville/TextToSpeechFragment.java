package com.example.hackville;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextToSpeechFragment extends Fragment {

    private Button button;
    private Button button2;

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
                if (view.getId() == R.id.button){
                    callBackInterface.yestTest();
                }else{
                    callBackInterface.noTest();
                }

                callBackInterface.testCallback();
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

        button = view.findViewById(R.id.button);
        button2 = view.findViewById(R.id.button2);

        button.setOnClickListener(loginListener);
        button2.setOnClickListener(loginListener);

        return view;


    }

}
