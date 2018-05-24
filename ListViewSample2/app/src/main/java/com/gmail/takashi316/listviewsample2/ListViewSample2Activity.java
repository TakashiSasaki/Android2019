package com.gmail.takashi316.listviewsample2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListViewSample2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_sample2);

        ListView lvMenu = (ListView) findViewById(R.id.lvMenu);

        List<String> menuList = new ArrayList<>();
        menuList.add("karaage");
        menuList.add("hanba-gu");
        menuList.add("shouga");
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, menuList);

        lvMenu.setAdapter(adapter);

        lvMenu.setOnItemClickListener(new ListItemClickListener());

    }//onCreate



    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        OrderConfirmDialogFragment dialogFragment = new OrderConfirmDialogFragment();
        dialogFragment.show(getFragmentManager(), "OrderConfirmDialogFragment");
        }//onItemClick
    }//ListItemClickListener


}
