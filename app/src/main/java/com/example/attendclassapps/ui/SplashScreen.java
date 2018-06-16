package com.example.attendclassapps.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.attendclassapps.db.dao.UserDao;
import com.example.attendclassapps.db.dao.AttendClassDatabaseHelper;
import com.example.attendclassapps.R;
import com.example.attendclassapps.db.session.SessionCheck;
import com.example.attendclassapps.db.session.UserSession;
import com.example.attendclassapps.ui.main.MainActivity;

public class SplashScreen extends AppCompatActivity {

    public int SPLASH_TIME = 1000;
    protected AppCompatEditText login_ID;
    protected LinearLayout BtnLogin;
    private AttendClassDatabaseHelper attendClassDatabaseHelper;
    protected SessionCheck sessionCheck;
    protected UserSession setUser_ID;
    private Context context = this;
    private static final String TAG = "Log_Attend_Class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionCheck = new SessionCheck(SplashScreen.this);
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
                if (sessionCheck.logged_in()) {
                    Intent mainIntent = new Intent(getApplicationContext() , MainActivity.class);
                    startActivity(mainIntent);
                    ActivityCompat.finishAffinity(SplashScreen.this);
                }
                else {
                    LoginDialog();
                }

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

            }
        });

        loginDialog.show();
        Log.i(TAG, "Splash_Screen_LoginDialog");
    }

    private void CheckLogin() {
        String user_id = login_ID.getText().toString().trim();
        UserDao userDao = new UserDao(user_id);
        UserDao validateUserDao = attendClassDatabaseHelper.checkUserLogin(userDao);

        AttendClassDatabaseHelper attendClassDatabaseHelper = new AttendClassDatabaseHelper(this);
        UserSession userSession = new UserSession(this);

        if (login_ID.length() == 0) {
            String message = getString(R.string.missing_message);
            Toast.makeText(context , message , Toast.LENGTH_SHORT).show();
        }
        else if (validateUserDao == null) {
            String message = getString(R.string.error_message);
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
        else {
            setUser_ID = new UserSession(SplashScreen.this);
            setUser_ID.setUser_ID_Session(validateUserDao.getUser_ID());
            sessionCheck.setLogged_in(true);

            SQLiteDatabase sqLiteDatabase = attendClassDatabaseHelper.getWritableDatabase();
            String sqlQuery = "SELECT * FROM UserLogin WHERE ID = ";
            Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery + validateUserDao.getUser_ID() , null);
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