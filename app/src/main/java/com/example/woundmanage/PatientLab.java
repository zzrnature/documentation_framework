// data class; holding a list of patients
// It uses a singleton pattern

package com.example.woundmanage;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PatientLab {


    // singleton pattern
    private static PatientLab sPatientLab;


    // a list of patients
    private List<Patient> mPatients;

    public static PatientLab get(Context context) {
        if (sPatientLab == null) {
            sPatientLab = new PatientLab(context);
        }
        return sPatientLab;
    }

    // the real constructor
    private PatientLab(Context context) {
        mPatients = new ArrayList<>(); // create an empty list

        // populate the list
//        for (int i = 0; i < 100; i++) {
//            Patient patient = new Patient();
//            // set the title and solved
//            patient.setTitle("Patient #" + i);
//            patient.setSolved(i % 2 == 0); // Every other one
//            mPatients.add(patient);
//        }
    }


    // return a list of patients
    public List<Patient> getPatients() {
        return mPatients;
    }

    // given a id, get a patient
    public Patient getPatient(UUID id) {
        for (Patient patient : mPatients) {
            if (patient.getId().equals(id)) {
                return patient;
            }
        }
        return null;
    }


    public void addPatient(Patient p) {
        mPatients.add(p);
    }


}



















