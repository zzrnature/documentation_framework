package com.example.woundmanage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


        mTextView_record_patient_name = v.findViewById(R.id.record_patient_name);

        mTextView_record_patient_name.setText(mRecord.getPatient().getPatientName());


        mTextView_record_title = v.findViewById(R.id.record_title);
        mTextView_record_title.setText(mRecord.getTitle());




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




    public void toast(String s){
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

}
