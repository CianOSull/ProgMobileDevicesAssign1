package com.example.assign1androidhexconverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class thridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thrid);

        Button firstActReturnBtn = (Button) findViewById(R.id.firstActReturnBtn);
        firstActReturnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);

                // This gets the text fro mthe edit text
                EditText hexEditText = (EditText) findViewById(R.id.hexEditText);
                String hexToText = hexEditText.getText().toString();
                // this converts it to hex
                String hexConverted = hexToAscii(hexToText);

                // Use put Extra to change text fields in the other class
                //startIntent.putExtra("com.example.tutoral2.SOMETHING", "HELLO WORLD");
                startIntent.putExtra("com.example.assign1androidhexconverter.textConversion",
                        hexConverted);

                startActivity(startIntent);
            }
        });
    }

    private String hexToAscii(String hex){
        hex = hex.replace(" ", "");
        hex = hex.replace("\\x", "");
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < hex.length(); i+=2) {
            str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        }
        return str.toString();
    }
}
