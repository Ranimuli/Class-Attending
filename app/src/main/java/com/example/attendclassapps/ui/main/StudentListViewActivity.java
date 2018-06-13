package com.example.attendclassapps.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.attendclassapps.ui.generator.Qr_GetTextForGenerate;
import com.example.attendclassapps.R;
import com.example.attendclassapps.model.StudentModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentListViewActivity extends AppCompatActivity {

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
        
        studentLV = findViewById(R.id.listVStudent);

        result = new ArrayList<>();
        
        updateList();
        Log.i(TAG, "StudentListViewActivity_onCreate");

    }
    
    private void updateList() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    StudentModel studentModel = studentSnapshot.getValue(StudentModel.class);
                    result.add(studentModel);
                }
                StudentListViewAdapter adapter = new StudentListViewAdapter(StudentListViewActivity.this , result);
                studentLV.setAdapter(adapter);
                Log.i(TAG, "StudentListViewActivity_onDataChange_get Data From Firebase");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}