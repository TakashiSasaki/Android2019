package com.example.chap10_databasesample;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class CocktailMemoActivity extends AppCompatActivity {

    int _cocktailId = -1;
    String _cocktailName = "";
    TextView _tvCocktailName;
    Button _btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_memo);
        _tvCocktailName = findViewById(R.id.tvCocktailName);
        _btnSave = findViewById(R.id.btnSave);
        ListView lvCocktail = findViewById(R.id.lvCocktail);
        lvCocktail.setOnItemClickListener
                (new ListItemClickListener());
    }

    public void onSaveButtonClick(View view) {
        EditText etNote = findViewById(R.id.etNote);
        String note = etNote.getText().toString();

        DatabaseHelper helper =
                new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            String sqlDelete =
                    "DELETE FROM cocktailmemo "
                            + "WHERE _id = ?";
            SQLiteStatement stat =
                    db.compileStatement(sqlDelete);
            stat.bindLong(1, _cocktailId);
            stat.execute(); // returns void

            String sqlInsert =
                    "INSERT INTO cocktailmemo "
                            + "(_id, name, note) "
                            + "VALUES (?,?,?);";
            stat = db.compileStatement(sqlInsert);
            stat.bindLong(1, _cocktailId);
            stat.bindString(2, _cocktailName);
            stat.bindString(3, note);
            stat.executeInsert(); // returns long

        } finally {
            db.close();
        }

        _tvCocktailName.setText(getString(R.string.tv_name));
        etNote.setText("");
        _btnSave.setEnabled(false);
    }

    private class ListItemClickListener
            implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent,
                                View view, int position,
                                long id) {
            _cocktailId = position;
            _cocktailName = (String) parent.getItemAtPosition(position);
            _tvCocktailName.setText(_cocktailName);
            _btnSave.setEnabled(true);

            DatabaseHelper helper =
                    new DatabaseHelper(getApplicationContext());
            SQLiteDatabase db =  helper.getReadableDatabase();

            try{
                String sql = "SELECT * FROM cocktailmemo"
                        + " WHERE _id = " + _cocktailId;
                Cursor cursor = db.rawQuery(sql, null);
                String note = "";
                while(cursor.moveToNext()){
                    int idxNote = cursor.getColumnIndex("note");
                    note = cursor.getString(idxNote);
                }//while
                EditText etNote = findViewById(R.id.etNote);
                etNote.setText(note);
            } finally {
                db.close();
            }
        }//onItemClick
    }//ListItemClickListener

}
