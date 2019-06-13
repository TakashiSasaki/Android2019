package com.example.lifecyclesample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class LifeCycleMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("LifeCycleSample",
                "Main onCreate() called.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("LifeCycleSample",
                "Main onCreateOptionsMenu() called.");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        Log.i("LifeCycleSample",
                "Main onStart() called.");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i("LifeCycleSample",
                "Main onRestart() called.");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i("LifeCycleSample",
                "Main onResume() called.");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("LifeCycleSample",
                "Main onPause() called.");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("LifeCycleSample",
                "Main onStop() called.");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("LifeCycleSample",
                "Main onDestroy() called.");
        super.onDestroy();
    }

    void onButtonClick(View view){
        Intent intent = new Intent(
          getApplicationContext(),
          LifeCycleSubActivity.class
        );
        startActivity(intent);
    }

}
