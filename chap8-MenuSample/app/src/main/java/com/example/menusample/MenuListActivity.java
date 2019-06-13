package com.example.menusample;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.content.Intent;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuListActivity extends AppCompatActivity {

    private ListView _lvMenu;
    private List<Map<String,Object>> _menuList;
    private static final String[] FROM = {"name", "price"};
    private static final int[] TO =
            {R.id.tvMenuName, R.id.tvMenuPrice};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        this._lvMenu = findViewById(R.id.lvMenu);
        this._menuList = this.createTeishokuList();

        SimpleAdapter adapter = new SimpleAdapter(
                this.getApplicationContext(),
                this._menuList,
                R.layout.row,
                FROM, TO
        );

        this._lvMenu.setAdapter(adapter);
        this._lvMenu.setOnItemClickListener(
                new ListItemClickListener()
        );

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.registerForContextMenu(this._lvMenu);
    }//onClick

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options_menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }//onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId){
            case R.id.menuListOptionTeishoku:
                this._menuList = this.createTeishokuList();
                break;
            case R.id.menuListOptionCurry:
                this._menuList = this.createCurryList();
                break;
            case android.R.id.home:
                this.finish();
                break;
        }//switch

        SimpleAdapter adapter = new SimpleAdapter(
                getApplicationContext(),
                this._menuList,
                R.layout.row,
                FROM, TO
        );
        this._lvMenu.setAdapter(adapter);

        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.menu_context_menu_list, menu);
        menu.setHeaderTitle(R.string.menu_list_context_header);
    }//onCreateContextMenu

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int listPosition = info.position;

        //ArrayListから情報を取得するやり方
        Map<String, Object> menu = this._menuList.get(listPosition);

        //ListViewから情報を取得するやり方
        //Map<String, Object> menu =
        //        (Map<String,Object>)this._lvMenu.getItemAtPosition(listPosition);

        int itemId = item.getItemId();
        switch(itemId){
            case R.id.menuListContextDesc:
                String desc = (String) menu.get("desc");
                Toast.makeText(getApplicationContext(), desc, Toast.LENGTH_LONG).show();
                break;
            case R.id.menuListContextOrder:
                order(menu);
                break;
        }
        return super.onContextItemSelected(item);
    }

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

        return menuList;
    }

    private List<Map<String, Object>> createCurryList(){
        List<Map<String, Object>> menuList
                = new ArrayList<>();
        Map<String, Object> menu = new HashMap<>();
        menu.put("name", "Beef Curry");
        menu.put("price", new Integer(520));
        menu.put("desc", "100% beef grown in Japan.");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "Pork Curry");
        menu.put("price", 420);
        menu.put("desc", "100% pork grown in Japan.");
        menuList.add(menu);

        return menuList;
    }//createCurryList

    private class ListItemClickListener
        implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick
                (AdapterView<?> parent,
                 View view, int position, long id) {
            Map<String, Object> item =
                    (Map<String,Object>)parent.getItemAtPosition(position);
            order(item);
        }//onItemClick
    }//ListItemClickListener

    private void order(Map<String,Object> menu){
        String menuName = (String)menu.get("name");
        Integer menuPrice = (Integer) menu.get("price");

        Intent intent = new Intent(
                getApplicationContext(),
                MenuThanksActivity.class);
        intent.putExtra("menuName", menuName);
        intent.putExtra("menuPrice", menuPrice + " Yen");
        startActivity(intent);
    }
}
