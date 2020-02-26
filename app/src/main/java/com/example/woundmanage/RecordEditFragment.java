package com.example.woundmanage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class RecordEditFragment extends Fragment {


    //----------------------------------------------------------------
    // for two args: the record passed in and the one return back
    // keys of arguments
    //----------------------------------------------------------------
    private static final String ARG_RECORD = "record";
    private static final String ARG_RECORD_BACK = "record_back";

    // these two hold the arguments from fragment's activity
    private Record mRecord; // hold a record object
    private String mActivityRecordString;  // hold the object key from the activity


    //----------------------------------------------------------------
    // widgets handle
    //----------------------------------------------------------------
    private EditText mTitleField_record_info; // record information foobar
    private TextView mTextView_record_title;   // shows the patient name
    private TextView mTextView_record_patient_name;   // shows the patient name
    private Button mButton_save_record;
    private RadioGroup mRadioGroup1;
    private RadioGroup mRadioGroup2;
    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;
    private RadioButton mRadioButton3;
    private RadioButton mRadioButton4;

    //----------------------------------------------------------------
    // Public fragment creation method.
    // Used by host activity; create fragment with args
    // If we create the fragment in activity directly with new RecordFragment,
    // we have to know the fragment's argument keys, e.g. ARG_RECORD, etc.
    //----------------------------------------------------------------
    public static RecordEditFragment newInstance(Record record) {

        // create Bundle object and add two arguments
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECORD, record);
        //args.putSerializable(ARG_RECORD_BACK, backRecordString);

        // create fragment
        RecordEditFragment fragment = new RecordEditFragment();
        fragment.setArguments(args);

        return fragment;
    }


    //----------------------------------------------------------------
    // Some initialization
    // Note the layout is not inflated here.
    // Obtaining the arg using getArguments passed by the activity
    //----------------------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bmob.initialize(getContext(), "67893c5b8d8829a456b0fd2153a19d59");


        // get arguments from host activity
        mRecord = (Record) getArguments().getSerializable(ARG_RECORD);
        mActivityRecordString = (String) getArguments().getSerializable(ARG_RECORD_BACK);


    }


    //----------------------------------------------------------------
    // Inflate the view here
    // Also wire up the widgets
    //----------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate the fragment's layout
        View v = inflater.inflate(R.layout.fragment_record_edit, container, false);

        getActivity().setTitle("添加记录");



        mTextView_record_patient_name = v.findViewById(R.id.record_patient_name);

        mTextView_record_patient_name.setText(mRecord.getPatient().getPatientName());


        //mTextView_record_title = v.findViewById(R.id.record_title);
        //mTextView_record_title.setText(mRecord.getTitle());

        mRadioGroup1 = v.findViewById(R.id.radioGroup1);
        mRadioGroup2 = v.findViewById(R.id.radioGroup2);

        mRadioButton1 = v.findViewById(R.id.radioButton1);
        mRadioButton2 = v.findViewById(R.id.radioButton2);
        mRadioButton3 = v.findViewById(R.id.radioButton3);
        mRadioButton4 = v.findViewById(R.id.radioButton4);


        mRadioGroup1.setOnCheckedChangeListener(listener1);
        mRadioGroup2.setOnCheckedChangeListener(listener2);





        //------------------------------------------------
        // record save button
        //------------------------------------------------
        mButton_save_record = v.findViewById(R.id.record_save);
        mButton_save_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // insert it to the DB
                mRecord.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if(e==null){
                            toast("添加record成功，返回objectId为："+objectId);
                            mButton_save_record.setEnabled(false);

                        }else{
                            toast("创建record失败：" + e.getMessage());
                        }
                    }
                });

                //getActivity().onBackPressed();
            }
        });


        // text field
        mTitleField_record_info = v.findViewById(R.id.record_info);
//
//
//        // mPatient is from the list view, so we use that to update the view
//        mTitleField_record_info.setText(mRecord.getTitle());

        // add listener
        mTitleField_record_info.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            // CharSequence that is the user’s input
            // This method returns a string, which you then use to set the Patient’s title.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRecord.setRecordInfo(s.toString());

            }
            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        return v;
    }


    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                mRadioGroup2.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                mRadioGroup2.clearCheck(); // clear the second RadioGroup!
                mRadioGroup2.setOnCheckedChangeListener(listener2); //reset the listener
                //Log.e("XXX2", "do the work");

                switch ( group.getCheckedRadioButtonId()){
                    case R.id.radioButton1:
                        toast("b1 pushed");
                        break;
                    case R.id.radioButton2 :
                        toast("b2 pushed");
                        break;
                }
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {


        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                mRadioGroup1.setOnCheckedChangeListener(null);
                mRadioGroup1.clearCheck();
                mRadioGroup1.setOnCheckedChangeListener(listener1);
                //Log.e("XXX2", "do the work");

                switch ( group.getCheckedRadioButtonId()){
                    case R.id.radioButton3:
                        toast("b3 pushed");
                        break;
                    case R.id.radioButton4 :
                        toast("b4 pushed");
                        break;
                }
            }
        }
    };





//    public void handleCombinedClick(View view) {
//        // Clear any checks from both groups:
//        mRadioGroup1.clearCheck();
//        //mRadioGroup2.clearCheck();
//
//
//        // Manually set the check in the newly clicked radio button:
////        ((RadioButton) view).setChecked(true);
////
////        // Perform any action desired for the new selection:
////        switch (view.getId()) {
////            case R.id.radioButton1:
////                // do something
////                toast("button 1 checked");
////
////                break;
////
////            case R.id.radioButton3:
////                toast("button 3 checked");
////                // do something
////                break;
////
////
////        }
//    }



    public void toast(String s){
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

}
