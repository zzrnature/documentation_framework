package com.example.woundmanage;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;


public class RecordBrowseActivity extends SingleFragmentActivity {

    private static final String INPUT_RECORD_RECORD = "com.example.woundmanage.input_record_record";



    @Override
    protected Fragment createFragment() {

        // get the argument from the intent that starts the activity
        Record record = (Record) getIntent().getSerializableExtra(INPUT_RECORD_RECORD);

        // create the fragment with an argument
        return RecordBrowseFragment.newInstance(record);
    }


    public static Intent newIntent_record_list(Context packageContext, Record record) {
        Intent intent = new Intent(packageContext, RecordBrowseActivity.class);
        intent.putExtra(INPUT_RECORD_RECORD, record);
        return intent;
    }

}

