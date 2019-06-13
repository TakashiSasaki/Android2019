package com.example.fragmentsample;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public MenuListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._parentActivity = this.getActivity();
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
        return view;
    }
}
