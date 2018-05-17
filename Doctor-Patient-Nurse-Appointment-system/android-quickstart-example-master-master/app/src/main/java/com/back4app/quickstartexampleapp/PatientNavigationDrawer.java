package com.back4app.quickstartexampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/*This is the home page patient will be able to view when he logins successfully
* with his credentials. This can further redirect to patient Home, appointments, prescriptions
* or the Blood Bank information application. */
public class PatientNavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;

    public void subscribePushNotification() {
        final Set<String> subchannels = new LinkedHashSet<String>();
        ParseQuery<ParseObject> oQuery = ParseQuery.getQuery("Prescriptions");
        oQuery.whereEqualTo("patientId", ParseUser.getCurrentUser().getObjectId());
        oQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject object : objects) {
                        String docId = "user_" + object.getString("docId");
                        subchannels.add(docId);
                    }
                    ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                e.printStackTrace();
                                return;
                            } else {
                                for (final String channel: subchannels) {
                                   ParsePush.subscribeInBackground(channel, new SaveCallback() {
                                       @Override
                                       public void done(ParseException e) {
                                           Log.d("PatientNavigationDrawer","Subscribed to channel "+  channel);
                                           if (e != null) {
                                               e.printStackTrace();
                                           }
                                       }
                                   });
                                }
                            }
                        }
                    });
                } else {
                    Log.d("PatientNavigationDrawer", "exception caught in subscribePushNotification "
                            +e.getMessage().toString());
                }
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        subscribePushNotification();
        View navHeaderView = navigationView.getHeaderView(0);
        // ImageView navigationImageView = (ImageView) navHeaderView.findViewById(R.id.imageView_small_icon_navigation_view);
        //navigationImageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fair_housing));
        TextView navigationTextView = (TextView)navHeaderView.findViewById(R.id.textViewEmail);
        ParseUser curuser = ParseUser.getCurrentUser();
        navigationTextView.setText(curuser.getEmail().toString());
        getSupportFragmentManager().beginTransaction().replace(
                R.id.content_patient_navigation_relative_layout,
                InitFragmentPatientNavigationDrawer.newInstance(R.id.init_fragment_patient_navigation_drawer_relative_layout)).commit();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Log.d("start patient_home", "Start the Patient Home Activity");
            intent = new Intent(PatientNavigationDrawer.this, PatientHome2.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_appointments) {
            Log.d("start patient_home", "Start the Patient Appointments Activity");
            intent = new Intent(PatientNavigationDrawer.this, DisplayPatientAppointments.class);
            startActivity(intent);

        } else if (id == R.id.nav_prescriptions) {
            Log.d("start patient_home", "Start the Patient Prescriptions Activity");
            intent = new Intent(PatientNavigationDrawer.this, PatientPrescriptions.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_bloodbanks) {
            Log.d("start patient_home", "Start the Patient Prescriptions Activity");
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.assignment9.manojkulkarni.firebasefinal");
            if (launchIntent != null) {
                startActivity(launchIntent);//null pointer check in case package name was not found
            }

        }else if (id == R.id.nav_logout) {
            Log.d("Start logout action", "Go to Login Page");
            ParseUser curuser = ParseUser.getCurrentUser();
            curuser.logOutInBackground();
            intent = new Intent(PatientNavigationDrawer.this, MainActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
