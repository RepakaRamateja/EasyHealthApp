package com.back4app.quickstartexampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

/* This class includes fields where doctos writes prescriptions medicines,
* which is stored in the prescriptions table of Parse Server database.*/
public class WritePrescription extends AppCompatActivity {

    String pid;
    EditText pres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_prescription);
        Intent intent = getIntent();
        pid = intent.getStringExtra(DisplayPatient.EXTRA_pid);
        if(pid == null)
        {
            Log.d("posts","Caught you");
        }
        else {
            Log.d("posts", pid);
        }
        pres = (EditText) findViewById(R.id.editText);
    }

    public void sendPres(View view)
    {
        ParseUser curuser = ParseUser.getCurrentUser();
        ParseObject prescr = new ParseObject("Prescriptions");
        prescr.put("patientId",pid);
        prescr.put("docId",curuser.getObjectId());
        prescr.put("medicines", pres.getText().toString());
        prescr.saveEventually();
        Context context = getApplicationContext();
        CharSequence text = "Sent Prescription!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.write_presciption, menu);
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
