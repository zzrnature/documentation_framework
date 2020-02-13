package com.example.woundmanage;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class RecordEditActivity extends SingleFragmentActivity{
    // two argument keys, one being the input, another the output
    // key of the input record data
    private static final String INPUT_RECORD = "com.example.woundmanage.input_record";

    // key of the result record data
    private static final String BACK_RECORD = "com.example.woundmanage.back_record";


    //----------------------------------------------------------------
    // for other activity's fragments to start this activity
    // because the INPUT_RECORD is our own string.
    // If other activity or fragment directly start, they have to know the keys, e.g. INPUT_RECORD
    // packageContext is the source context
    //----------------------------------------------------------------
    public static Intent newIntent_record(Context packageContext, Record record) {
        Intent intent = new Intent(packageContext, RecordEditActivity.class);
        intent.putExtra(INPUT_RECORD, record);
        return intent;
    }


    // Another delegation method to fetch the real record
//    public static Record getIntentRecord(Intent data){
//        return (Record) data.getSerializableExtra(BACK_RECORD);
//    }



    //----------------------------------------------------------------
    // create the fragment; the real creation has been delegated by newInstance function
    // since when creating a fragment from the list, an argument is passed in,
    // we get that argument and when creating the fragment, we add arguments to it
    //----------------------------------------------------------------
    @Override
    protected Fragment createFragment() {

        // get the argument from the intent that starts the activity
        Record record = (Record) getIntent().getSerializableExtra(INPUT_RECORD);


        // create the fragment with arguments, one being the real record,
        // another being the data key. We hold this key only in this activity, so we have to
        // pass it to the fragment
        return RecordEditFragment.newInstance(record);
    }




    public void toast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
