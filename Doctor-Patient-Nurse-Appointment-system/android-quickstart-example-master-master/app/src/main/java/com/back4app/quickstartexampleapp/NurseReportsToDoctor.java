package com.back4app.quickstartexampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.Toast;


import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/*This is used by the nurse during a new user account creation. The spinner is
* populated with the list of doctors the nurse which choose to report to. */
public class NurseReportsToDoctor extends AppCompatActivity  {
    Button continueBtn;
    Spinner sp;
    List<String> docList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nurse_reports_to_doctor);
        overridePendingTransition(R.anim.activity_in,R.anim.old_activity_out);
        docList = new ArrayList<String>();
        sp = findViewById(R.id.spinnerForDoctors);
        final ArrayAdapter<String> adp = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, docList);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Selected value is "+adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                Log.d("NurseReportsToDoctor", "onItemSelected: from spinner list is "+adapterView.getItemAtPosition(i).toString());
                sp.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo("userType", "Doctor");
        userQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    for(ParseUser user : objects) {
                        docList.add(user.get("Fname")+ " "+user.get("Lname"));
                    }
                    adp.notifyDataSetChanged();
                }
            }
        });
        sp.setAdapter(adp);
    }

    public void gotoNurseNavigation(View v) {
         final ParseUser user = ParseUser.getCurrentUser();
         if (sp.getSelectedItem() == null) {
             Log.d("NurseReportsToDoctor", "gotoNurseNavigation: selected Item is null");
             return ;
         }
         String getDocStr = sp.getSelectedItem().toString();
        if (getDocStr == null) {
            Toast.makeText(getApplicationContext(), "No item selected from the spinner", Toast.LENGTH_SHORT).show();
            return ;
         }
         String[] FullName = getDocStr.split(" ");
         String firstName = FullName[0];
         String lastName = FullName[1];
         ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        Log.d("NurseReportsToDoctor", "gotoNurseNavigation: FirstName = "+firstName+ " lastName = "+lastName);
        userQuery.whereEqualTo("Fname", firstName);
        //userQuery.whereEqualTo("Lname", lastName);
        try {
            userQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {
                        Log.d("NurseReportsToDoctor", "gotoNurseNavigation: Doctor Id retrieved is "+objects.get(0).getObjectId()+ " username is "+objects.get(0).getString("username"));
                        user.put("nurseReportsToDoc", objects.get(0).getObjectId());
                        try {
                            user.save();
                            Intent intent = new Intent(NurseReportsToDoctor.this, NurseNavigationDrawer.class);
                            startActivity(intent);
                        } catch (com.parse.ParseException ex) {
                            Log.d("NurseReportsToDoctor", "exception caught with user.save(): "+ex.getMessage());
                        }
                    } else {
                        Log.d("NurseReportsToDoctor", "done: exception caught userQuery.getFirstInBackground");
                    }
                }
            });


        } catch (Exception e) {
            Log.d("NurseReportsToDoctor", "gotoNurseNavigation: exception caught"+e.getMessage());
        }
    }
}
