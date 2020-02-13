package com.example.woundmanage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.bmob.v3.Bmob;


public class RecordBrowseFragment extends Fragment {


    // each record must have a record
    private Record mRecord; // hold a record object
    private TextView mRecordInfo;
    private TextView mRecordPatientName;


    private static final String ARG_RECORD = "record";




    // Used by the container activity; create fragment with args
    public static RecordBrowseFragment newInstance(Record record) {

        // create a argument, a Bundle object
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECORD,  record);

        // create fragment
        RecordBrowseFragment fragment = new RecordBrowseFragment();
        fragment.setArguments(args);

        return fragment;
    }


    // some other initialization
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // obtain the arg's record
        Record record = (Record) getArguments().getSerializable(ARG_RECORD);

        mRecord = record;

        Bmob.initialize(getContext(), "67893c5b8d8829a456b0fd2153a19d59");
        // for menu
        //setHasOptionsMenu(true);

    }


    // infalte layout and shwo the list
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate the fragment's layout
        View v = inflater.inflate(R.layout.fragment_record_browse, container, false);


        mRecordInfo = v.findViewById(R.id.record_info); // find the widget
        mRecordInfo.setText(mRecord.getTitle());


        mRecordPatientName = v.findViewById(R.id.record_patient_name);
        mRecordPatientName.setText(mRecord.getPatientName());


        return v;
    }


}
