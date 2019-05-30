package com.example.intentsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        List <Map<String,String>> menuList = new ArrayList<>();

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
                        from,  //値を取り出すキーの名前の配列
                        to  //取り出した値を反映させるTextViewのIDの配列
                );

        //アダプターのsetViewBinderで、
        //データからビューを作成するときに操作を加えられる。
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                ((TextView) view).setText("＜＜" + textRepresentation + "＞＞");
                return false;
            }//setViewValue
        });

        lvMenu.setAdapter(adapter);
        lvMenu.setOnItemClickListener(new ListItemClickListener());
    }//onCreate

    private class ListItemClickListener
    implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent,
                                View view, int position, long id) {
            Map item = (Map) parent.getItemAtPosition(position);
            String menuName = (String) item.get("name");
            String menuPrice = (String) item.get("price");
            Intent intent = new Intent(
                    getApplicationContext(),
                    MenuThanksActivity.class
            );
            intent.putExtra("menuName", menuName);
            intent.putExtra("menuPrice", menuPrice);
            startActivity(intent);
        }
    }


}//MenuListActivity
