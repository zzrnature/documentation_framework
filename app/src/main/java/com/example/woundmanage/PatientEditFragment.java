// Fragment controlling the patient transaction

package com.example.woundmanage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;



public class PatientEditFragment extends Fragment {



    //----------------------------------------------------------------
    // for two args: the patient passed in and the one return back
    // keys of arguments
    //----------------------------------------------------------------
    private static final String ARG_PATIENT = "patient";

    // these two hold the arguments from fragment's activity
    private Patient mPatient; // hold a patient object




    //----------------------------------------------------------------
    // widgets handle
    //----------------------------------------------------------------
    private EditText mPatientNameField;
    private EditText mPatientInfor1Field;
    private EditText mPatientInfor2Field;
    private Button mTestSaveButton;

//    private Button mDateButton;
//    private CheckBox mSolvedCheckBox;


    //----------------------------------------------------------------
    // Public fragment creation method.
    // Used by host activity; create fragment with args
    // If we create the fragment in activity directly with new PatientEditFragment,
    // we have to know the fragment's argument keys, e.g. ARG_PATIENT, etc.
    //----------------------------------------------------------------
    //public static PatientEditFragment newInstance(Patient patient, String backPatientString) {
    public static PatientEditFragment newInstance(Patient patient) {
        // create Bundle object and add two arguments
        Bundle args = new Bundle();

        args.putSerializable(ARG_PATIENT, patient);

        // create fragment
        PatientEditFragment fragment = new PatientEditFragment();
        fragment.setArguments(args);

        return fragment;
    }


    //----------------------------------------------------------------
    // Some initialization
    // Note the layout is not inflated here.
    // Obtaining the arg using getArguments passed by the activity
    //----------------------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bmob.initialize(getContext(), "67893c5b8d8829a456b0fd2153a19d59");

        // get arguments from host activity
        mPatient = (Patient) getArguments().getSerializable(ARG_PATIENT);

    }


    //----------------------------------------------------------------
    // Inflate the view here
    // Also wire up the widgets
    //----------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate the fragment's layout
        View v = inflater.inflate(R.layout.fragment_patient, container, false);


        getActivity().setTitle("添加患者");


        // zzr added button
        // For the saving of the editing patient
        // Set the result for the list view; return the edited patient
        mTestSaveButton = v.findViewById(R.id.patient_save);

        mTestSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // insert it to the DB
                mPatient.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if(e==null){
                            toast("添加patient成功，返回objectId为："+objectId);
                            mTestSaveButton.setEnabled(false); // make the buttion unavailable

                        }else{
                            toast("创建patient失败：" + e.getMessage());
                        }
                    }
                });

                //getActivity().onBackPressed();

            }
        });


        //------------------------------------------
        // patient name editing
        //------------------------------------------
        // text field
        mPatientNameField =  v.findViewById(R.id.patient_name); // find the widget

        // add listener
        mPatientNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            // Change patient name
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPatient.setPatientName(s.toString());

            }
            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });



        //------------------------------------------
        // patient infor1 editing
        //------------------------------------------
        // text field
        mPatientInfor1Field = v.findViewById(R.id.patient_info1);

        // add listener
        mPatientInfor1Field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            // Change patient_info1 name
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPatient.setPatientInfor1(s.toString());

            }
            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });


        //------------------------------------------
        // patient infor2 editing
        //------------------------------------------
        // text field
        mPatientInfor2Field = v.findViewById(R.id.patient_info2);

        // add listener
        mPatientInfor2Field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            // Change patient_info2 name
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPatient.setPatientInfor2(s.toString());

            }
            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });


        // button
        // find the button; patient_date is the button's id
        // mDateButton = (Button) v.findViewById(R.id.patient_date);

        // show the patient's date on the button
//        mDateButton.setText(mPatient.getDate().toString());

        // ensures it will not respond to the user pressing
//        mDateButton.setEnabled(false);



        // checkbox listener
//        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.patient_solved);
//        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mPatient.setSolved(isChecked); // set patient data
//            }
//        });
        return v;
    }

    public void toast(String s){
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }


}













