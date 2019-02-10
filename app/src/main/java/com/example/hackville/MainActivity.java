package com.example.hackville;

import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements CallBackInterface {

    private FirebaseAuth mAuth;
    public static FragmentManager fragmentManager;
    private TextToSpeech tts;
    private static float TTS_PITCH = 1f; // The pitch for the tts bot
    private static final String TAG = "MainActivity";

    //region lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


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
        //fragmentManager.beginTransaction().add(R.id.container, new LoginPage(), null).commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
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


    //endregion

    //region Firebase Auth

    private void updateUI(FirebaseUser currentUser) {


    }

    private void getUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
    }

    //endregion

    /**
     * Play audio version of message
     * @param message The message you want to be said
     */
    @Override
    public void textToSpeech(String message) {
        Log.d("MainActivity", message);
        tts.speak(message, TextToSpeech.QUEUE_ADD, null);
    }

    /**
     * Stops the TTS
     */
    public void stopTTS() {
        tts.stop();
    }

    //region callback methods

    @Override
    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    //region fragment navigation

    @Override
    public void goToLogin() {
        LoginPageFragment loginPageFragment = new LoginPageFragment();
        loginPageFragment.setCallBackInterface(this);
        fragmentManager.beginTransaction().replace(R.id.container, loginPageFragment).commit();
    }

    //endregion

    //region firebase calls
    @Override
    public void googleLogin() {

    }

    @Override
    public void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            MailboxFragment mailboxFragment = new MailboxFragment();
                            mailboxFragment.setCallBackInterface(MainActivity.this);
                            fragmentManager.beginTransaction().replace(R.id.container, mailboxFragment).commit();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    public void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            fragmentManager.beginTransaction().replace(R.id.container, new MailboxFragment()).commit();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
    //endregion

    //endregion
}
