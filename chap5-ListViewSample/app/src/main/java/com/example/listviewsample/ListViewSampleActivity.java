package com.example.listviewsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_list_view_sample);

        ListView lvMenu = this.findViewById(R.id.lvMenu);
        lvMenu.setOnItemClickListener(new ListItemClickListener());

        //lvMenuという変数を作らずにsetOnItemClickListenerする書き方も可。
        //((ListView)findViewById(R.id.lvMenu)).setOnItemClickListener(new ListItemClickListener());
    }//onCreate

    private class ListItemClickListener
        implements AdapterView.OnItemClickListener {

        private Toast previousToast;

        @Override
        public void onItemClick(AdapterView<?> parent,
                                View view, int position, long id) {
            String item = (String) parent.getItemAtPosition(position);
            String show = "You've chosen " + item + ".";
            Toast toast = Toast.makeText(
                    ListViewSampleActivity.this,
                    show, Toast.LENGTH_LONG);
            if(previousToast != null) previousToast.cancel();
            previousToast = toast;
            toast.show();
        }//onItemClick
    }//ListItemClickListener
}
