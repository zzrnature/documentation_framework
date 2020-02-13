// activity holding the patient fragment


package com.example.woundmanage;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;


public class PatientEditActivity extends SingleFragmentActivity {

    // two argument keys, one being the input, another the output
    // key of the input patient data
    private static final String INPUT_PATIENT = "com.example.woundmanage.input_patient";

    // key of the result patient data
    //private static final String BACK_PATIENT = "com.example.woundmanage.back_patient";


    //----------------------------------------------------------------
    // for other activity's fragments to start this activity
    // because the INPUT_PATIENT is our own string.
    // If other activity or fragment directly start, they have to know the keys, e.g. INPUT_PATIENT
    // packageContext is the source context
    //----------------------------------------------------------------
    public static Intent newIntent_patient(Context packageContext, Patient patient) {
        Intent intent = new Intent(packageContext, PatientEditActivity.class);
        intent.putExtra(INPUT_PATIENT, patient);
        return intent;
    }


    //----------------------------------------------------------------
    // create the fragment; the real creation has been delegated by newInstance function
    // since when creating a fragment from the list, an argument is passed in,
    // we get that argument and when creating the fragment, we add arguments to it
    //----------------------------------------------------------------
    @Override
    protected Fragment createFragment() {

        // get the argument from the intent that starts the activity
        Patient patient = (Patient) getIntent().getSerializableExtra(INPUT_PATIENT);

        // create the fragment with arguments, one being the real patient,
        // another being the data key. We hold this key only in this activity, so we have to
        // pass it to the fragment
        //return PatientEditFragment.newInstance(patient, BACK_PATIENT);
        return PatientEditFragment.newInstance(patient);
    }

}
