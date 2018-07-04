package com.gmail.takashi316.databasesample;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class CocktailMemoActivity extends Activity {

    int _cocktailId = -1;
    String _cocktailName = "";
    TextView _tvCocktailName;
    Button _btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_memo);

        this._tvCocktailName = this.findViewById(R.id.tvCocktailName);
        this._btnSave = this.findViewById(R.id.btnSave);
        ListView lvCocktail = this.findViewById(R.id.lvCocktail);
        lvCocktail.setOnItemClickListener(new ListItemClickListener());
    }

    public void onSaveButtonClick(View view){
        EditText etNote = this.findViewById(R.id.etNote);
        this._tvCocktailName.setText(this.getString(R.string.tv_name));
        etNote.setText("");

        new DatabaseHelper(getApplicationContext());

        this._btnSave.setEnabled(false);
    }//onSaveButtonClick

    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            _cocktailId = position;
            _cocktailName = (String) adapterView.getItemAtPosition(_cocktailId);
            _tvCocktailName.setText(_cocktailName);
            _btnSave.setEnabled(true);
        }
    }
}
