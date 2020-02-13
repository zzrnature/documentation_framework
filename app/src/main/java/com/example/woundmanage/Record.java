
package com.example.woundmanage;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import cn.bmob.v3.BmobObject;

public class Record extends BmobObject implements Serializable {

    // UUID is a Java utility class included in the Android framework
    private String pPatientId;

    private String pTitle;

    private Date pDate;

    private boolean pSolved;

    private Patient mPatient;

    private String mPatientName;

    public Record (Patient p) {

        //pId = UUID.randomUUID(); //generate universally unique ID

        pPatientId = p.getObjectId();
        pDate = new Date();
        mPatient = p;
        mPatientName = p.getTitle();
    }

    //public UUID getId() {
    public String getpPatientId() {
        return pPatientId;
    }

    public String getPatientName() {
        return mPatientName;
    }

    public Patient getPatient(){
        return mPatient;
    }

    public String getTitle() {
        return pTitle;
    }

    public void setTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public Date getDate() {
        return pDate;
    }

    public void setDate(Date pDate) {
        this.pDate = pDate;
    }

    public boolean isSolved() {
        return pSolved;
    }

    public void setSolved(boolean pSolved) {
        this.pSolved = pSolved;
    }



}
