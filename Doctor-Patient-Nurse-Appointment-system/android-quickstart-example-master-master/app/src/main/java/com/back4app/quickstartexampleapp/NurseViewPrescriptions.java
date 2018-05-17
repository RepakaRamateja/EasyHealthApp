package com.back4app.quickstartexampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.parse.ParseObject;
import android.widget.Toast;

/* This class displays the prescriptions that can be viewed by the nurse under the doctor
* she reports to. This can be useful later for her to send push notifcations about appointment
* changes / health tips to the patients. */
public class NurseViewPrescriptions extends AppCompatActivity implements RecycleViewNurseViewPrescriptions.CustomOnClickRecycleViewNursePrescriptionsViewListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_view_prescriptions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(
                R.id.content_nurse_view_prescriptions_relative_layout,
                RecycleViewNurseViewPrescriptions.newInstance(R.id.recycler_linear_layout_nurse_view_prescriptions)).commit();
    }

    @Override
    public void onRecycleViewItemClicked(View v, ParseObject doctor) {
        /*String res;
        String res2 = adapter.getItem(i);
        TextView taskDescription = (TextView) view.findViewById(android.R.id.list);
        //res = taskDescription.getText().toString();*/
        Toast.makeText(this,  "Recycle Item CLicked", Toast.LENGTH_LONG).show();

     //   Intent intent = new Intent(this, DisplayDoc.class);
     //   String docFullName = doctor.getString("Fname") + " " + doctor.getString("Lname");
     //   intent.putExtra(PatientHome2.EXTRA_selectedDoc, docFullName);
     //   startActivity(intent);
        /*Toast.makeText(this, "Inside PatientHome2 item click npw go to doctor detail display",
                Toast.LENGTH_LONG).show(); */
    }

}
