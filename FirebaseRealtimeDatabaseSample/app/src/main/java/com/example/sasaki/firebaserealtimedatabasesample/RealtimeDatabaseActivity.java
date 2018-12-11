package com.example.sasaki.firebaserealtimedatabasesample;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RealtimeDatabaseActivity extends AppCompatActivity {

    EditText editTextKey;
    EditText editTextValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_database);

        this.editTextKey = findViewById(R.id.editTextKey);
        this.editTextValue =findViewById(R.id.editTextValue);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference messageReference = database.getReference("message");
        messageReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                String value = (String)dataSnapshot.getValue();
                editTextKey.setText(key);
                editTextValue.setText(value);
            }//onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }//onCancelled
        });

    }//onCreate

    public void onButtonClick(View view){
        String key = this.editTextKey.getText().toString();
        String value = this.editTextValue.getText().toString();
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference(key);
        databaseReference.setValue(value);
    }//onButtonClick
}//RealtimeDatabaseActivity
