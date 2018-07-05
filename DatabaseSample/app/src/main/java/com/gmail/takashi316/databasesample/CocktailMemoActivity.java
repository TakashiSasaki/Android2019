package com.gmail.takashi316.databasesample;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
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
        String note = etNote.getText().toString();

        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        try {

            String sqlDelete = "DELETE FROM cocktailmemo WHERE _id = ?";
            SQLiteStatement stmt = db.compileStatement(sqlDelete);
            stmt.bindLong(1, _cocktailId);
            /*int nRows =*/
            stmt.executeUpdateDelete();

            String sqlInsert = "INSERT INTO cocktailmemo (_id, name, note) VALUES (?, ?, ?)";
            stmt = db.compileStatement(sqlInsert);
            stmt.bindLong(1, _cocktailId);
            stmt.bindString(2, _cocktailName);
            stmt.bindString(3, note);
            long primaryKey = stmt.executeInsert();
            Log.d("CocktailMemoActivity", "primary key = " + primaryKey);
        } finally {
            db.close(); //SQLiteDatabaseクラスのファイナライザを呼び出している
        }//try

        this._tvCocktailName.setText(this.getString(R.string.tv_name));
        etNote.setText("");
        this._btnSave.setEnabled(false);
    }//onSaveButtonClick

    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            _cocktailId = position;
            _cocktailName = (String) adapterView.getItemAtPosition(_cocktailId);
            _tvCocktailName.setText(_cocktailName);
            _btnSave.setEnabled(true);

            DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
            SQLiteDatabase db = helper.getReadableDatabase();

            try {
                String sql = "SELECT * FROM cocktailmemo WHERE _id = ?";
                //String sql = "SELECT * FROM cocktailmemo WHERE _id = " + _cocktailId;

                //String[] params = {""+_cocktailId};
                String[] param = {String.valueOf(_cocktailId)};
                Cursor cursor = db.rawQuery(sql, param);

                String note = "";

                while (cursor.moveToNext()) {
                    int idxNote = cursor.getColumnIndex("note");
                    Log.d("CocktailMemoActivity", "column index of 'note' = " + idxNote);
                    note = cursor.getString(idxNote);
                }//while

                EditText etNote = findViewById(R.id.etNote);
                etNote.setText(note);
            } finally {
                db.close();
            }//try
        }//onItemClick
    }//ListItemClickListener
}//CocktailMemoActivity
