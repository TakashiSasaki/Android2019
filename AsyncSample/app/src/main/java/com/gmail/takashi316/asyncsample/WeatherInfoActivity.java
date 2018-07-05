package com.gmail.takashi316.asyncsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);

        ListView lvCityList = this.findViewById(R.id.lvCityList);
        List<Map<String,String>> cityList = new ArrayList<>();
        Map<String, String> city = new HashMap<>();
        city.put("name","大阪");
        city.put("id", "270000");
        cityList.add(city);
        city = new HashMap<>();
        city.put("name", "神戸");
        city.put("id", "280010");
        cityList.add(city);
        city = new HashMap<>();
        city.put("name", "松山");
        city.put("id", "380010");
        cityList.add(city);

        String[] from={"name"};
        int[] to={android.R.id.text1};

        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                cityList, android.R.layout.simple_expandable_list_item_1, from, to);

        lvCityList.setAdapter(adapter);

    }//onCreate

    private class ListItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Map<String, String> item = (Map<String,String>) adapterView.getItemAtPosition(i);
            String cityName = item.get("name");
            String cytyId = item.get("id");
            TextView tvCityName = findViewById(R.id.tvCityName);
            tvCityName.setText(cityName + "の天気：");
        }//onItemClick
    }//ListItemClickListener

}//WeatherInfoActivity
