package com.example.menusample;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MenuThanksActivity extends AppCompatActivity {

    TextView tvMenuName;
    TextView tvMenuPrice;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_thanks);

        tvMenuName = findViewById(R.id.tvMenuName);
        tvMenuPrice = findViewById(R.id.tvMenuPrice);

        Intent intent = getIntent();
        String menuName = intent.getStringExtra("menuName");
        String menuPrice = intent.getStringExtra("menuPrice");

        tvMenuName.setText(menuName);
        tvMenuPrice.setText(menuPrice);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }//onCreate

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
          this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void onBackButtonClick(View view) {
        finish();
    }
}

