// patient class.
// Currently, copy the criminal class from the book
// THis is the Model part of MVC(Model-Controller-View)

package com.example.woundmanage;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import cn.bmob.v3.BmobObject;

public class Patient extends BmobObject implements Serializable {

    // UUID is a Java utility class included in the Android framework
    //private UUID pId;

    private String patientName;
    private String patientInfor1;
    private String patientInfor2;
    private Date pDate;




    public String getPatientInfor1() {
        return patientInfor1;
    }

    public void setPatientInfor1(String patientInfor1) {
        this.patientInfor1 = patientInfor1;
    }

    public String getPatientInfor2() {
        return patientInfor2;
    }

    public void setPatientInfor2(String patientInfor2) {
        this.patientInfor2 = patientInfor2;
    }

//    private boolean pSolved;


    public Patient() {
    //    pId = UUID.randomUUID(); //generate universally unique ID
        pDate = new Date();
    }

    //public UUID getId() {
    //    return pId;
    //}

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String name) {
        this.patientName = name;
    }

    public Date getDate() {
        return pDate;
    }

//    public void setDate(Date pDate) {
//        this.pDate = pDate;
//    }

//    public boolean isSolved() {
//        return pSolved;
//    }

//    public void setSolved(boolean pSolved) {
//        this.pSolved = pSolved;
//    }



}
