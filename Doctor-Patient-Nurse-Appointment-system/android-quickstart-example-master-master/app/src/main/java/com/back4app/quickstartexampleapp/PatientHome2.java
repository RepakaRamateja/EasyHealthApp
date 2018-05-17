package com.back4app.quickstartexampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.List;

public class PatientHome2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecycleViewPatientHome.CustomOnClickRecycleViewPatientHomeListener {

    Intent intent;
    public final static String EXTRA_selectedDoc = "com.back4app.quickstartexampleapp.DOCNAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_patient_home);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_patient_home2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeaderView = navigationView.getHeaderView(0);
        // ImageView navigationImageView = (ImageView) navHeaderView.findViewById(R.id.imageView_small_icon_navigation_view);
        //navigationImageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fair_housing));
        TextView navigationTextView = (TextView)navHeaderView.findViewById(R.id.textViewEmail);
        ParseUser curuser = ParseUser.getCurrentUser();
        navigationTextView.setText(curuser.getEmail().toString());
        getSupportFragmentManager().beginTransaction().replace(
                R.id.content_patient_home_navigation_relative_layout,
                RecycleViewPatientHome.newInstance(R.id.recycler_linear_layout_patient_home)).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_patient_home2);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_home2, menu);
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
          //  intent = new Intent(PatientHome2.this, PatientHome2.class);
          //  startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_appointments) {
            Log.d("start patient_home", "Start the Patient Appointments Activity");
            intent = new Intent(PatientHome2.this, DisplayPatientAppointments.class);
            startActivity(intent);

        } else if (id == R.id.nav_prescriptions) {
            Log.d("start patient_home", "Start the Patient Prescriptions Activity");
            intent = new Intent(PatientHome2.this, PatientPrescriptions.class);
            startActivity(intent);
        }  else if (id == R.id.nav_logout) {
            Log.d("Start logout action", "Go to Login Page");
            ParseUser curuser = ParseUser.getCurrentUser();
            curuser.logOutInBackground();
            intent = new Intent(PatientHome2.this, MainActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_patient_home2);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRecycleViewItemClicked(View v, ParseObject doctor) {
        /*String res;
        String res2 = adapter.getItem(i);
        TextView taskDescription = (TextView) view.findViewById(android.R.id.list);
        //res = taskDescription.getText().toString();
        Toast.makeText(this, res2 + " selected", Toast.LENGTH_LONG).show(); */

        Intent intent = new Intent(this, DisplayDoc.class);
        String docFullName = doctor.getString("Fname") + " " + doctor.getString("Lname");
        intent.putExtra(PatientHome2.EXTRA_selectedDoc, docFullName);
        startActivity(intent);
        /*Toast.makeText(this, "Inside PatientHome2 item click npw go to doctor detail display",
                Toast.LENGTH_LONG).show(); */
    }

    /* copied from PatientHome.java to add functionaltiy for search button
        public void searchFunc(View view){
        adapter.clear();
        TextView searchInput = (TextView) findViewById(R.id.search_input);
        String sInput = searchInput.getText().toString();
        String [] names = new String[2];
        names= sInput.split(" ");
        //names[0] = sInput.split("\\s+")[0];
        //String lname = sInput.split("\\s+")[1];
        //Log.d("post",names[0].trim());
        //Log.d("post",names[1].trim());
        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo("userType", "Doctor");
        //userQuery.whereEqualTo("Fname",names[0].trim());
        //userQuery.whereEqualTo("Lname",names[1].trim());
        userQuery.whereEqualTo("Speciality", sInput.trim());
        //userQuery.whereEqualTo("Speciality", "Orthopedic");

        userQuery.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> results, ParseException e) {
                // results has the list of users with a hometown team with a winning record
                int i = 0;
                Log.d("post", "inside parse " + String.valueOf(results.size()));
                for (ParseObject result : results) {
                    Log.d("post", "found");
                    // This does not require a network access.
                    //ParseObject user = result.getParseObject("userType");
                    String fname2 = result.getString("Fname");
                    String lname2 = result.getString("Lname");

                    //where.add(fname+" "+lname);
                    //values[i]=fname+" "+lname;
                    //i++;
                    adapter.setNotifyOnChange(true);
                    adapter.add(fname2 + " " + lname2);

                    Log.d("post", "retrieved a related post " + fname2 + " " + lname2);
                }
            }
        });

    }*/
}
