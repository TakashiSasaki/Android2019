package com.gmail.takashi316.fragmentsample;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuListFragment extends Fragment {


    private Activity _parentActivity;
    private boolean _isLayoutXlarge = false;
    //private Context context;

    public MenuListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._parentActivity = getActivity();
        //this.context = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View inflatedView = inflater.inflate
                (R.layout.fragment_menu_list,
                container, false);

        ListView lvMenu = (ListView)inflatedView.findViewById(R.id.lvMenu);
        List<Map<String, String>> menuList = new ArrayList<>();

        // menuList に要素を足していく
        Map<String, String> menu = new HashMap<>();
        menu.put("name", "からあげていしょく");
        menu.put("price", "800円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "ハンバーグ定食");
        menu.put("price", "850円");
        menuList.add(menu);

        final String[] from = {"name", "price"};
        final int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleAdapter adapter = new SimpleAdapter
                (this._parentActivity,
                menuList, android.R.layout.simple_list_item_2,
                from, to);

        lvMenu.setAdapter(adapter);
        lvMenu.setOnItemClickListener(new ListItemClickListener());

        return inflatedView;
    }//onCreateView

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View menuThanksFrame = _parentActivity.findViewById
                (R.id.menuThanksFrame);

        if(menuThanksFrame==null){
            this._isLayoutXlarge = false;
        } else {
            this._isLayoutXlarge = true;
        }//if

    }//onActivityCreated

    private class ListItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String,String> item = (Map<String,String>) parent.getItemAtPosition(position);
            String menuName = item.get("name");
            String menuPrice = item.get("price");

            Bundle bundle = new Bundle();
            bundle.putString("menuName", menuName);
            bundle.putString("menuPrice", menuPrice);

            if(_isLayoutXlarge) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                MenuThanksFragment menuThanksFragment = new MenuThanksFragment();
                menuThanksFragment.setArguments(bundle);
                transaction.replace(R.id.menuThanksFrame, menuThanksFragment);

                transaction.commit();
            } else {
                Intent intent = new Intent(_parentActivity, MenuThanksActivity.class);
                intent.putExtras(bundle);
                //intent.putExtra("menuName", menuName);
                //intent.putExtra("menuPrice", menuPrice);
                startActivity(intent);
            }//if

        }//onItemClick
    }//ListItemClickListener

}//MenuListFragment
