package com.example.asyncsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView tvCityName;
    ListView lvCityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCityName = findViewById(R.id.tvCityName);
        lvCityList = findViewById(R.id.lvCityList);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                tvCityName.setText("Imabari");
            }
        });
        // It causes an error of UI thread violation.
        //thread.start();

        tvCityName.setText("Matsuyama");

        List<Map<String, String>> cityList
                = new ArrayList<>();
        Map<String, String> city
                = new HashMap<>();
        city.put("name", "Osaka");
        city.put("id", "270000");
        cityList.add(city);
        city = new HashMap<>();
        city.put("name", "Matsuyama");
        city.put("id", "380010");
        cityList.add(city);

        String[] from = {"name"};
        int[] to = {android.R.id.text1};

        SimpleAdapter adapter = new SimpleAdapter(
                getApplicationContext(),
                cityList,
                android.R.layout.simple_expandable_list_item_1,
                from, to);
        lvCityList.setAdapter(adapter);

    }

}
