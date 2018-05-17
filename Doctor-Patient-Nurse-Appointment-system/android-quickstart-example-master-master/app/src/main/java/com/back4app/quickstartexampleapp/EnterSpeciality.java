package com.back4app.quickstartexampleapp;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.parse.ParseUser;

/* This is used by the doctor class to store his specialisation information which is added as
* an extra column in the user database table */
public class EnterSpeciality extends AppCompatActivity {

    EditText t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_speciality);
        t1 = (EditText) findViewById(R.id.editText);

    }

    public void sendSpec(View view)
    {
        ParseUser curuser = ParseUser.getCurrentUser();
        curuser.put("Speciality", t1.getText().toString());
        curuser.saveEventually();
        Intent intent = new Intent(this, EnterHospitalDet.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.enter_speciality, menu);
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
