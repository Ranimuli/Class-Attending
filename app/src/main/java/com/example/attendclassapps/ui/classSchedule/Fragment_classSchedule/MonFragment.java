package com.example.attendclassapps.ui.classSchedule.Fragment_classSchedule;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.attendclassapps.db.dao.AttendClassDatabaseHelper;
import com.example.attendclassapps.R;
import com.example.attendclassapps.db.session.UserSession;

import java.util.ArrayList;

public class MonFragment extends Fragment {

    AttendClassDatabaseHelper attendClassDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;
    ListView listView;
    UserSession userSession;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mon , container ,false);

        listView = view.findViewById(R.id.classListMon);
        attendClassDatabaseHelper = new AttendClassDatabaseHelper(getActivity());
        userSession = new UserSession(getActivity());
        String getUserID = userSession.User_ID();
        String setDay = "Monday";

        sqLiteDatabase = this.attendClassDatabaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Enroll WHERE Student_ID = '" + getUserID + "' AND Class_Day = '" + setDay + "'" ,null);
        ArrayList<String> arr_list = new ArrayList<>();
        if(cursor != null){
            cursor.moveToFirst();
        }
        while(!cursor.isAfterLast()){
            arr_list.add(cursor.getString(cursor.getColumnIndex("Subject_ID"))
                    + "\n" + cursor.getString(cursor.getColumnIndex("Subject_Name"))
                    + "\nTeacher:  " + cursor.getString(cursor.getColumnIndex("Teacher_Name"))
                    + "\nTime:  "+ cursor.getString(cursor.getColumnIndex("Class_Start")) + " - "
                    + ""+ cursor.getString(cursor.getColumnIndex("Class_Finish")));
            cursor.moveToNext();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.my_listview , arr_list);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        attendClassDatabaseHelper.close();
        sqLiteDatabase.close();
    }
}