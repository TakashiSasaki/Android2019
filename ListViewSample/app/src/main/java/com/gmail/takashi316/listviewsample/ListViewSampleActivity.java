package com.gmail.takashi316.listviewsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewSampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_list_view_sample);

        ListView lvMenu = findViewById(R.id.lvMenu);
        lvMenu.setOnItemClickListener(new ListItemClickListener());
        
        //((ListView)findViewById(R.id.lvMenu)).setOnItemClickListener(new ListItemClickListener());
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final String item = (String) parent.getItemAtPosition(position);
            final String show = "あなたが選んだていしょく：" + item;
            Toast.makeText(ListViewSampleActivity.this, show,Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), show,Toast.LENGTH_LONG).show();
        }//onItemClick
    }
}
