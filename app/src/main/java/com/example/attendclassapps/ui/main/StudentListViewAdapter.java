package com.example.attendclassapps.ui.main;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.attendclassapps.R;
import com.example.attendclassapps.model.StudentModel;

import java.util.List;

public class StudentListViewAdapter extends ArrayAdapter<StudentModel> {

    private Activity context;
    private List<StudentModel> studentList;

    public StudentListViewAdapter(Activity context, List<StudentModel> studentList) {
        super(context , R.layout.student_pattern , studentList);
        this.context = context;
        this.studentList = studentList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewStudent = inflater.inflate(R.layout.student_pattern, null , true);

        TextView textViewID = listViewStudent.findViewById(R.id.viewStudentID);
        TextView textViewName = listViewStudent.findViewById(R.id.viewStudentName);
        TextView textViewTime = listViewStudent.findViewById(R.id.viewTime);

        StudentModel studentModel = studentList.get(position);
        textViewID.setText(studentModel.id);
        textViewName.setText(studentModel.name);
        textViewTime.setText(studentModel.checkTime);

        return listViewStudent;
    }
}
