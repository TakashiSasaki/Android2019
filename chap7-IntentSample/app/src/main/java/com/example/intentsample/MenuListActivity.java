package com.example.intentsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvMenu = findViewById(R.id.lvMenu);

        List<Map<String,String>> menuList = new ArrayList<>();

        Map<String, String> menu = new HashMap<>();
        menu.put("name", "karaage");
        menu.put("price", "800yen");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "hamburg");
        menu.put("price", "850yen");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "ginger");
        menu.put("price", "850yen");
        menuList.add(menu);

        String[] from = {"name", "price"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter
                (getApplicationContext(), menuList,
                        android.R.layout.simple_list_item_2,
                        from, to);

        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                ((TextView) view).setText("＜＜" + textRepresentation + "＞＞");
                return false;
            }//setViewValue
        });

        lvMenu.setAdapter(adapter);
    }
}
