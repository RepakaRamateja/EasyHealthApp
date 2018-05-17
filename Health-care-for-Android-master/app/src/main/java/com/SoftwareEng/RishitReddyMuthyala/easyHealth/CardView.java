package com.SoftwareEng.RishitReddyMuthyala.easyHealth;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* 
 * This class is for switching to Card View Activity.
 */
public class CardView extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyRecyclerViewAdapter adapter;
    HospitalData hospitalData;
    List<Map<String, ?>> hospitalList;
    Hospital hospital;
    CollapsingToolbarLayout collapsingToolbarLayout;

    Boolean sortLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    String query;
    TextView tv;
    MyFirebaseRecylerAdapter myFirebaseRecylerAdapter;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /* 
     * This method gets called upon the creation of this card view activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        hospitalData=new HospitalData();
        recyclerView = (RecyclerView) findViewById(R.id.cardView);
        //recyclerView.setHasFixedSize(true);
        hospitalList = hospitalData.getHospitalList();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        tv=(TextView) findViewById(R.id.text);

        DatabaseReference childRef= FirebaseDatabase.getInstance().getReference().child("hospitaldata").getRef();

        myFirebaseRecylerAdapter=new MyFirebaseRecylerAdapter(Hospital.class,R.layout.fragment_card_list,MyFirebaseRecylerAdapter.HospitalViewHolder.class,childRef,getApplicationContext());


        //adapter = new MyRecyclerViewAdapter(getApplicationContext(), moviesList);
        recyclerView.setAdapter(myFirebaseRecylerAdapter);

        if(hospitalData.getSize()==0)
        {
           hospitalData.setAdapter(myFirebaseRecylerAdapter);
            hospitalData.setContext(getApplicationContext());
            hospitalData.initializeDataFromCloud();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapse_toolbar);

            myFirebaseRecylerAdapter.setRecyclerClickListener(new RecyclerClickListener() {
           @Override
           public void onItemClick(final int position) {
               HashMap<String, ?>hospital=(HashMap<String, ?>) hospitalData.getItem(position);
               String id=(String) hospital.get("id");
               DatabaseReference ref=hospitalData.getFireBaseRef();

               ref.child(id).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       HashMap<String, String>hospital=(HashMap<String, String>) dataSnapshot.getValue();
                       getSupportFragmentManager().beginTransaction().replace(R.id.activity_card_view, HospitalDetailFragment.newInstance((HashMap) hospitalList.get(position))).addToBackStack("null").commit();

                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                       Log.d("My Test","The Read failed: "+databaseError.getMessage());
                   }
               });
           }

           @Override
           public void onItemLongClick(View v, int position) {

           }

                @Override
                public void onOverFlowMenuClick(View v, int position) {

                }


            });




        itemAnimation();
        adapterAnimation();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /* 
     * This method is for item animation.
     */
    public void itemAnimation() {
        //add item animation from wassbeef library
        //recyclerView.setItemAnimator();
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(2000);
        defaultItemAnimator.setRemoveDuration(2000);
        recyclerView.setItemAnimator(defaultItemAnimator);
        defaultItemAnimator.setChangeDuration(1000);
        defaultItemAnimator.getRemoveDuration();

    }

    public void adapterAnimation() {
        //choose one animation from wassbeef and pass the adapter object into the new animator

        //recyclerView.setAdapter();

    }


    /* 
     * This method gets called up on the creation of options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);

        SearchView searchView=(SearchView)menu.findItem(R.id.search).getActionView();

        if(searchView!=null) {

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


                @Override
                public boolean onQueryTextSubmit(String query) {

                    int position = hospitalData.findFirst(query);
                    Log.d("pos", "onQueryTextSubmit: "+position);
                    if (position >= 0) {
                        //recyclerView.scrollToPosition(position);
                        recyclerView.scrollToPosition(position);
                        //recyclerView.getFilterTouchesWhenObscured();
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }

            super.onCreateOptionsMenu(menu);
        return true;

        }


    /* 
     * This method gets called upon the options item selction.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.search:
                break;

            case R.id.share:
                return false;

            default:
                break;


        }

        return true;


    }




    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("CardView Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    public class ActionBarCallBack implements ActionMode.Callback
    {



        int position;

        public ActionBarCallBack(int position)
        {

            this.position=position;

        }


        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_menu,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            HashMap hm=(HashMap) hospitalData.getItem(position);
            mode.setTitle((String) hm.get("name"));
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id=item.getItemId();

            switch(id)
            {
                case R.id.delcontext:
                   hospitalList.remove(position);
                    adapter.notifyItemRemoved(position);
                    mode.finish();
                    break;

                case R.id.copycontext:
                    hospitalList.add(position + 1, (HashMap) (hospitalData.getItem(position)).clone());
                    adapter.notifyItemInserted(position + 1);
                    mode.finish();
                    break;



                default:break;


            }




            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }
}

