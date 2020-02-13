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
    private UUID pId;

    private String pTitle;

    private Date pDate;

    private boolean pSolved;

    public Patient() {
        pId = UUID.randomUUID(); //generate universally unique ID
        pDate = new Date();
    }

    public UUID getId() {
        return pId;
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
