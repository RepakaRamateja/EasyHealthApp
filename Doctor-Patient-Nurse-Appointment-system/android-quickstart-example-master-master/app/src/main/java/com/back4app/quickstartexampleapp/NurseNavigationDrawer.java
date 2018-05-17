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

import com.parse.ParseUser;
import com.parse.ParseCloud;
import com.parse.FunctionCallback;
import com.parse.ParseException;
import java.util.Map;
import java.util.HashMap;

/* This is the page nurse will view after a successful  login with
* the credentials. This contains Nurse Home, Send Notifications or view prescriptions
* activity */
public class NurseNavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                R.id.content_nurse_navigation_relative_layout,
                InitFragmentNurseNavigationDrawer.newInstance(R.id.init_fragment_nurse_navigation_drawer_relative_layout)).commit();
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
        getMenuInflater().inflate(R.menu.nurse_navigation_drawer, menu);
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

        if (id == R.id.nav_nurse_home) {
            intent = new Intent(NurseNavigationDrawer.this, NurseHome.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_nurse_prescription) {
            intent = new Intent(NurseNavigationDrawer.this, NurseViewPrescriptions.class);
            startActivity(intent);
        } else if (id == R.id.nav_nurse_send_tips) {
            callSendPushFunc();
        } else if (id == R.id.nav_logout) {
            Log.d("Start logout action", "Go to Login Page");
            ParseUser curuser = ParseUser.getCurrentUser();
            curuser.logOutInBackground();
            intent = new Intent(NurseNavigationDrawer.this, MainActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void callSendPushFunc() {
        // Use this map to send parameters to your Cloud Code function
        // Just push the parameters you want into it

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("channelId", "user_"+ParseUser.getCurrentUser().get("nurseReportsToDoc").toString());
        parameters.put("nurseMessage", "Please send this urgent to patient");
       // This calls the function in the Cloud Code
        ParseCloud.callFunctionInBackground("pushsample", parameters, new FunctionCallback<Map<String, Object>>() {
            @Override
            public void done(Map<String, Object> mapObject, ParseException e) {
                if (e == null) {
                    Log.d("NurseNavigationDrawer", "done: push sent successfully "+mapObject);
                }
                else {
                    // Something went wrong
                    Log.d("NurseNavigationDrawer", "error in sending the push"+e.getMessage());
                }
            }
        });
    }

}
