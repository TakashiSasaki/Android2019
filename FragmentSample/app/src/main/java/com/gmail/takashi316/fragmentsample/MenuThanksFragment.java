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
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuThanksFragment extends Fragment {

    private Activity _parentActivity;
    private boolean _isLayoutXlarge = false;

    public MenuThanksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._parentActivity = getActivity();
        //this.setHasOptionsMenu(true);

        View fragmentMenuList = _parentActivity.findViewById
                (R.id.fragmentMenuList);
        if(fragmentMenuList==null){
            this._isLayoutXlarge = false;
        } else {
            this._isLayoutXlarge = true;
        }//if

    }//onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_menu_thanks,
                container, false);

        Bundle extras;
        if(this._isLayoutXlarge){
            extras = this.getArguments();
        } else {
            Intent intent = this._parentActivity.getIntent();
            extras = intent.getExtras();
        }//if

        String menuName = "不明";
        String menuPrice = "不明";

        if(extras != null) {
            menuName = extras.getString("menuName");
            menuPrice = extras.getString("menuPrice");
        }//if

        TextView tvMenuName = view.findViewById(R.id.tvMenuName);
        TextView tvMenuPrice = view.findViewById(R.id.tvMenuPrice);

        tvMenuName.setText(menuName);
        tvMenuPrice.setText(menuPrice);

        Button btBackButton = view.findViewById(R.id.btBackButton);
        btBackButton.setOnClickListener(new ButtonClickListener());

        return view;
    }//onCreateView

    private class ButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(_isLayoutXlarge){
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(MenuThanksFragment.this);
                transaction.commit();
            } else {
                _parentActivity.finish();
            }//if
        }//onClick
    }//ButtonClickListener

}//MenuThanksFragment
