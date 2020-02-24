package com.example.woundmanage;

import android.content.Intent;
import android.os.Bundle;
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


public class PatientListFragment extends Fragment {

    // handles of recycle view and adatper
    private RecyclerView mPatientRecyclerView;
    private PatientAdapter mAdapter;


    // request for the menu starting activity;
    // we use the start activity for result, so a code is needed to check with the result
    private static final int REQUEST_CODE_PATIENT = 0;



    // Some initialization other than inflating the view
    // we do for the Bmob and menu here.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize Bmob
        Bmob.initialize(getContext(), "67893c5b8d8829a456b0fd2153a19d59");

        // for menu
        setHasOptionsMenu(true);
    }



    // inflate the recycler view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate the layout
        View view = inflater.inflate(R.layout.fragment_patient_list, container, false);
        // from the view, find the id from the view
        mPatientRecyclerView =  view.findViewById(R.id.patient_recycler_view);
        // set a layout manager
        mPatientRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //DividerItemDecoration mDivider = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        //mDivider.setDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(),R.color.colorZzrborder)));
        //mPatientRecyclerView.addItemDecoration(mDivider);


        mPatientRecyclerView.addItemDecoration(new TestDividerItemDecoration());

        getActivity().setTitle("患者列表");




        // for the adapter
        updateUI();


        return view;
    }


    // This is for: from the editing to the list
    // we have to update the list
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    // inflate the menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_patient_list, menu);
    }


    // acts of the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // entering the patient editing page
            case R.id.new_patient:

                Patient patient = new Patient();

                // using the PatientEditActivity delegation method
                Intent intent = PatientEditActivity.newIntent_patient(getActivity(), patient);

                startActivity(intent);


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void updateUI() {

        if (mAdapter == null){
            queryPatientlist();
        } else {
            queryPatientlist();
            mAdapter.notifyDataSetChanged();
        }

    }


    // holder class; also responsible for user action
    private class PatientHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {


        // to handle the patient in bind method
        private Patient mPatient;
        private TextView mPatientNameTextView;
        private TextView mDateTextView;

        // in constructor, we pass the view and pull out interested widgets
        // also set listeners
        public PatientHolder(LayoutInflater inflater, ViewGroup parent) {

            // inflater inflates a view with the layout list_item_patient
            super(inflater.inflate(R.layout.list_item_patient, parent, false));

            // for listener
            itemView.setOnClickListener(this);




            // itemview holds a reference to the entire View you passed into super(view)
            mPatientNameTextView =  itemView.findViewById(R.id.patient_name);
            mDateTextView =  itemView.findViewById(R.id.patient_date);
        }

        // data change, used by adaptor
        public void bind(Patient patient) {
            mPatient = patient;
            mPatientNameTextView.setText(mPatient.getPatientName());

            SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");

            //            mDateTextView.setText(mPatient.getDate().toString().substring(0,11));
            mDateTextView.setText(sf.format(mPatient.getDate()));
        }

        @Override
        public void onClick(View view) {

            toast("clicked" + mPatient.getPatientName());

            // create an intent to start another patient edit activity
            // Here we use the public method of patient activity to create the intent
            Intent intent = RecordListActivity.newIntent_record_list(getActivity(), mPatient);

            startActivity(intent);
        }
    }


    // adapter; note that the adapter is simple and dumb; just received data
    // real binding happens in holder
    private class PatientAdapter extends RecyclerView.Adapter<PatientHolder> {

        // Adapter is to hold the data, so it has to have a handler of data
        private List<Patient> mPatients;

        // in constructor, just holding the data as an argument
        public PatientAdapter(List<Patient> patients) {
            mPatients = patients;
        }

        // override some methods
        @Override
        public PatientHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // create an inflater
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            // create a new holder and return
            return new PatientHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(PatientHolder holder, int position) {
            // get a patient and give it to the holder
            Patient patient = mPatients.get(position);
            holder.bind(patient);
        }


        // Get the size of the list. The list is using the private handler
        @Override
        public int getItemCount() {
            return mPatients.size();
        }
    }





    private void queryPatientlist(){

        // query DB to show the recyclerview
        BmobQuery<Patient> query = new BmobQuery<>();

        query.order("-createdAt");

        query.findObjects(new FindListener<Patient>() {
            @Override
            public void done(List<Patient> pts, BmobException e) {
                if (e == null) {

                    // query result is pts; send it to the adapter
                    // The following two must be together here !!!
                    mAdapter = new PatientAdapter(pts);
                    mPatientRecyclerView.setAdapter(mAdapter);

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
