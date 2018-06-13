package com.example.attendclassapps.ui.reader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.attendclassapps.model.CheckedModel;
import com.example.attendclassapps.db.session.UserSession;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentSendDataToFirebase extends AppCompatActivity {

        private static final String TAG = "AddToDatabase";
        String userID;

        FirebaseDatabase mFirebaseDatabase;
        FirebaseAuth mAuth;
        FirebaseAuth.AuthStateListener mAuthListener;
        DatabaseReference myRef;
        String ID, Name;
        UserSession userSession;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            userSession = new UserSession(this);
            mAuth = FirebaseAuth.getInstance();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            myRef = mFirebaseDatabase.getReference();
            FirebaseUser user = mAuth.getCurrentUser();
            userID = user.getUid();

            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        // User is signed in
                        Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    } else {
                        // User is signed out
                        Log.d(TAG, "onAuthStateChanged:signed_out");
                    }
                }
            };

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d(TAG, "onDataChange: Added information to database: \n" + dataSnapshot.getValue());
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

            ID = userSession.User_ID();
            Name = userSession.User_Name();
            String getID = ID;
            String getName = Name;

            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm");
            String getCheckTime = dateFormat.format(date);

            if (!ID.equals("") && !Name.equals("") && !getCheckTime.equals("")) {
                CheckedModel checkedModel = new CheckedModel(getID , getName , getCheckTime);
                myRef.child("Student_join").child("Class_room").child(userID).setValue(checkedModel);
            }
            Log.d(TAG, "Added information to database: " + getCheckTime);
            finish();
        }
    }
