package com.example.fragmentsample;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuThanksFragment extends Fragment {


    public MenuThanksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_thanks, container, false);
        Intent intent = getActivity().getIntent();
        Bundle extras = ((Intent) intent).getExtras();

        String menuName = "";
        String menuPrice = "";
        if(extras != null){
            menuName = extras.getString("menuName");
            menuPrice = extras.getString("menuPrice");
        }

        ((TextView)view.findViewById(R.id.tvMenuName)).setText(menuName);
        ((TextView)view.findViewById(R.id.tvMenuPrice)).setText(menuPrice);

        return view;
    }

}
