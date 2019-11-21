package com.example.toolbarsample;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;

//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.app.AppCompatActivity;


public class ScrollArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_article);
        TextView textViewArtile = findViewById(R.id.textViewArticle);
        textViewArtile.setText(R.string.tv_article);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //toolbar.setSubtitleTextColor(Color.LTGRAY)
        //setSupportActionBar(toolbar);
        //setActionBar(toolbar);
    }
}
