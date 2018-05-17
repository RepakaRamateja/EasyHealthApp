package com.back4app.quickstartexampleapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by jashwanthreddy on 11/24/17.
 */

public class InitFragmentPatientNavigationDrawer extends Fragment {
    private ArrayList<String> mData;
    private static final String SHOWCASE_ID = "Sequence 1";
    Button patientappointmentsButton;
    Button patienthomeButton;
    Button patientPrescriptionsButton;
    private static final String ARG_SECTIONNUMBER = "section_1";
    public static InitFragmentPatientNavigationDrawer newInstance(int section_number)
    {
        InitFragmentPatientNavigationDrawer fragment = new InitFragmentPatientNavigationDrawer();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTIONNUMBER, section_number);
        fragment.setArguments(args);
        return fragment;
    }
    public InitFragmentPatientNavigationDrawer() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //   setHasOptionsMenu(true);
        if (getArguments() != null)
        {
            int sectionNumber  = (Integer) getArguments().getSerializable(ARG_SECTIONNUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        final View rootView = inflater.inflate(R.layout.init_patient_navigation_drawer_fragment, container, false);
        patientappointmentsButton = (Button)rootView.findViewById(R.id.patientappointmentsbutton);
        patienthomeButton = (Button)rootView.findViewById(R.id.patienthomebutton);
        patientPrescriptionsButton = (Button)rootView.findViewById(R.id.patientPrescriptionsbutton);

        patientappointmentsButton.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), DisplayPatientAppointments.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(getActivity(), rootView, "testAnimation");
                    getActivity().startActivity(intent, options.toBundle());
                }
                else {
                    startActivity(intent);
                }
            }
        });


        patienthomeButton.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), PatientHome2.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(getActivity(), rootView, "testAnimation");
                    getActivity().startActivity(intent, options.toBundle());
                }
                else {
                    startActivity(intent);
                }
            }
        });

        patientPrescriptionsButton.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), PatientPrescriptions.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(getActivity(), rootView, "testAnimation");
                    getActivity().startActivity(intent, options.toBundle());
                }
                else {
                    startActivity(intent);
                }
            }
        });


        return rootView;
    }
}
