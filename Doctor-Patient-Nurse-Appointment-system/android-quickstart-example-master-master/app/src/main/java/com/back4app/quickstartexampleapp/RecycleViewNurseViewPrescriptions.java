package com.back4app.quickstartexampleapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecycleViewNurseViewPrescriptions extends Fragment {
    RecyclerView mRecycleView;
    RecycleViewNursePrescriptionsViewAdapter mRecycleViewAdapter;
    List<ParseObject> mDataSet = new ArrayList<ParseObject>();
    List<ParseUser> PDataSet = new ArrayList<ParseUser>();

    public RecycleViewNurseViewPrescriptions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_recycle_view_nurse_view_prescriptions, container, false);
        mRecycleView = (RecyclerView)rootView.findViewById(R.id.recycle_view_nurse_view_prescriptions);
        customOnClickRvNpVListener =  (RecycleViewNurseViewPrescriptions.CustomOnClickRecycleViewNursePrescriptionsViewListener)rootView.getContext();


        updateData();
        mRecycleViewAdapter = new RecycleViewNursePrescriptionsViewAdapter(getContext(), mDataSet,PDataSet);
        mRecycleView.setAdapter(mRecycleViewAdapter);


        // use this setting to improve th performance if you know that changes
        // in content donot change the size of recycle view
        mRecycleView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        Log.d("RecycleViewPatientHome", "Oncreateview function new linearlayout manager");
        mRecycleView.setLayoutManager(mLayoutManager);

        mRecycleViewAdapter.setOnItemClickListener(new RecycleViewNursePrescriptionsViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick (View view, int position) {
                ParseObject doctor = (ParseObject) mDataSet.get(position);
                customOnClickRvNpVListener.onRecycleViewItemClicked(view,doctor);
            }

        });

        Log.d("RecycleViewPatientHome", "Oncreateview function new RecycleAdapter");
        itemAnimation();
        adapterAnimation();
        return rootView;
    }

    public void updateData(){

        ParseQuery<ParseObject> oQuery = ParseQuery.getQuery("Prescriptions");
        oQuery.whereEqualTo("docId", ParseUser.getCurrentUser().getString("nurseReportsToDoc"));
        oQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject result : objects) {
                        mDataSet.add(result);
                        ParseQuery<ParseUser> ouser=ParseUser.getQuery();
                        ouser.whereEqualTo("objectId",result.getString("patientId"));
                        try {
                            List<ParseUser> temp = ouser.find();
                            PDataSet.add(temp.get(0));
                        }
                        catch(Exception e2)
                        {
                            Log.d("NurseViewPrescriptions", " Exception caught in patient id "+e2.getMessage());
                        }
                        mRecycleViewAdapter.notifyDataSetChanged();
                        mRecycleViewAdapter.notifyItemInserted(mDataSet.size()-1);
                    }
                } else {
                    Log.d("NurseViewPrescriptions", "exception caught in prescriptio query based on doctor id "+e.getMessage());
                }
                Log.d("NurseViewPrescriptions", "done: result size is "+objects.size());
            }
        });
    }

    // TODO: Rename and change types and number of parameters
    public static RecycleViewNurseViewPrescriptions newInstance(int section_num) {
        RecycleViewNurseViewPrescriptions fragment = new RecycleViewNurseViewPrescriptions();
        Bundle args = new Bundle();
        return fragment;
    }

    public interface CustomOnClickRecycleViewNursePrescriptionsViewListener {
        public void onRecycleViewItemClicked(View v, ParseObject doctor);
    }
    private RecycleViewNurseViewPrescriptions.CustomOnClickRecycleViewNursePrescriptionsViewListener customOnClickRvNpVListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void adapterAnimation(){
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(mRecycleViewAdapter);
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(alphaInAnimationAdapter);
        scaleInAnimationAdapter.setDuration(300);
        mRecycleView.setAdapter(scaleInAnimationAdapter);

    }

    public void itemAnimation() {
        ScaleInAnimator scaleInAnimation = new ScaleInAnimator();
        scaleInAnimation.setInterpolator(new OvershootInterpolator());
        scaleInAnimation.setAddDuration(100);
        scaleInAnimation.setRemoveDuration(100);
        mRecycleView.setItemAnimator(scaleInAnimation);
    }

}
