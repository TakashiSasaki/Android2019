package com.example.recyclerviewsample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScrollListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        //教科書では変数名が toolbar となっていますがここでは collapsingToolbarLayout としました。
        CollapsingToolbarLayout collapsingToolbarLayout =
                findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle(getString(R.string.toolbar_title));
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.LTGRAY);

        RecyclerView lvMenu = findViewById(R.id.lvMenu);
        RecyclerView.LayoutManager l
                = new LinearLayoutManager(getApplicationContext());
        lvMenu.setLayoutManager(l);

        RecyclerView.Adapter a =
                new RecyclerListAdapter(createTeishokuList());
        lvMenu.setAdapter(a);
    }

    // 教科書第８章のアクティビティからコピーしてきた。
    private List<Map<String, Object>> createTeishokuList(){
        List<Map<String, Object>> menuList
                = new ArrayList<>();
        Map<String, Object> menu = new HashMap<>();
        menu.put("name", "Karaage Teishoku");
        menu.put("price", new Integer(800));
        menu.put("desc", "Fried chicken, salad, rice and miso soup.");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "Hamburg Teishoku");
        menu.put("price", 850);
        menu.put("desc", "Handmade Hamburg, Salad, Rice and Miso Soup.");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "Curry Rice");
        menu.put("price", 810);
        menu.put("desc", "Hot-hot-hot Curry Rice.");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "Egg Drop Soup");
        menu.put("price", 330);
        menu.put("desc", "Mild-hot soup.");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "Fisherman's Pie");
        menu.put("price", 950);
        menu.put("desc", "Cod, salmon and prawns, in a creamy dill and parsley sauce.");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "Beer");
        menu.put("price", 450);
        menu.put("desc", "Locally brewed beer in Matsuyama.");
        menuList.add(menu);

        return menuList;
    }//createTeishokuList

    private class RecyclerListViewHolder
        extends RecyclerView.ViewHolder{

        public TextView _tvMenuName;
        public TextView _tvMenuPrice;

        // リストビューの一行分の画面部品を構築するためのコンストラクタ
        // row.xmlのListViewのインスタンスが渡されてくる
        public RecyclerListViewHolder(@NonNull View itemView) {
            super(itemView);
            _tvMenuName = itemView.findViewById(R.id.tvMenuName);
            _tvMenuPrice = itemView.findViewById(R.id.tvMenuPrice);
        }


    }//RecyclerListViewHolder

    private class RecyclerListAdapter
            extends RecyclerView.Adapter<RecyclerListViewHolder> {

        private List<Map<String,Object>> _listData;

        public RecyclerListAdapter(List<Map<String,Object>> listData){
            this._listData=listData;
        }
        @NonNull
        @Override
        public RecyclerListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater =
                    LayoutInflater.from(getApplicationContext());
            View view = layoutInflater.inflate
                    (R.layout.row, viewGroup, false);
            RecyclerListViewHolder vh =
                    new RecyclerListViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerListViewHolder recyclerListViewHolder, int i) {
            Map<String,Object> item = _listData.get(i);
            String menuName = (String)item.get("name");
            int menuPrice = (Integer)item.get("price");
            String priceString = String.valueOf(menuPrice);
            recyclerListViewHolder._tvMenuPrice.setText(priceString);
            recyclerListViewHolder._tvMenuName.setText(menuName);
        }

        @Override
        public int getItemCount() {
            return this._listData.size();
        }
    }

}
