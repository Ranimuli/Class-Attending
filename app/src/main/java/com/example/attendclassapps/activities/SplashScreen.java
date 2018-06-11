package com.example.attendclassapps.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.attendclassapps.AttendClassDatabaseHelper;
import com.example.attendclassapps.R;
import com.example.attendclassapps.Session;
import com.example.attendclassapps.User;
import com.example.attendclassapps.UserSession;

public class SplashScreen extends Activity {

    public int SPLASH_TIME = 3000;
    protected AppCompatEditText login_ID;
    protected LinearLayout BtnLogin;
    private AttendClassDatabaseHelper attendClassDatabaseHelper;
    protected Session session;
    protected UserSession setUser_ID;
    private Context context = this;
    private static final String TAG = "Log_Attend_Class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        session = new Session(SplashScreen.this);
        attendClassDatabaseHelper = new AttendClassDatabaseHelper(SplashScreen.this);
        attendClassDatabaseHelper.createDataBase();
        try {
            attendClassDatabaseHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (session.logged_in()) {
                    Intent mainIntent = new Intent(getApplicationContext() , MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                else {
                    LoginDialog();
                }
                /*Intent mainIntent = new Intent(getApplicationContext() , MainActivity.class);
                startActivity(mainIntent);*/
            }
        }, SPLASH_TIME);

        Log.i(TAG, "Splash_Screen_onCreate");
    }

    public void LoginDialog(){
        final Dialog loginDialog = new Dialog(SplashScreen.this);
        loginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loginDialog.setContentView(R.layout.login_dialog);

        attendClassDatabaseHelper = new AttendClassDatabaseHelper(this);

        login_ID = loginDialog.findViewById(R.id.loginID);
        BtnLogin = loginDialog.findViewById(R.id.vBtnLogin);

        loginDialog.setCanceledOnTouchOutside(false);
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLogin();

                /*String UserTokenEmail = login_ID.getText().toString().trim() + "@attendclasscookies.com";
                String UserTokenPassword = login_ID.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(UserTokenEmail , UserTokenPassword);
                Log.i(TAG, UserTokenEmail);
                Log.i(TAG, "Sign in to Firebase Authentication");*/

                /*Task<AuthResult> task = mAuth.signInAnonymously();
                task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful())
                        Log.w(TAG, task.getException());
                    }
                });*/
            }
        });

        loginDialog.show();
        Log.i(TAG, "Splash_Screen_LoginDialog");
    }

    private void CheckLogin() {
        String user_id = login_ID.getText().toString().trim();
        User user = new User(user_id);
        User validateUser = attendClassDatabaseHelper.checkUserLogin(user);

        AttendClassDatabaseHelper attendClassDatabaseHelper = new AttendClassDatabaseHelper(this);
        UserSession userSession = new UserSession(this);

        if (login_ID.length() == 0) {
            String message = getString(R.string.missing_message);
            Toast.makeText(context , message , Toast.LENGTH_SHORT).show();
        }
        else if (validateUser == null) {
            String message = getString(R.string.error_message);
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
        else {
            setUser_ID = new UserSession(SplashScreen.this);
            setUser_ID.setUser_ID_Session(validateUser.getUser_ID());
            session.setLogged_in(true);

            SQLiteDatabase sqLiteDatabase = attendClassDatabaseHelper.getWritableDatabase();
            String sqlQuery = "SELECT * FROM UserLogin WHERE ID = ";
            Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery + validateUser.getUser_ID() , null);
            if (cursor != null) {
                cursor.moveToFirst();
                userSession.setUser_Name_Session(cursor.getString(1));
                userSession.setUser_State_Session(cursor.getString(2));
                cursor.close();
            }

            Intent intent = new Intent(getApplicationContext() , MainActivity.class);
            startActivity(intent);
            Log.i(TAG, "Splash_Screen_CheckLogin");
            finish();
        }
    }
}