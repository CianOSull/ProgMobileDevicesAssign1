package com.example.assign1androidhexconverter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // The manifest file has been changed so this runs first, remember to take a picture of that
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                Intent i = new Intent(splashScreen.this, MainActivity.class);
                startActivity(i);
                finish(); } }, 3000);
    }
}
