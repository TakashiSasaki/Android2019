package com.gmail.takashi316.hello_kbc_sasaki;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ScrollingActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


                if (firebaseUser == null) {
                    Snackbar.make(view, "You've not been logged in.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "You've been logged in as " + firebaseUser.getEmail(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }//if

                Task<AuthResult> task = firebaseAuth.signInWithEmailAndPassword
                        ("test1@example.com", "test1pass");
                task.addOnCompleteListener(ScrollingActivity2.this,
                        new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    AuthResult authResult = task.getResult();
                                    Log.d("HelloKbcSasaki", task.getResult().getUser().getEmail());
                                } else {
                                    Log.d("HelloKbcSasaki", "Failed to sign in.");
                                }
                            }//onComplete
                        });


            }
        });
    }
}
