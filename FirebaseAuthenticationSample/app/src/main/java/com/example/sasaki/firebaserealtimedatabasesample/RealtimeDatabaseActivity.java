package com.example.sasaki.firebaserealtimedatabasesample;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RealtimeDatabaseActivity extends AppCompatActivity {

    EditText editTextKey;
    EditText editTextValue;
    EditText editTextEmail;
    EditText editTextProviderId;
    EditText editTextPassword;
    EditText editTextLog;
    EditText editTextUid;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_database);

        this.editTextKey = findViewById(R.id.editTextKey);
        this.editTextValue = findViewById(R.id.editTextValue);
        this.editTextEmail = findViewById(R.id.editTextEmail);
        this.editTextProviderId = findViewById(R.id.editTextProviderId);
        this.editTextPassword = findViewById(R.id.editTextPassword);
        this.editTextLog = findViewById(R.id.editTextLog);
        this.editTextUid = findViewById(R.id.editTextUid);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference messageReference = database.getReference("message");
        messageReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                String value = (String) dataSnapshot.getValue();
                editTextKey.setText(key);
                editTextValue.setText(value);
            }//onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }//onCancelled
        });

        this.firebaseAuth = FirebaseAuth.getInstance();

    }//onCreate

    @Override
    protected void onStart() {
        super.onStart();
        this.updateUser();
    }//onStart

    private void updateUser() {
        FirebaseUser fireBaseUser = this.firebaseAuth.getCurrentUser();
        if (fireBaseUser == null) {
            this.editTextLog.setText("onStart : user is not logged in.");
        } else {
            this.editTextEmail.setText(fireBaseUser.getEmail());
            this.editTextPassword.setText("");
            this.editTextUid.setText(fireBaseUser.getUid());
            this.editTextProviderId.setText(fireBaseUser.getProviderId());
            this.editTextUid.setText(fireBaseUser.getUid());
        }//if
    }//updateUser

    public void onButtonClick(View view) {
        String key = this.editTextKey.getText().toString();
        String value = this.editTextValue.getText().toString();
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference(key);
        databaseReference.setValue(value);
    }//onButtonClick

    public void onButtonCreateUserClick(View view) {
        //TODO: check if email and password is not null
        final String email = this.editTextEmail.getText().toString();
        final String password = this.editTextPassword.getText().toString();
        this.firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    editTextLog.setText("New user has created successfully.");
                                    updateUser();
                                } else {
                                    editTextLog.setText("Failed to create new user.");
                                }//if
                            }//onComplete
                        }//OnCompleteListener
                );//addOnCompleteListener
    }//onCreateUserClick

    public void onButtonLoginClick(View view) {
        //TODO: check if email and password is not null
        final String email = this.editTextEmail.getText().toString();
        final String password = this.editTextPassword.getText().toString();
        this.firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    editTextLog.setText("Logged in as an existing user.");
                                    updateUser();
                                } else {
                                    editTextLog.setText("Failed to login as an existing user.");
                                }//if
                            }//onComplete
                        }//OnCompleteListener
                );//addOnCompleteListener
    }//onButtonLoginClick

    public void onButtonLogoutClick(View view){
        this.firebaseAuth.signOut();
        this.editTextLog.setText("Signed out successfully");
    }//onButtonLogoutClick

    public void onButtonDeleteUserClick(View view){
        this.firebaseAuth.getCurrentUser().delete()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    editTextLog.setText("Deleted an existing user.");
                                } else {
                                    editTextLog.setText("Failed to delete an existing user.");
                                }//if
                            }//onComplete
                        }//OnCompleteListener
                );//addOnCompleteListener
    }//onButtonDeleteUserClick
}//RealtimeDatabaseActivity
