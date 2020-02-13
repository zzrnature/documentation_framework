// Since we have more fragment using a activity container,
// we let it be a abstract class.

package com.example.woundmanage;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    // this is where the concrete implementation varies
    protected abstract Fragment createFragment();

    //protected Fragment fragment;
    //protected PatientEditFragment fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // appear in almost every activiy
        // activity_fragment: it is a activity; it is for holding a fragment
        // This layout only has one element: framelayout with id = fragment_container
        // Even though in this app, we have two fragment using this container,
        // it seems that it will not bring about violation.
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();

        // fragment_container is the id of of
        //Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

}
