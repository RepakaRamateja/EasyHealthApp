package com.back4app.quickstartexampleapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.HashMap;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecycleViewPatientHome.CustomOnClickRecycleViewPatientHomeListener} interface
 * to handle interaction events.
 * Use the {@link RecycleViewPatientHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecycleViewPatientHome extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView mRecycleView;
    RecycleViewPatientHomeAdapter mRecycleViewAdapter;
    List<ParseObject> mDataSet = new ArrayList<ParseObject>();


   // private OnFragmentInteractionListener mListener;

    public void updateData(){

        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo("userType", "Doctor");
        userQuery.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> results, ParseException e) {
                // results has the list of users with a hometown team with a winning record
                int i=0;
                for (ParseObject result : results) {
                    // This does not require a network access.
                    //ParseObject user = result.getParseObject("userType");
                    String fname = result.getString("Fname");
                    String lname = result.getString("Lname");
                    mDataSet.add(result);
                   // Log.d("RecycleViewPatientHome", "current size after adding data is: "+ mDataSet.size());
                    //where.add(fname+" "+lname);
                    //values[i]=fname+" "+lname;
                    //i++;
                    // adapter.setNotifyOnChange(true);
                    // adapter.add(fname+" "+lname);
                    mRecycleViewAdapter.notifyDataSetChanged();
                    mRecycleViewAdapter.notifyItemInserted(mDataSet.size()-1);
                    Log.d("post", "retrieved a related post "+fname+" "+lname);
                }
              //  mDataSet.clear();
                Log.d("RecycleViewPatientHome", "done: results size is  "+results.size());
              //  mDataSet.addAll(results);
              //  mRecycleViewAdapter.notifyDataSetChanged();;
            }
        });
    }

    public RecycleViewPatientHome() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RecycleViewPatientHome newInstance(int section_num) {
        RecycleViewPatientHome fragment = new RecycleViewPatientHome();
        Bundle args = new Bundle();
        return fragment;
    }

    public interface CustomOnClickRecycleViewPatientHomeListener {
        public void onRecycleViewItemClicked(View v, ParseObject doctor);
    }
    private CustomOnClickRecycleViewPatientHomeListener customOnClickRvPhListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_recycle_view_patient_home, container, false);
        customOnClickRvPhListener = (CustomOnClickRecycleViewPatientHomeListener)rootView.getContext();
        mRecycleView = (RecyclerView)rootView.findViewById(R.id.recycle_view_patient_home);

        // populate mDataset before sending
        updateData();
        Log.d("RecycleViewPatientHome", "onCreateView: size of dataset is "+ mDataSet.size());
        mRecycleViewAdapter = new RecycleViewPatientHomeAdapter(getContext(), mDataSet);
        mRecycleView.setAdapter(mRecycleViewAdapter);

        // use this setting to improve th performance if you know that changes
        // in content donot change the size of recycle view
        mRecycleView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        Log.d("RecycleViewPatientHome", "Oncreateview function new linearlayout manager");
        mRecycleView.setLayoutManager(mLayoutManager);

        mRecycleViewAdapter.setOnItemClickListener(new RecycleViewPatientHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick (View view, int position) {
                ParseObject doctor = (ParseObject) mDataSet.get(position);
                customOnClickRvPhListener.onRecycleViewItemClicked(view,doctor);
            }

        });

        Log.d("RecycleViewPatientHome", "Oncreateview function new RecycleAdapter");
        // fancy animation
        // fancyAnimation();
        // animation for adapter
        // adapterAnimation();
        //defaultAnimation();
        itemAnimation();
        adapterAnimation();
        return rootView;
     }

     @Override
     public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
         SearchView search = null;
         if (menu.findItem(R.id.recycle_fragment_actionview) == null) {
             inflater.inflate(R.menu.recycle_fragment_actionview, menu);
             Log.d("RecycleViewPatientHome", "onCreateOptionsMenu: inflated with search view to filter based on speciality");
         }
         MenuItem menuItem = menu.findItem(R.id.recycle_fragment_searchitem);
         if (menuItem != null) {
             search = (SearchView)menuItem.getActionView();
         }
         if (search != null) {
             search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                 @Override
                 public boolean onQueryTextSubmit(String query) {
                     return true;
                 }
                 @Override
                 public boolean onQueryTextChange(String query) {
                     return true;
                 }
             });
         };
         super.onCreateOptionsMenu(menu, inflater);
     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.recycle_fragment_searchitem) {
            TransitionManager.beginDelayedTransition((ViewGroup) getActivity().findViewById(R.id.toolbar_patient_home));
            MenuItemCompat.expandActionView(item);
            return true;
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      /*  if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

      @Override
      public void onDetach() {
        super.onDetach();
  //      mListener = null;
      }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/


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
