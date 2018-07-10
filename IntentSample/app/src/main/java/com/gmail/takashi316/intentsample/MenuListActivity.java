package com.gmail.takashi316.intentsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        final ListView lvMenu = (ListView) findViewById(R.id.lvMenu);

        List menuList = new ArrayList<>();

        Map<String, String> menu = new HashMap<>();
        menu.put("name", "からあげていしょく");
        menu.put("price", "800円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "ハンバーグ定食");
        menu.put("price", "850円");
        menuList.add(menu);

        String[] from = {"name", "price"};
        int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                menuList, android.R.layout.simple_list_item_2,
                from, //値を取り出すキーの名前の配列
                to  //取り出した値を反映させるTextViewのIDの配列
                );

        lvMenu.setAdapter(adapter);
        lvMenu.setOnItemClickListener(new ListItemClickListener());
    }

    private class ListItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String,String> item = (Map<String,String>) parent.getItemAtPosition(position);
            String menuName = item.get("name");
            String menuPrice = item.get("price");

            Intent intent = new Intent(getApplicationContext(), MenuThanksActivity.class);
            intent.putExtra("name", menuName);
            intent.putExtra("price", menuPrice);

            startActivity(intent);

        }
    }
}
