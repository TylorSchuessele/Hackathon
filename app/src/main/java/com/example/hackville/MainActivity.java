package com.example.hackville;

import android.content.Intent;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements CallBackInterface, GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth mAuth;
    public static FragmentManager fragmentManager;
    private TextToSpeech tts;
    private static float TTS_PITCH = 1f; // The pitch for the tts bot
    private static final String TAG = "MainActivity";

    GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 9001;
    private String lang = Locale.getDefault().getLanguage();
    private Locale local = Locale.getDefault();
    private String transTxt;


    //region lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        Log.w(TAG,local.toString());

        // Initialize Text to speech
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(local);
                    tts.setPitch(TTS_PITCH);
                }

            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        translate(getString(R.string.text_to_speech_label), lang);

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
    public void translate(String text, String target) {
        Log.w(TAG,"before URL");

        StringBuilder str  = new StringBuilder();

        String url = "https://translation.googleapis.com/language/translate/v2";
        String preTarget = "?target=";
        String preKey = "&key=";
        String key = "AIzaSyBDIsyoTXSeJDjlVAl5YVw2b9kpesDmXbc";
        String preText = "&q=";
        str.append(url);
        str.append(preTarget);
        str.append(target);
        str.append(preKey);
        str.append(key);
        str.append(preText);
        str.append(text);

        Log.w(TAG,"after URL");
        Log.w(TAG,str.toString());

        url = str.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.w(TAG,response.toString());
                        try {
                           JSONObject data = response.getJSONObject("data");
                           JSONArray trans = data.getJSONArray("translations");
                           JSONObject txt = trans.getJSONObject(0);
                           String txt2 = txt.getString("translatedText");
                           Log.w(TAG, txt2);
                           transTxt = txt2;
                        } catch (JSONException e) {
                            Log.w(TAG,"fail");
                        }

                        if (!Locale.getDefault().getLanguage().equals("en")){
                            String mainTxt = (transTxt != null) ? transTxt : getString(R.string.text_to_speech_label);
                            TextView textView = findViewById(R.id.textView);
                            textView.setText(mainTxt);
                            Log.w(TAG,"test:"+mainTxt);
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.w(TAG,error);
                    }
                });

        Volley.newRequestQueue(this).add(jsonObjectRequest);


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
        tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
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

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        goToMailbox();

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
                            goToMailbox();

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
                            goToMailbox();
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

    @Override
    public void goToMailbox() {

        MailboxFragment mailboxFragment = new MailboxFragment();
        mailboxFragment.setCallBackInterface(MainActivity.this);
        fragmentManager.beginTransaction().replace(R.id.container, mailboxFragment).commit();

    }

    //endregion

    //endregion
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.container), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
