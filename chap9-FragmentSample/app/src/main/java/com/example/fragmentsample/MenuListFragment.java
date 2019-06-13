package com.example.fragmentsample;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
    private boolean _isLayoutXLarge = true;

    public MenuListFragment() {
        // Required empty public constructor
    }

    //@Override
    //public void setArguments(@Nullable Bundle args) {
    //    super.setArguments(args);
    //}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._parentActivity = this.getActivity();
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);

        ListView lvMenu =  view.findViewById(R.id.lvMenu);
        List<Map<String,String>> menuList = new ArrayList<>();
        Map<String,String> map = new HashMap();
        map.put("name", "Karaage teishoku");
        map.put("price", "800 yen");
        menuList.add(map);
        map = new HashMap<>();
        map.put("name", "Hamburg teishoku");
        map.put("price", "850 yen");
        menuList.add(map);

        //事前にfrom, to作っておく必要無し
        SimpleAdapter adapter = new SimpleAdapter(
                this.getContext(),
                menuList,
                android.R.layout.simple_list_item_2,
                new String[]{"name", "price"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        lvMenu.setAdapter(adapter);
        lvMenu.setOnItemClickListener(new ListItemClickListener());
        return view;
    }//onCreateView

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View menuThanksFrame =
                getActivity().findViewById(R.id.menuThanksFrame);
        if(menuThanksFrame == null){
            this._isLayoutXLarge = false;
        } else {
            this._isLayoutXLarge = true;
        }
    }//onActivityCreated

    private class ListItemClickListener
        implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map item = (Map) parent.getItemAtPosition(position);
            String menuName = (String) item.get("name");
            String menuPrice = (String) item.get("price");

            Bundle bundle = new Bundle();
            bundle.putString("menuName", menuName);
            bundle.putString("menuPrice", menuPrice);

            if(_isLayoutXLarge){ // tablet
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction =
                        manager.beginTransaction();
                MenuThanksFragment menuThanksFragment =
                        new MenuThanksFragment();
                menuThanksFragment.setArguments(bundle);
                transaction.replace(R.id.menuThanksFrame,
                        menuThanksFragment);
                transaction.commit();
            } else { // smart phone
                Intent intent = new Intent(
                        getContext(),
                        MenuThanksActivity.class
                );
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }//onItemClick
    }//ListItemClickListener
}//MenuListFragment
