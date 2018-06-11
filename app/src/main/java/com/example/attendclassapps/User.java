package com.example.attendclassapps;

import android.content.Context;

public class User {

    protected Context context;
    private String User_ID;

    public static final String Table = "UserLogin";

    public class Column {
        public static final String user_id = "ID";
    }

    public User(String User_ID) {
        this.User_ID = User_ID;
    }

    public User() {

    }

    public String getUser_ID() {
        return User_ID;
    }
    public void setUser_ID(String User_ID) {
        this.User_ID = User_ID;
    }

    public User(Context context) {
        this.context = context;
    }

}
