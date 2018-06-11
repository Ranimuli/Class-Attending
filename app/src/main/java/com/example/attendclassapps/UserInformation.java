package com.example.attendclassapps;

public class UserInformation {

    private String name, id ;
    private String checkTime;

    public UserInformation(){

    }

    public UserInformation(String getID, String getname , String getCheckTime) {
        this.id = getID;
        this.name = getname;
        this.checkTime = getCheckTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }
}
