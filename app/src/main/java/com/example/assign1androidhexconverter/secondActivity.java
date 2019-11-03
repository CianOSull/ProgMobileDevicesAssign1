package com.example.assign1androidhexconverter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class secondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button returnToMainBtn = (Button) findViewById(R.id.returnToMainBtn);
        returnToMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainPage();
            }
        });

        //hexToTxtTextView
        // Attempts to launch an activity wihtin own app
        if (getIntent().hasExtra("com.example.assign1androidhexconverter.hexConversion")){
            TextView hexToTxtTextView = (TextView) findViewById(R.id.hexToTxtTextView);
            String text = getIntent().getExtras().getString(
                    "com.example.assign1androidhexconverter.hexConversion");
            hexToTxtTextView.setText(text);
        }

        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setMessage("This is a Navigator to other Pages:");
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Back to Main page",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Intent to go back to main activity
                        backToMainPage();
                    }
                });
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Stay on this Page",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // If have time t put a toast message here
                        Toast showToast = Toast.makeText(secondActivity.this,
                                "You choose to this on this activity", Toast.LENGTH_SHORT);
                        showToast.show();
                    }
                });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Go to the Third Page",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Intent to third page
                        Intent startIntent = new Intent(getApplicationContext(), thridActivity.class);
                        // Use put Extra to change text fields in the other class
                        //startIntent.putExtra("com.example.tutoral2.SOMETHING", "HELLO WORLD");
                        startActivity(startIntent);
                    }
                });
        Button alertBtn = (Button) findViewById(R.id.alertBtn);
        alertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    private void backToMainPage(){
        Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
        // Use put Extra to change text fields in the other class
        startActivity(startIntent);
    }
}
