package com.example.woundmanage;

import android.content.Context;
import android.content.Intent;


import androidx.fragment.app.Fragment;


public class RecordListActivity extends SingleFragmentActivity {

    private static final String INPUT_PATIENT_RECORD = "com.example.woundmanage.input_patient_record";



    @Override
    protected Fragment createFragment() {

        // get the argument from the intent that starts the activity
        Patient patient = (Patient) getIntent().getSerializableExtra(INPUT_PATIENT_RECORD);

        // create the fragment with an argument
        return RecordListFragment.newInstance(patient);
    }


    public static Intent newIntent_record_list(Context packageContext, Patient patient) {
        Intent intent = new Intent(packageContext, RecordListActivity.class);
        intent.putExtra(INPUT_PATIENT_RECORD, patient);
        return intent;
    }

}

