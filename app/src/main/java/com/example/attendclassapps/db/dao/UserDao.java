package com.example.attendclassapps.db.dao;

import android.content.Context;

public class UserDao {

    protected Context context;
    private String User_ID;

    public static final String Table = "UserLogin";

    public class Column {
        public static final String user_id = "ID";
    }

    public UserDao(String User_ID) {
        this.User_ID = User_ID;
    }

    public UserDao() {

    }

    public String getUser_ID() {
        return User_ID;
    }
    public void setUser_ID(String User_ID) {
        this.User_ID = User_ID;
    }

    public UserDao(Context context) {
        this.context = context;
    }

}
