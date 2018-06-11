package com.example.attendclassapps;

public interface UserManagerHelper {
    public static final String DB_Name = "AttendClassData";
    public static final int DB_Version = 1;

    public User checkUserLogin(User user);
}
