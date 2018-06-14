package com.gmail.takashi316.menusample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuThanksActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_thanks);

        Intent intent = getIntent();
        String name = intent.getStringExtra("menuName");
        String price = intent.getStringExtra("menuPrice");

        TextView tvMenuName = (TextView) findViewById(R.id.tvMenuName);
        TextView tvMenuPrice = (TextView) findViewById(R.id.tvMenuPrice);

        tvMenuName.setText(name);
        tvMenuPrice.setText(price);


       /* 昔はIDを設定してこんな書き方をするしかなかった。
        ((Button) findViewById(R.id.buttonFinish)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        */
    }

    public void onBackButtonClick(View view){
        finish();
    }
}
