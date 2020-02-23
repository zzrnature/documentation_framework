package com.example.woundmanage;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;





import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RecordListFragment extends Fragment {



    private RecyclerView mRecordRecyclerView;
    private RecordListFragment.RecordAdapter mAdapter;

    private static final String ARG_PATIENT = "patient";


    // the passed in patient from the patient list;
    // each record must have a patient
    private Patient mPatient; // hold a patient object
    private TextView mPatientName;
//    private TextView mPatientDate;
    private TextView mPaientInfo1;
    private TextView mPaientInfo2;


    // Used by the container activity; create fragment with args
    public static RecordListFragment newInstance(Patient patient) {

        // create a argument, a Bundle object
        Bundle args = new Bundle();
        args.putSerializable(ARG_PATIENT, patient);

        // create fragment
        RecordListFragment fragment = new RecordListFragment();
        fragment.setArguments(args);

        return fragment;
    }


    // some other initialization
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // obtain the arg's patient
        Patient patient = (Patient) getArguments().getSerializable(ARG_PATIENT);

        mPatient = patient;


        Bmob.initialize(getContext(), "67893c5b8d8829a456b0fd2153a19d59");

        // for menu
        setHasOptionsMenu(true);

    }


    // infalte layout and shwo the list
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate the fragment's layout
        View v = inflater.inflate(R.layout.fragment_record_list, container, false);



        getActivity().setTitle("患者记录");


        mPatientName = v.findViewById(R.id.record_list_patient_name);
        //mPaientInfo1 = v.findViewById(R.id.record_list_patient_info1);
        //mPaientInfo2 = v.findViewById(R.id.record_list_patient_info2);



        mPatientName.setText(mPatient.getPatientName());
        //mPaientInfo1.setText(mPatient.getPatientInfor1());
        //mPaientInfo2.setText(mPatient.getPatientInfor2());


        //mTitleField = (TextView) v.findViewById(R.id.record_list_patient_title); // find the widget
        //mTitleField.setText(mPatient.getPatientName());



        // from the view, find the id from the view
        mRecordRecyclerView = v.findViewById(R.id.record_recycler_view);
        // set a layout manager
        mRecordRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        mRecordRecyclerView.addItemDecoration(new TestDividerItemDecoration());



        updateUI();

        return v;
    }


    // This is for: from the editing to the list
    // we have to update the list
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    // menu creation
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_record_list, menu);
    }


    // menu listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // entering the patient editing page
            case R.id.new_record:


                //toast("menu pushed");
                Record record = new Record(mPatient);

                // foo
                record.setTitle("记录");

                // using the RecordActivity delegation method: newIntent_record
                Intent intent = RecordEditActivity.newIntent_record(getActivity(), record);

                //toast("menu pushed");
                //startActivityForResult(intent, REQUEST_CODE_RECORD);
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //query happens here
    private void updateUI() {

        if (mAdapter == null){
            queryRecordlist();
        } else {
            queryRecordlist();
            mAdapter.notifyDataSetChanged();
        }

    }


    // real binding happens in holder
    private class RecordHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        // to handle the patient in bind method
        private Record mRecord;
        private TextView mInfoTextView;
        private TextView mDateTextView;
        //private TextHView TextView;


        // in constructor, we pass the view and pull out interested widgets
        // also set listeners
        public RecordHolder(LayoutInflater inflater, ViewGroup parent) {

            // inflater inflates a view with the layout list_item_patient
            super(inflater.inflate(R.layout.list_item_record, parent, false));

            // for listener
            itemView.setOnClickListener(this);


            // itemview holds a reference to the entire View you passed into super(view)
            mInfoTextView = itemView.findViewById(R.id.record_info);
            mDateTextView = itemView.findViewById(R.id.record_date);

            //mDateTextView = (TextView) itemView.findViewById(R.id.patient_date);
        }

        // data change, used by adaptor
        public void bind(Record record) {
            mRecord = record;
            mInfoTextView.setText(mRecord.getRecordInfo());


            SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");

            mDateTextView.setText(sf.format(mRecord.getDate()));


            //mDateTextView.setText(mRecord.getDate().toString().substring(0,11));





        }

        @Override
        public void onClick(View view) {

            toast("clicked" + mRecord.getTitle());

            // create an intent to start another patient edit activity
            // Here we use the public method of patient activity to create the intent
            Intent intent = RecordBrowseActivity.newIntent_record_list(getActivity(), mRecord);

            startActivity(intent);

        }
    }


    // adapter; note that the adapter is simple and dumb; just received data
    private class RecordAdapter extends RecyclerView.Adapter<RecordListFragment.RecordHolder> {

        // Adapter is to hold the data, so it has to have a handler of data
        private List<Record> mRecords;

        // in constructor, just holding the data as an argument
        public RecordAdapter(List<Record> records) {
            mRecords = records;
        }

        // override some methods
        @Override
        public RecordListFragment.RecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // create an inflater
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            // create a new holder and return
            return new RecordListFragment.RecordHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(RecordListFragment.RecordHolder holder, int position) {
            // get a patient and give it to the holder
            Record record = mRecords.get(position);
            holder.bind(record);
        }



        // Get the size of the list. The list is using the private handler
        @Override
        public int getItemCount() {
            return mRecords.size();
        }
    }




    private void queryRecordlist(){

        // query DB to show the recyclerview
        BmobQuery<Record> query = new BmobQuery<>();

        //query.addWhereEqualTo("pTitle", st);

        String st = mPatient.getObjectId();
        query.addWhereEqualTo("pPatientId", st);

        query.order("-createdAt");

        query.findObjects(new FindListener<Record>() {
            @Override
            public void done(List<Record> rds, BmobException e) {
                if (e == null) {

                    // query result is rds; send it to the adapter
                    // The following two must be together here !!!
                    mAdapter = new RecordListFragment.RecordAdapter(rds);
                    mRecordRecyclerView.setAdapter(mAdapter);

                } else {
                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public void toast(String s){
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }


}








