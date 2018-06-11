package com.example.attendclassapps;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    protected Context context;
    public static final  String user_KEY = "user_logged" , logged_KEY = "stay_logged";

    public Session(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(user_KEY , Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setLogged_in(boolean logged_in) {
        editor.putBoolean(logged_KEY , logged_in);
        editor.commit();
    }
    public boolean logged_in() {
        return sharedPreferences.getBoolean(logged_KEY , false);
    }
}
