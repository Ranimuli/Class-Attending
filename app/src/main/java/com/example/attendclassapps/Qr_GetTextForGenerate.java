package com.example.attendclassapps;

import android.content.Context;
import android.content.SharedPreferences;

public class Qr_GetTextForGenerate {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    Context context;
    String subjectName , subjectId;
    public static final  String subjectSharedPreference = "subject" , subject_name_KEY = "subjectName" , subject_id_KEY = "subjectID";

    public Qr_GetTextForGenerate() {

    }

    public Qr_GetTextForGenerate(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(subjectSharedPreference, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setSubjectName (String subjectName) {
        editor.putString(subject_name_KEY , subjectName);
        editor.commit();
    }
    public void setSubjectId (String subjectId) {
        editor.putString(subject_id_KEY , subjectId);
        editor.commit();
    }

    public String getSubjectName(){
        this.subjectName = sharedPreferences.getString(subject_name_KEY , "");
        return subjectName;
    }
    public String getSubjectId(){
        this.subjectId = sharedPreferences.getString(subject_id_KEY , "");
        return subjectId;
    }
}
