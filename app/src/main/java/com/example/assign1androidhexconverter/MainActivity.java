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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

/*
 There is three linear layouts on the activity main, one is vertical which is the whole screen
 and the other two are horizontal which is inbetween the button and edit text and inbetween the
 first horizontal and the vertical

 Layout of Activities:
 (Swithcing activities will use intents)
 Actvity 1 - MainActivity: Edit Text to enter in text, this text will be passed ot activity 2
 and wil be processed into hex. There will be a button below the edit text to go to activity 2 and
 below that will be a button or activity 3 and a textview of the result of activity 3
 Activity 2: Will pick a format to display the hex in and will be byte, /x or continous string
 Activity 3: This will take hex and convert it to text. There will be an edit text for the hex
 and you pick the format to put it in, maybe actually just use functions to remvoe spaces and /x.
 You will press a button to get back to activity one and the result will be displayed

 */

public class MainActivity extends AppCompatActivity {
    private final String CHANNEL_ID = "Channel Main";
    private final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the notification
        displayNotificaiton();

        if (getIntent().hasExtra("com.example.assign1androidhexconverter.textConversion")){
            TextView hexToTxtResultView = (TextView) findViewById(R.id.hexToTxtResultView);
            String hex = getIntent().getExtras().getString(
                    "com.example.assign1androidhexconverter.textConversion");
            hexToTxtResultView.setText(hex);
        }

       // Here I am initialising the components of the app
        Button secondActBtn = (Button) findViewById(R.id.secondActBtn);

        secondActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), secondActivity.class);

                // This gets the text fro mthe edit text
                EditText textEditText = (EditText) findViewById(R.id.textEditText);
                String textToHex = textEditText.getText().toString();
                // this converts it to hex
                String textConverted = convertToHex(textToHex);

                // Use put Extra to change text fields in the other class
                // This takes the text from the edit text and puts it into the next activity
                startIntent.putExtra("com.example.assign1androidhexconverter.hexConversion",
                        textConverted);
                startActivity(startIntent);
            }
        });

        Button thirdActBtn = (Button) findViewById(R.id.thirdActBtn);
        thirdActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), thridActivity.class);
                // Use put Extra to change text fields in the other class
                //startIntent.putExtra("com.example.tutoral2.SOMETHING", "HELLO WORLD");
                startActivity(startIntent);
            }
        });
    }

    private String convertToHex(String text){
        //RadioButton byteRadioBtn = (RadioButton) findViewById(R.id.byteRadioBtn);
        RadioButton contRadioBtn = (RadioButton) findViewById(R.id.contRadioBtn);
        RadioButton xRadioBtn = (RadioButton) findViewById(R.id.xRadioBtn);
        RadioGroup radioGroupMain = (RadioGroup) findViewById(R.id.radioGroupMain);
        RadioButton rb = (RadioButton) findViewById(radioGroupMain.getCheckedRadioButtonId());
        String splitString;

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

    private void displayNotificaiton(){
        // This will create the channels
        createNotificaitonChannel();

        Toast.makeText(MainActivity.this, "Calling Number", Toast.LENGTH_SHORT).show();
        Intent callIntent = new Intent(this, MainActivity.class);
        callIntent.setData(Uri.parse("tel:021-0000000"));
        PendingIntent phoneCallIntent = PendingIntent.getActivity(MainActivity.this,
                0, callIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_phone_notification)
                        .setContentTitle("Make a call")
                        .setContentText("Tap the notification to call the phone.")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("This will call the specified number."))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // When tapped close and make call
                        .setContentIntent(phoneCallIntent)
                        .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

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
