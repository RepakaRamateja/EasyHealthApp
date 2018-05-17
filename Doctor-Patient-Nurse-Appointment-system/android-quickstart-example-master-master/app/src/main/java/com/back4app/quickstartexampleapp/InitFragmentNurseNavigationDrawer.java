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


/**
 * A simple {@link Fragment} subclass.
 */
public class InitFragmentNurseNavigationDrawer extends Fragment {

    Button nursehalthtipsButton;
    Button nursehomeButton;
    Button nurseviewprescriptionsButton;

    public InitFragmentNurseNavigationDrawer() {
        // Required empty public constructor
    }

    private static final String ARG_SECTIONNUMBER = "section_1";
    public static InitFragmentNurseNavigationDrawer newInstance(int section_number)
    {
        InitFragmentNurseNavigationDrawer fragment = new InitFragmentNurseNavigationDrawer();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTIONNUMBER, section_number);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView =  inflater.inflate(R.layout.fragment_init_fragment_nurse_navigation_drawer, container, false);
        nursehalthtipsButton = (Button)rootView.findViewById(R.id.nursehealthtipsbutton);
        nurseviewprescriptionsButton = (Button)rootView.findViewById(R.id.nurseprescriptionsviewbutton);
        nursehomeButton = (Button)rootView.findViewById(R.id.nursehomebutton);
        nursehomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(getActivity(), NurseHome.class);
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

        nurseviewprescriptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(getActivity(), NurseViewPrescriptions.class);
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

}
