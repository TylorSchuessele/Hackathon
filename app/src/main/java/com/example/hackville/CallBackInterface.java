package com.example.hackville;

interface CallBackInterface {
    void yestTest();
    void noTest();
    void textToSpeech(String message);
    void makeToast(String message);
    void goToLogin();
    void googleLogin();
    void login(String email, String pass);
    void signUp(String email, String pass);
}
