package com.gmail.takashi316.menusample;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

        TextView tvMenuName = (TextView) this.findViewById(R.id.tvMenuName);
        TextView tvMenuPrice = (TextView) this.findViewById(R.id.tvMenuPrice);

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

       ActionBar actionBar = this.getActionBar();
       actionBar.setDisplayHomeAsUpEnabled(true);

    }//onCreate

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }//if
        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected

    public void onBackButtonClick(View view){
        this.finish();
    }//onBackButtonClick
}//MenuThanksActivity
