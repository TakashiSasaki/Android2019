package com.gmail.takashi316.hello_kbc_sasaki;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HelloKbcSasaki extends AppCompatActivity {

    EditText m1email;
    EditText m1text;
    FirebaseApp firebaseApp;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference m1ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_kbc_sasaki);
        this.m1email = findViewById(R.id.m1email);
        this.m1text = findViewById(R.id.m1text);
        this.firebaseApp = FirebaseApp.initializeApp(this.getApplicationContext());
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.m1ref = this.firebaseDatabase.getReference("/messages/m1");
        this.m1ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String emailString = (String)dataSnapshot.child("email").getValue();
                m1email.setText(emailString);
                String textString = (String)dataSnapshot.child("text").getValue();
                m1text.setText(textString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
