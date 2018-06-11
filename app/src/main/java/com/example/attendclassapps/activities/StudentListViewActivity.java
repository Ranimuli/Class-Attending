package com.example.attendclassapps.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.attendclassapps.Qr_GetTextForGenerate;
import com.example.attendclassapps.R;
import com.example.attendclassapps.StudentList;
import com.example.attendclassapps.StudentModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentListViewActivity extends AppCompatActivity {

    /*AttendClassDatabaseHelper attendClassDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;
    ListView listView;
    UserSession userSession;
    String getID,getName,getMajor;*/

    //private static final String TAG = "ViewDatabase";

    private List<StudentModel> result;
    private SharedPreferences sharedPreferences;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;

    TextView ClassRoomName;
    ListView studentLV;

    private static final String TAG = "Log_Attend_Class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_list_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Student in Class");

        //UserSession userSession = new UserSession();
        sharedPreferences = this.getSharedPreferences(Qr_GetTextForGenerate.subjectSharedPreference , MODE_PRIVATE);
        String subjectName = sharedPreferences.getString(Qr_GetTextForGenerate.subject_name_KEY , null);
        String subjectID = sharedPreferences.getString(Qr_GetTextForGenerate.subject_id_KEY , null);
        ClassRoomName = findViewById(R.id.subjectShow);
        Qr_GetTextForGenerate qr_getTextForGenerate = new Qr_GetTextForGenerate(StudentListViewActivity.this);

        if (subjectName == null && subjectID == null) {
             Log.i(TAG, "StudentListViewActivity_onCreate_Empty Class");
         }
         else {
             ClassRoomName.setText(qr_getTextForGenerate.getSubjectName() + " " + qr_getTextForGenerate.getSubjectId());
             Log.i(TAG, "StudentListViewActivity_onCreate_Opened Class");
         }

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Student_join").child("Class_room");

        //textID = findViewById(R.id.viewStudentID);
        //textName = findViewById(R.id.viewStudentName);
        studentLV = findViewById(R.id.listVStudent);

        result = new ArrayList<>();


        /*recyclerView = findViewById(R.id.studentRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        studentAdapter = new StudentAdapter(result);
        recyclerView.setAdapter(studentAdapter);*/

        updateList();
        Log.i(TAG, "StudentListViewActivity_onCreate");

    }

    /*private void updateList() {
         myRef.addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                 result.add(dataSnapshot.getValue(StudentModel.class));
                 studentAdapter.notifyDataSetChanged();
             }

             @Override
             public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                 StudentModel studentModel = dataSnapshot.getValue(StudentModel.class);

                 int index = getItemIndex(studentModel);

                 result.set(index , studentModel);
                 studentAdapter.notifyItemChanged(index);
             }

             @Override
             public void onChildRemoved(DataSnapshot dataSnapshot) {

             }

             @Override
             public void onChildMoved(DataSnapshot dataSnapshot, String s) {

             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });
    }

    private int getItemIndex(StudentModel studentModel) {
        int index = -1;
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).uniqueKey.equals(studentModel.uniqueKey));
            index = 1;
            break;
        }
        return index;
    }*/

    /*private void updateList() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    //Map map = (Map) ds.child("Student_join").getValue();

                    StudentModel studentModel = ds.child("Student_join").child("Student").getValue(StudentModel.class);
                    result.add(studentModel);

                    //String idgt = String.valueOf(dataSnapshot.child("Student_join").child("Student").child("id").getValue());
                    //String namegt = String.valueOf(dataSnapshot.child("Student_join").child("Student").child("name").getValue());
                    //textID.setText(idgt);
                    //textName.setText(namegt);

                    //display all the information
                    /*Log.d(TAG, "showData: name: " + idgt);
                    Log.d(TAG, "showData: email: " + namegt);

                    /*ArrayList<String> array  = new ArrayList<>();
                    array.add(namegt + " "+ idgt);
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext() , R.layout.my_listview,array);
                    studentLV.setAdapter(adapter);

                }

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    i = String.valueOf(map.get(myRef.child("Student_join").child("Student").child("ID: "+userSession.User_ID())));
                    //Log.v(TAG,""+ childDataSnapshot.child("Student_join").child("Student").child("ID: "+userSession.User_ID()).getValue());
                    textID.setText(i);
                }

                ArrayList<String> arr_list = new ArrayList<>();
                i = String.valueOf(map.get(myRef.child("Student_join").child("Student").child("ID: "+userSession.User_ID())));
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    arr_list.add(i);
                }

                //Log.v(TAG,""+ childDataSnapshot.child("Student_join").child("Student").child("ID: "+userSession.User_ID()).getValue());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_listview , arr_list);
                textID.setText(i);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }*/

    /*@Override
    protected void onStart() {
        super.onStart();*/
    private void updateList() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    StudentModel studentModel = studentSnapshot.getValue(StudentModel.class);
                    result.add(studentModel);
                }
                StudentList adapter = new StudentList(StudentListViewActivity.this , result);
                studentLV.setAdapter(adapter);
                Log.i(TAG, "StudentListViewActivity_onDataChange_get Data From Firebase");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    /*private int getItemIndex(StudentModel studentModel) {
         int index = -1;

         for (int i = 0; i < result.size(); i++) {
             if (result.get(i).userKEY_Student.equals(studentModel.userKEY_Student));
         }
         return index;
    }*/
}

    /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void showData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            UserInformation uInfo = new UserInformation();
            uInfo.setName(ds.child(userID).getValue(UserInformation.class).getId());
            uInfo.setName(ds.child(userName).getValue(UserInformation.class).getName());

            //display all the information
            Log.d(TAG, "showData: รหัสนักศึกษา: " + uInfo.getId());
            Log.d(TAG, "showData: ชื่อ: " + uInfo.getName());

            ArrayList<String> array  = new ArrayList<>();
            array.add(uInfo.getId());
            array.add(uInfo.getName());
            ArrayAdapter adapter = new ArrayAdapter(StudentListViewActivity.this,R.layout.my_listview,array);
            StudentlistView.setAdapter(adapter);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
/*
        //listView = findViewById(R.id.student_list);
        /*attendClassDatabaseHelper = new AttendClassDatabaseHelper(this);
        userSession = new UserSession(this);
        String getUserID = userSession.User_ID();
        String teacherTable = "Teacher";

        /*sqLiteDatabase = this.attendClassDatabaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ teacherTable +" WHERE ID = '" + getUserID + "'" ,null);
        ArrayList<StudentListViewActivity> arr_list = new ArrayList<>();
        /*if(cursor != null){
            cursor.moveToFirst();
        }
        while(!cursor.isAfterLast()){
            arr_list.add(new StudentListViewActivity(cursor.getString(cursor.getColumnIndex("ID")),
                    cursor.getString(cursor.getColumnIndex("Fullname")),
                    cursor.getString(cursor.getColumnIndex("Major"))));
            cursor.moveToNext();
        }*/
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_listview , arr_list);
        //listView.setAdapter(adapter);
        /*if(arr_list.size() > 0 ){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Student_Join").child("Teacher");
            for(StudentListViewActivity studentListViewActivity : arr_list){
                databaseReference.push().setValue(studentListViewActivity);
            }
        }
    }

    /*public StudentListViewActivity(String setId, String setName, String setMajor){
        this.getID = setId;
        this.getName = setName;
        this.getMajor = setMajor;
    }*/

    /*@Override
    public void onStop() {
        super.onStop();
        attendClassDatabaseHelper.close();
        sqLiteDatabase.close();
    }
        /*sharedPreferences = this.getSharedPreferences(UserSession.user_show_KEY , MODE_PRIVATE);

        attendClassDatabaseHelper = new AttendClassDatabaseHelper(this);
        sqLiteDatabase = attendClassDatabaseHelper.getWritableDatabase();

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM Enroll WHERE Student_ID " ,null);
        ArrayList<String> arr_list = new ArrayList<>();
        if(cursor != null){
            cursor.moveToFirst();
        }
        while(!cursor.isAfterLast()){
            arr_list.add(cursor.getString(cursor.getColumnIndex("Class_Day"))
            + "\n"+ cursor.getString(cursor.getColumnIndex("Subject_ID"))
            + "\n" + cursor.getString(cursor.getColumnIndex("Subject_Name"))
            + "\nTime: "+ cursor.getString(cursor.getColumnIndex("Class_Start")));
            cursor.moveToNext();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_listview , arr_list);
        listView.setAdapter(adapter);

        Log.i("Log_Attend_Class", "StudentListViewActivity_onCreate");
    }

    @Override
    protected void onStop() {
        super.onStop();
        attendClassDatabaseHelper.close();
        sqLiteDatabase.close();
        Log.i("Log_Attend_Class", "StudentListViewActivity_onStop");
        }*/