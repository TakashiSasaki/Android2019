package com.gmail.takashi316.servicesample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SoundServiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_service);

        Intent intent = getIntent();
        boolean fromNotification = intent.getBooleanExtra("fromNotification", false);
        if(fromNotification == true){
            findViewById(R.id.btPlay).setEnabled(false);
            findViewById(R.id.btStop).setEnabled(true);
        }//if
    }//onCreate

    public void onPlayButtonClick(View view){
        Intent intent = new Intent(getApplicationContext(),
                SoundManagerService.class);
        startService(intent);
        findViewById(R.id.btPlay).setEnabled(false);
        findViewById(R.id.btStop).setEnabled(true);
    }//onPlayButton

    public void onStopButtonClick(View view){
        Intent intent = new Intent(getApplicationContext(),
                SoundManagerService.class);
        stopService(intent);
        findViewById(R.id.btPlay).setEnabled(true);
        findViewById(R.id.btStop).setEnabled(false);
    }//onStopButton

}//SoundManagerService
