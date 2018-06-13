package com.example.attendclassapps.db.dao;

public interface UserManagerHelper {
    public static final String DB_Name = "AttendClassData";
    public static final int DB_Version = 1;

    public UserDao checkUserLogin(UserDao userDao);
}
