package com.example.assign1androidhexconverter;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {
    private final String CHANNEL_ID = "Channel Main";
    private final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the notification
        displayNotificaiton();

        // This handles if an intent is sent to main activity
        if (getIntent().hasExtra("com.example.assign1androidhexconverter.textConversion")){
            TextView hexToTxtResultView = (TextView) findViewById(R.id.hexToTxtResultView);
            String hex = getIntent().getExtras().getString(
                    "com.example.assign1androidhexconverter.textConversion");
            hexToTxtResultView.setText(hex);
        }

        // Create second activity button
        Button secondActBtn = (Button) findViewById(R.id.secondActBtn);

        secondActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This creates the intent fo the second activity
                Intent startIntent = new Intent(getApplicationContext(), secondActivity.class);

                // This gets the text from edit text
                EditText textEditText = (EditText) findViewById(R.id.textEditText);
                String textToHex = textEditText.getText().toString();
                // this converts the text hex using the methods below
                String textConverted = convertToHex(textToHex);

                // This takes the text from the edit text and puts it into the next activity
                startIntent.putExtra("com.example.assign1androidhexconverter.hexConversion",
                        textConverted);
                // This starts the intent and moves hte user to the second acitivity
                // Where the result is displayed
                startActivity(startIntent);
            }
        });

        // This handles the third activity
        Button thirdActBtn = (Button) findViewById(R.id.thirdActBtn);
        thirdActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), thridActivity.class);
                //startIntent.putExtra("com.example.tutoral2.SOMETHING", "HELLO WORLD");
                startActivity(startIntent);
            }
        });
    }

    // This handles hte conversion text to hex
    private String convertToHex(String text){
        RadioButton contRadioBtn = (RadioButton) findViewById(R.id.contRadioBtn);
        RadioButton xRadioBtn = (RadioButton) findViewById(R.id.xRadioBtn);
        RadioGroup radioGroupMain = (RadioGroup) findViewById(R.id.radioGroupMain);
        RadioButton rb = (RadioButton) findViewById(radioGroupMain.getCheckedRadioButtonId());
        String splitString;

        // This decides which method to use depending on which radio button is selected
        if(rb == contRadioBtn){
            splitString = asciiToHex(text);
        }
        else if(rb == xRadioBtn){
            splitString = splitSringX(text);
        }
        else{
            splitString = splitSringByte(text);
        }
        return splitString;
    }

    // This converts strings in to hexadecimal
    private String asciiToHex(String asciiStr) {
        char[] chars = asciiStr.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char ch : chars) {
            hex.append(Integer.toHexString((int) ch));
        }
        return hex.toString();
    }

    // This converts hex strings into \x format
    private   String splitSringX(String hex){
        String ascii = asciiToHex(hex);
        String splitString = "\\x";
        for(int i = 0; i < ascii.length(); i+=2){
            if(i+2 < ascii.length()) {
                splitString += ascii.charAt(i) + "" +  ascii.charAt(i + 1) + "\\x";
            }
            else {
                splitString += ascii.charAt(i) + "" +  ascii.charAt(i + 1);
            }
        }
        return splitString;
    }

    // This converts strings into byte format
    private String splitSringByte(String hex){
        String ascii = asciiToHex(hex);
        String splitString = "";
        for(int i = 0; i < ascii.length(); i+=2){
            if(i+1 < ascii.length()) {
                splitString += ascii.charAt(i) + "" +  ascii.charAt(i + 1) + " ";
            }
            else {
                splitString += ascii.charAt(i) + "" +  ascii.charAt(i + 1);
            }
        }
        return splitString;
    }

    // This creates the notification that will be displayed
    private void displayNotificaiton(){
        // This will create the channels
        createNotificaitonChannel();

        // Create the intent and pending intent
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:0210000000"));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent phoneCallIntent = PendingIntent.getActivity(MainActivity.this,
                0, callIntent, 0);

        // Create hte notificaiton
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        //.setSmallIcon(R.drawable.ic_phone_notification)
                        .setSmallIcon(R.drawable.ic_phone_notification)
                        .setContentTitle("Make a call")
                        .setContentText("Tap the notification to call the phone.")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("This will call the specified number."))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // When tapped close and make call
                        .setContentIntent(phoneCallIntent)
                        .setAutoCancel(true);

        // Send it to the manager to be displayed
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    // This creates the notification channel for androids above oreo
    private void createNotificaitonChannel(){
        if(Build.VERSION_CODES.O <= Build.VERSION.SDK_INT){
            String channelName = "Make a call";
            String description = "Make a call to a number";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel noteChannel = new NotificationChannel(CHANNEL_ID, channelName,
                    importance);
            noteChannel.setDescription(description);

            NotificationManager noteManger = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);
            noteManger.createNotificationChannel(noteChannel);
        }
    }
}
