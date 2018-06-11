package com.example.attendclassapps;

public class StudentModel {

    public String id , name , checkTime;

    public StudentModel() {

    }

    public StudentModel(String getUserIdFirebase, String getUserNameFirebase , String getUserCheckTime) {
        this.id = getUserIdFirebase;
        this.name = getUserNameFirebase;
        this.checkTime = getUserCheckTime;
    }
}
