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
 * This class is for switching to Card View 1 Activity.
 */
public class CardView1 extends AppCompatActivity {

    RecyclerView recyclerView1;
    RecyclerView.LayoutManager layoutManager1;
    MyRecyclerViewAdapter adapter1;
    DonorData donorData;
    List<Map<String, ?>> donorList;
    Donor donor;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Boolean sortLayout1;
    Toolbar toolbar1;
    ActionBar actionBar1;
    String query1;
    TextView tv1;
    MyFirebaseRecyclerAdapter1 myFirebaseRecylerAdapter1;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /* 
     * This method gets called upon the creation of the card view activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view1);
        donorData=new DonorData();
        recyclerView1 = (RecyclerView) findViewById(R.id.cardView1);
        //recyclerView.setHasFixedSize(true);
        donorList = donorData.getDonorList();
        layoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerView1.setLayoutManager(layoutManager1);
        tv1=(TextView) findViewById(R.id.text1);

        DatabaseReference childRef= FirebaseDatabase.getInstance().getReference().child("donordata").getRef();

        myFirebaseRecylerAdapter1=new MyFirebaseRecyclerAdapter1(Donor.class,R.layout.fragment_card_list1,MyFirebaseRecyclerAdapter1.DonorViewHolder.class,childRef,getApplicationContext());


        //adapter = new MyRecyclerViewAdapter(getApplicationContext(), moviesList);
        recyclerView1.setAdapter(myFirebaseRecylerAdapter1);

        if(donorData.getSize()==0)
        {
            donorData.setAdapter(myFirebaseRecylerAdapter1);
            donorData.setContext(getApplicationContext());
            donorData.initializeDataFromCloud();
        }

        toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapse_toolbar1);

        myFirebaseRecylerAdapter1.setRecyclerClickListener(new RecyclerClickListener() {
            @Override
            public void onItemClick(final int position) {
                HashMap<String, ?>donor=(HashMap<String, ?>) donorData.getItem(position);

                String id=(String) donor.get("id");
                DatabaseReference ref=donorData.getFireBaseRef();


                ref.child(id).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String, String>donor=(HashMap<String, String>) dataSnapshot.getValue();
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_card_view1, DonorDetailFragment.newInstance((HashMap) donorList.get(position))).addToBackStack("null").commit();

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
            public void onOverFlowMenuClick(View v, final int position) {

                /*PopupMenu popupMenu = new PopupMenu(CardView1.this, v);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        HashMap hospital;
                        switch (item.getItemId()) {

                            case R.id.copycontext:
                                hospital=(HashMap) ((HashMap) hospitalData.getItem(position)).clone();
                                hospital.put("id",((String)hospital.get("id")+"_new"));
                                hospitalData.addItemToServer(hospital);
                                return true;

                            case R.id.delcontext:
                                hospital=(HashMap) ((HashMap) hospitalData.getItem(position)).clone();
                                hospitalData.removeItemFromServer(hospital);
                                return true;



                            default:
                                return false;
                        }
                    }
                });

                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.contextual_menu, popupMenu.getMenu());
                popupMenu.show();*/

            }
        });




        itemAnimation();
        adapterAnimation();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void itemAnimation() {
        //add item animation from wassbeef library
        //recyclerView.setItemAnimator();
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(2000);
        defaultItemAnimator.setRemoveDuration(2000);
        recyclerView1.setItemAnimator(defaultItemAnimator);
        defaultItemAnimator.setChangeDuration(1000);
        defaultItemAnimator.getRemoveDuration();

    }

    public void adapterAnimation() {
        //choose one animation from wassbeef and pass the adapter object into the new animator

        //recyclerView.setAdapter();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);

        SearchView searchView=(SearchView)menu.findItem(R.id.search).getActionView();

        if(searchView!=null) {

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


                @Override
                public boolean onQueryTextSubmit(String query) {

                    int position = donorData.findFirst(query);
                    Log.d("pos", "onQueryTextSubmit: "+position);
                    if (position >= 0) {
                        //recyclerView.scrollToPosition(position);
                        recyclerView1.smoothScrollToPosition(position);
                        recyclerView1.getFilterTouchesWhenObscured();
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


    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {


            case R.id.search:
                break;



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

            HashMap hm=(HashMap) donorData.getItem(position);
            mode.setTitle((String) hm.get("contact"));
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
           /* int id=item.getItemId();

            switch(id)
            {
                case R.id.delcontext:
                    donorList.remove(position);
                    adapter1.notifyItemRemoved(position);
                    mode.finish();
                    break;

                case R.id.copycontext:
                    hospitalList.add(position + 1, (HashMap) (hospitalData.getItem(position)).clone());
                    adapter.notifyItemInserted(position + 1);
                    mode.finish();
                    break;



                default:break;*/







            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }
}

