package com.example.fragmenteventlistener;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
        implements BlankFragment.OnGetMessageListener {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
    }//onCreate

    @Override
    public String onGetMessage() {
        return "OnGetMessageListener#onGetMessage : "
                + editText.getText().toString();
    }//onGetMessage

    public String getMessage() {
        return "MainActivity#getMessage : "
                + editText.getText().toString();
    }//getMessage
}//MainActivity
