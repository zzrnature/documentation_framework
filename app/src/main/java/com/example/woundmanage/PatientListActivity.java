package com.example.woundmanage;

import androidx.fragment.app.Fragment;


public class PatientListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PatientListFragment();
    }
}