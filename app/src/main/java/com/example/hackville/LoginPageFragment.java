package com.example.hackville;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class LoginPageFragment extends Fragment {

    CallBackInterface callBackInterface;

    //views
    EditText edtEmail;
    EditText edtPass;
    Button btnLogin;
    Button btnSignup;
    Button btnGoogle;

    //sets up the views
    private void setupViews(View view){

        //setup the views
        edtEmail = view.findViewById(R.id.edt_email);
        edtPass = view.findViewById(R.id.edt_pass);
        btnLogin = view.findViewById(R.id.btn_Login);
        btnSignup = view.findViewById(R.id.btn_signup);
        btnGoogle = view.findViewById(R.id.btn_Google);

        //set the onclick listeners for the buttons
        btnLogin.setOnClickListener(loginListener);
        btnSignup.setOnClickListener(loginListener);

        btnGoogle.setOnClickListener(OAuthListener);
    }

    // Required empty public constructor
    public LoginPageFragment() {

    }

    //sets the CallBackInterface to communicate with the main activity
    public void setCallBackInterface(CallBackInterface callBackInterface){
        this.callBackInterface = callBackInterface;
    }

    //region OnClickListeners

    //Click listener handling the login
    public View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (callBackInterface != null){

                int id = view.getId();

                String email = edtEmail.getText().toString();
                String pass = edtPass.getText().toString();

                if (id == R.id.btn_Login) {

                    callBackInterface.login(email,pass);

                }else if (id == R.id.btn_signup){

                    callBackInterface.signUp(email,pass);

                }

            }
        }
    };

    public View.OnClickListener OAuthListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (callBackInterface != null){

                int id = view.getId();

                if (id == R.id.btn_Google){
                    //TODO: Make google OAuth work
                    callBackInterface.googleLogin();
                }

                //TODO: Add Facebook Login work
                //else if (id == R.id.btn_FaceBook){
                //
                //}

            }
        }
    };

    //endregion

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_page, container, false);

        setupViews(view);

        return view;

    }


}
