package com.example.intentsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    }

    void onBackButtonClick(View view){
        finish();
    }
}
