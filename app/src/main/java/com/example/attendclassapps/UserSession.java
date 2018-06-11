package com.example.attendclassapps;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    protected Context context;
    String User_ID_Session, User_Name_Session , User_State_Session;

    public static final  String user_show_KEY = "user_show" , getUser_ID_KEY = "get_user_id" , getUser_Name_KEY = "get_user_name" ,getUser_State_KEY = "get_user_state";

    public UserSession () {

    }

    public UserSession(String getUserID, String getUserName) {
        this.User_ID_Session = getUserID;
        this.User_Name_Session = getUserName;
    }

    public UserSession(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(user_show_KEY, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setUser_ID_Session(String User_ID) {
        editor.putString(getUser_ID_KEY , User_ID);
        editor.commit();
    }

    public void setUser_Name_Session(String User_Name) {
        editor.putString(getUser_Name_KEY, User_Name);
        editor.commit();
    }

    public void setUser_State_Session(String User_State){
        editor.putString(getUser_State_KEY , User_State);
        editor.commit();
    }

    public String User_ID () {
        return sharedPreferences.getString(getUser_ID_KEY , "");
    }

    public String User_Name () {
        return sharedPreferences.getString(getUser_Name_KEY , "");
    }

    public String User_State () {
        return sharedPreferences.getString(getUser_State_KEY , "");
    }

}
