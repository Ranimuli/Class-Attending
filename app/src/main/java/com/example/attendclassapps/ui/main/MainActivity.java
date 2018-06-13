package com.example.attendclassapps.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.attendclassapps.R;
import com.example.attendclassapps.db.session.SessionCheck;
import com.example.attendclassapps.db.session.UserSession;
import com.example.attendclassapps.ui.classSchedule.ClassScheduleActivity;
import com.example.attendclassapps.ui.generator.QrGeneratorActivity;
import com.example.attendclassapps.ui.view.QrViewActivity;
import com.example.attendclassapps.ui.SplashScreen;
import com.example.attendclassapps.ui.reader.ReaderActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerlayout;
    private ActionBarDrawerToggle ABDtoggle;
    private LinearLayout vBtnQrCodeGen, vBtnQrCodeReader;
    private TextView NameView , IDView;
    private ImageView ivUserImage;
    private static final String TAG = "Log_Attend_Class";
    private SessionCheck sessionCheck;
    protected UserSession userSession;
    private static final int ACTIVITY_NOT_INITIALIZED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setNavigationViewListener();
        bindView();

        startActivity(new Intent(getApplicationContext() , UserSignInToFirebase.class));

        userSession = new UserSession(MainActivity.this);
        sessionCheck = new SessionCheck(this);

        if (!sessionCheck.logged_in()) {
            logout();
        }
        else {
            NameView.setText(userSession.User_Name());
            IDView.setText(userSession.User_ID());
        }

        if (userSession.User_State().equals("1")) {
            vBtnQrCodeGen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent genAct = new Intent(getApplicationContext(), QrGeneratorActivity.class);
                    startActivity(genAct);
                }
            });
        }
        else if (userSession.User_State().equals("2")){
            ivUserImage.setImageResource(R.drawable.student);
            vBtnQrCodeGen.setVisibility(View.GONE);
            vBtnQrCodeReader.setVisibility(View.VISIBLE);
            vBtnQrCodeReader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent genAct = new Intent(getApplicationContext(), ReaderActivity.class);
                    startActivity(genAct);
                }
            });
        }

        ABDtoggle = new ActionBarDrawerToggle(this, drawerlayout,R.string.openDrawer,R.string.closeDrawer);
        drawerlayout.addDrawerListener(ABDtoggle);
        ABDtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.i(TAG, "MainActivity_onCreate ");
    }

    private void setNavigationViewListener(){
        NavigationView navigationView = findViewById(R.id.NavMenuList);
        userSession = new UserSession(MainActivity.this);
        String userStateSelectMenu = userSession.User_State();
        switch (userStateSelectMenu) {
            case "1": {
                navigationView.getMenu().setGroupVisible(R.id.gpItem2, false);
                navigationView.setNavigationItemSelectedListener(this);
                Log.i(TAG, "MainActivity_setNavigationViewListener_case 1");
                break;
            }
            case "2": {
                navigationView.getMenu().setGroupVisible(R.id.gpItem1, false);
                navigationView.setNavigationItemSelectedListener(this);
                Log.i(TAG, "MainActivity_setNavigationViewListener_case 2");
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "MainActivity_onOptionsItemSelect_Clicked");
        return ABDtoggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        userSession = new UserSession(MainActivity.this);
        switch (userSession.User_State()) {
            case "1": {
                switch (item.getItemId()){
                    case R.id.menu_QrCodeGenerator: {
                        startActivity(new Intent(getApplicationContext() , QrGeneratorActivity.class));
                        break;
                    }
                    case R.id.menu_QrCodeView: {
                        startActivity(new Intent(getApplicationContext() , QrViewActivity.class));
                        break;
                    }
                    case R.id.menu_StudentListView: {
                        startActivity(new Intent(getApplicationContext() , StudentListViewActivity.class));
                        break;
                    }
                    case R.id.menu_FBcontact: {
                        Uri uri = Uri.parse("https://www.facebook.com/Supol.Pupuang.927543");
                        startActivity(new Intent(Intent.ACTION_VIEW , uri));
                        break;
                    }
                    case R.id.menu_aboutMe:{
                        Uri uri = Uri.parse("https://drive.google.com/open?id=1fYZ3sIswjGjCUA4T0crA9uHOHkRvVvx2");
                        startActivity(new Intent(Intent.ACTION_VIEW , uri));
                        break;
                    }
                }
                break;
            }

            case "2": {
                switch (item.getItemId()){
                    case R.id.menu_QrCodeReader: {
                        startActivity(new Intent(getApplicationContext() , ReaderActivity.class));
                        break;
                    }
                    case R.id.menu_ClassSchedule: {
                        startActivity(new Intent(getApplicationContext() , ClassScheduleActivity.class));
                        break;
                    }
                    case R.id.menu_FBcontact: {
                        Uri uri = Uri.parse("https://www.facebook.com/Supol.Pupuang.927543");
                        startActivity(new Intent(Intent.ACTION_VIEW , uri));
                        break;
                    }
                    case R.id.menu_aboutMe:{
                        Uri uri = Uri.parse("https://drive.google.com/open?id=1fYZ3sIswjGjCUA4T0crA9uHOHkRvVvx2");
                        startActivity(new Intent(Intent.ACTION_VIEW , uri));
                        break;
                    }
                }
                break;
            }
        }
        drawerlayout.closeDrawer(GravityCompat.START);
        Log.i(TAG, "MainActivity_onNavigationItemSelected");
        return true;
    }

    private void logout() {
        sessionCheck.setLogged_in(false);
        finish();
        Intent intent = new Intent(getApplicationContext() , SplashScreen.class);
        startActivity(intent);
        Log.i(TAG, "MainActivity_logout");
    }

    protected void bindView(){
        ivUserImage = this.findViewById(R.id.ivUserImage);
        vBtnQrCodeGen = this.findViewById(R.id.vBtnQrCodeGen);
        vBtnQrCodeReader = this.findViewById(R.id.vBtnQrCodeReader);
        drawerlayout = this.findViewById(R.id.drawerNavMenu);
        NameView = this.findViewById(R.id.nameView);
        IDView = this.findViewById(R.id.idView);
    }
}
