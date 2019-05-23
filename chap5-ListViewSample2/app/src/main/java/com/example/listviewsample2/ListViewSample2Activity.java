package com.example.listviewsample2;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListViewSample2Activity extends AppCompatActivity {

    ListView lvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMenu = findViewById(R.id.lvMenu);
        List<String> menuList = new ArrayList<String>();
        menuList.add("karaage-teishoku");
        menuList.add("hamburg-teishoku");
        menuList.add("shougayaki-teishoku");
        menuList.add("steak-teishoku");
        menuList.add("yasai-itame-teishoku");
        menuList.add("tonkatsu-teishoku");
        menuList.add("minchi-katsu-teishoku");
        menuList.add("chicken-katsu-teishoku");
        menuList.add("korokke-teishoku");
        menuList.add("yakiniku-teishoku");

        ArrayAdapter<String> adapter =
                new ArrayAdapter(
                        ListViewSample2Activity.this,
                        android.R.layout.simple_list_item_1,
                        menuList);
        lvMenu.setAdapter(adapter);

        lvMenu.setOnItemClickListener(new ListItemClickListener());

    }//onCreate

    private class ListItemClickListener
            implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            DialogFragment dialogFragment
                    = new OrderConfirmDialogFragment();

            ((OrderConfirmDialogFragment) dialogFragment).menuItem
                    =   " (" + (String) parent.getItemAtPosition(position) + ")";

            dialogFragment.show(
                    getSupportFragmentManager(),
                    "OrderConfirmDialogFragment"
            );
        }//onItemClick
    }//ListItemClickListener
}//ListViewSample2Activity

