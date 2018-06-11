package com.example.attendclassapps.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.attendclassapps.UserSession;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserSignInToFirebaseActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener muAuthListener;
    UserSession userSession;
    private static final String TAG = "Log_Attend_Class";
    String UserTokenEmail;
    String UserTokenPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userSession = new UserSession(this);
        mAuth = FirebaseAuth.getInstance();
        muAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged_User: "+user.getUid()+" has sign in");
                }
            }
        };

        UserTokenEmail = userSession.User_ID() + "@attendclasscookies.com";
        UserTokenPassword = userSession.User_ID();

        mAuth.createUserWithEmailAndPassword(UserTokenEmail , UserTokenPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mAuth.signInWithEmailAndPassword(UserTokenEmail , UserTokenPassword);
                        }
                    }
                });
        Log.i(TAG, "Sign in to Firebase Authentication");
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(muAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(muAuthListener);
    }
}
