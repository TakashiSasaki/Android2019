package com.example.fragmentsample;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuThanksFragment extends Fragment {

    private boolean _isLayoutXLarge = true;

    public MenuThanksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager manager = getFragmentManager();
        Fragment menuListFragment =
                manager.findFragmentById(R.id.fragmentMenuList);
        if(menuListFragment == null){
            this._isLayoutXLarge = false;
        } else {
            this._isLayoutXLarge = true;
        }
    }//onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle extras;
        if(_isLayoutXLarge) {
             extras = getArguments();
        } else {
            Intent intent = getActivity().getIntent();
            extras = ((Intent) intent).getExtras();
        }

        String menuName = "";
        String menuPrice = "";
        if (extras != null) {
            menuName = extras.getString("menuName");
            menuPrice = extras.getString("menuPrice");
        }

        View view = inflater.inflate(R.layout.fragment_menu_thanks, container, false);
        ((TextView) view.findViewById(R.id.tvMenuName)).setText(menuName);
        ((TextView) view.findViewById(R.id.tvMenuPrice)).setText(menuPrice);
        ((Button)(view.findViewById(R.id.btBackButton)))
                .setOnClickListener(new ButtonClickListener());
        return view;
    }

    private class ButtonClickListener
            implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(_isLayoutXLarge){
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(MenuThanksFragment.this);
                transaction.commit();
            } else {
                getActivity().finish();
            }
        }//onClick
    }//ButtonCLickListener
}
