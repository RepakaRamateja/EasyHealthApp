package com.back4app.quickstartexampleapp;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.LinkedList;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class DoctorHome extends ListActivity implements AdapterView.OnItemClickListener {
    public final static String EXTRA_patientid2 = "com.back4app.quickstartexampleapp.PID2";
    ListView mlistview;
    ArrayAdapter<String> adapter;
    ArrayList<String> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        mlistview = (ListView)findViewById(android.R.id.list);

        entries = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, entries);

        mlistview.setAdapter(adapter);
        mlistview.setOnItemClickListener(this);
        // Defines the channels this should listen to
        final LinkedList<String> channels = new LinkedList<String>();
        final ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        // channels = installation.get("channels");
        // Adds the Firebase Sender Key
        installation.put("GCMSenderId", "1030057213987");
        ParseQuery<ParseUser> curquery = ParseUser.getQuery();
        curquery.whereEqualTo("userType", "Doctor");
        /* Create unique channel for each doctor. The patient will use this channel
        to subscribe to updates from the doctor . These are stored in an array of strings
        in the installation table */
        curquery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
               if (e == null) {
                   for(ParseUser object : objects) {
                       channels.add("user_"+object.getObjectId());
                   }
                   installation.addAllUnique("channels", channels);
                   try {
                       installation.save();
                   } catch (Exception e2) {
                       Log.d("DoctorHome", "done: exception caught in adding installations "+ e2.getMessage().toString());
                   }
               } else {
                   Log.d("DoctorHome", "exception caught in getting doctors: " + e.getMessage().toString());
               }
            }
        });


        updateData();
    }

    public void updateData() {

        ParseUser cuser = ParseUser.getCurrentUser();

        final String[] response = new String[5];
        ParseQuery<ParseObject> oQuery = ParseQuery.getQuery("Appointments");
        oQuery.whereEqualTo("docId", cuser.getObjectId());
        oQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> results, ParseException e) {
                // results has the list of users with a hometown team with a winning record
                int i = 0;
                for (ParseObject result : results) {
                    // This does not require a network access.
                    Log.d("count",String.valueOf(results.size()));
                    response[0] = result.getString("reason");
                    response[1] = result.getString("time");
                    response[2] = result.getString("date");
                    response[3] = result.getString("patientId");
                    ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
                    userQuery.whereEqualTo("objectId", response[3]);
                    userQuery.findInBackground(new FindCallback<ParseUser>() {
                        public void done(List<ParseUser> results, ParseException e) {
                            // results has the list of users with a hometown team with a winning record
                            int i=0;
                            Log.d("count",String.valueOf(results.size()));
                            for (ParseObject result2 : results) {
                                // This does not require a network access.
                                //ParseObject user = result.getParseObject("userType");
                                String fname = result2.getString("Fname");
                                String lname = result2.getString("Lname");

                                //where.add(fname+" "+lname);
                                //values[i]=fname+" "+lname;
                                //i++;
                                response[4]=fname+" "+lname;
                                adapter.setNotifyOnChange(true);
                                adapter.add(response[3]+" Appointment "+"with "+response[4]+" at "+response[1]+" on "+response[2]);
                                Log.d("post", "retrieved a related post " + fname + " " + lname);
                            }
                        }
                    });
                    //ParseObject user = result.getParseObject("userType");


                    //where.add(fname+" "+lname);
                    //values[i]=fname+" "+lname;
                    //i++;
                    //adapter.setNotifyOnChange(true);
                    //adapter.add("Appointment "+"with "+response[4]+);

                    //Log.d("post", "retrieved a related post " + fname + " " + lname);
                }
            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String res;
        final String res2 = adapter.getItem(i);
        TextView taskDescription = (TextView) view.findViewById(android.R.id.list);
        //res = taskDescription.getText().toString();
        Toast.makeText(this, res2 + " selected", Toast.LENGTH_LONG).show();
        ParseQuery<ParseUser> userQ = ParseUser.getQuery();
        Log.d("post","patid is "+res2.split(" ")[0].trim());
        /*userQ.whereEqualTo("objectId",res2.split(" ")[0].trim());
        //userQ.whereEqualTo("Lname",res2.split(" ")[3]);
        final String[] response = new String[1];
        userQ.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> results, ParseException e) {
                // results has the list of users with a hometown team with a winning record
                int i=0;
                for (ParseObject result : results) {
                    // This does not require a network access.
                    //ParseObject user = result.getParseObject("userType");
                    response[0] = result.getString("objectId");
                 }
                if(response[0] == null)
                {
                    Log.d("posts", "AGAIN CAUGHT YOU !!");
                }
                else
                {
                    Log.d("posts",response[0]);
                }
                Intent intent = new Intent(DoctorHome.this, DisplayPatient.class);
                intent.putExtra(DoctorHome.EXTRA_patientid2, response[0]);
                startActivity(intent);

            }
        });*/
        //Intent intent = new Intent(this, DisplayDoc.class);
        //intent.putExtra(PatientHome.EXTRA_selectedDoc, res2);
        //startActivity(intent);
        /* Transition to Display Patient Activity */
        Intent intent = new Intent(DoctorHome.this, DisplayPatient.class);
        intent.putExtra(DoctorHome.EXTRA_patientid2, res2.split(" ")[0].trim());
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
