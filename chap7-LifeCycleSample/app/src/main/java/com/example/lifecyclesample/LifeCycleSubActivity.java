package com.example.lifecyclesample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class LifeCycleSubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("LifeCycleSample",
               "Sub onCreate() called.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_sub);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("LifeCycleSample",
                "Sub onCreateOptionsMenu() called.");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        Log.i("LifeCycleSample",
                "Sub onStart() called.");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i("LifeCycleSample",
                "Sub onRestart() called.");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i("LifeCycleSample",
                "Sub onResume() called.");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("LifeCycleSample",
                "Sub onPause() called.");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("LifeCycleSample",
                "Sub onStop() called.");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("LifeCycleSample",
                "Sub onDestroy() called.");
        super.onDestroy();
    }

    void onButtonClick(View view){
        Intent intent = new Intent(
                getApplicationContext(),
                LifeCycleMainActivity.class
        );
        startActivity(intent);
    }

    void onFinishButtonClick(View view){
        finish();
    }
}
