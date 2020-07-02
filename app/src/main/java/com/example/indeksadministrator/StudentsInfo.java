package com.example.indeksadministrator;

public class StudentsInfo {

    private String Name;
    private String Surname;
    private String ID;

    public StudentsInfo() {}

    public StudentsInfo(String name, String surname, String ID) {
        Name = name;
        Surname = surname;
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
