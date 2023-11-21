package com.google.mediapipe.examples.gesturerecognizer.java.Registration.firebasedatabase;

public class ReadWriteUserDetails {
    public String fullname, doB, gender, mobile;

    //Constructor
    public ReadWriteUserDetails() {};
    public ReadWriteUserDetails(String textFullName, String textDoB, String textGender, String textMobile) {
        this.fullname = textFullName;
        this.doB = textDoB;
        this.gender = textGender;
        this.mobile = textMobile;
    }
}
