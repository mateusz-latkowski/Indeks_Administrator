package com.example.indeksadministrator;

public class TeachersInformation {

    private String Name;
    private String Surname;
    private String AcademicTitle;
    private String Room;
    private String PhoneNumber;
    private String Email;
    private String UserID;
    private String ActivateAccount;

    public TeachersInformation() {}

    public TeachersInformation(String name, String surname, String academicTitle, String room, String phoneNumber, String email, String userID, String activateAccount) {
        this.Name = name;
        this.Surname = surname;
        this.AcademicTitle = academicTitle;
        this.Room = room;
        this.PhoneNumber = phoneNumber;
        this.Email = email;
        this.UserID = userID;
        this.ActivateAccount = activateAccount;
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

    public String getAcademicTitle() {
        return AcademicTitle;
    }

    public void setAcademicTtitle(String academicTitle) {
        AcademicTitle = academicTitle;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getActivateAccount() {
        return ActivateAccount;
    }

    public void setActivateAccount(String activateAccount) {
        ActivateAccount = activateAccount;
    }
}
