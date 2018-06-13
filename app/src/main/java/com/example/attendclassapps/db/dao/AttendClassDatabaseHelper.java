package com.example.attendclassapps.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AttendClassDatabaseHelper extends SQLiteOpenHelper implements UserManagerHelper {

    //database name
    private static final String DB_Name = "AttendClassData";
    //database version
    private static final int DB_Version = 1;
    //database path
    private static String DB_Path = null;

    private SQLiteDatabase sqLiteDatabase;
    private final Context context;

    public AttendClassDatabaseHelper(Context context){
        super(context, DB_Name,null,DB_Version);
        this.context = context;
        DB_Path = "/data/data/" + context.getPackageName() + "/" + "databases/";
    }

    public void createDataBase() {
        boolean DBExist = checkDataBase();
        if (DBExist){
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error();
            }
        }
    }
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String path = DB_Path + DB_Name;
            checkDB = SQLiteDatabase.openDatabase(path, null , SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {

        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }
    private void copyDataBase() throws IOException {
        InputStream inputStream = context.getAssets().open(DB_Name);
        String outFileName =  DB_Path + DB_Name;
        OutputStream outputStream = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public void openDataBase() throws SQLException {
        String path = DB_Path + DB_Name;
        sqLiteDatabase = SQLiteDatabase.openDatabase(path, null ,SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (sqLiteDatabase != null)
            sqLiteDatabase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public UserDao checkUserLogin(UserDao userDao) {
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(UserDao.Table , null , UserDao.Column.user_id + " = ?",
                new String[] { userDao.getUser_ID()} ,
                null ,
                null ,
                null);

        UserDao currentUserDao = new UserDao();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                currentUserDao.setUser_ID(cursor.getString(0));
                sqLiteDatabase.close();
                return currentUserDao;
            }
        }
        return null;
    }
}
