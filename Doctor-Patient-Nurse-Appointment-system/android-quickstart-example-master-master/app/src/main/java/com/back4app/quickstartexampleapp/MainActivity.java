package com.back4app.quickstartexampleapp;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.util.Log;

import com.parse.ParseInstallation;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

/* This page includes the signin of an already exisiting user or will
* redirect to the creation of a new user account. In the oncreate method
* initialise the Parse setting with the installtion ID, which can be unique to
* each Mobile device.*/
public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_EMAIL = "com.back4app.quickstartexampleapp.EMAIL";
    public final static String EXTRA_FNAME = "com.back4app.quickstartexampleapp.FNAME";
    public final static String EXTRA_LNAME = "com.back4app.quickstartexampleapp.LNAME";
    public final static String EXTRA_PASS = "com.back4app.quickstartexampleapp.PASS";
    public final static String EXTRA_UTYPE = "com.back4app.quickstartexampleapp.UTYPE";

    String userType;
    //EditText uname;
    EditText email;
    EditText pass;
    RadioButton btn1;
    RadioButton btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Save the current Installation to Back4App
        //ParseInstallation.getCurrentInstallation().saveInBackground();
        Parse.initialize(this);
        // Back4App's Parse setup
       /* Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("efK08GFEEpuz0IEViSEYXDAPcLUQn3e8QYbybeW2")
                .clientKey("4yQ26Qtl8zkzoUBKs7hCsh01l5c4zqlR6QZVnRF6")
                .server("https://parseapi.back4app.com/").build()
        );*/

        // This is the installation part
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("GCMSenderId", "1030057213987");
        installation.saveInBackground();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void userSignIn(View view) {

      //  uname = (EditText) findViewById(R.id.editText2);
        email = (EditText) findViewById(R.id.editText3);
        pass = (EditText) findViewById(R.id.editText);
        //Parse.initialize(this, "h8up9mUy8RC8uubGJJKzPDbXANhqMlZ8jt7diWUH", "CxYmqiAgyaHArADvGZUSn9w2peO9wfzkbgYYdtwe");

        ParseUser.logInInBackground(email.getText().toString(), pass.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    Context context = getApplicationContext();
                    CharSequence text = "Signed In!";
                    int duration = Toast.LENGTH_LONG;


                    //ParseObject uType = new ParseObject("userType");
                    //uType= parseUser.get("suerType");

                    String utype = parseUser.getString("userType");
                    Toast toast = Toast.makeText(context, utype, duration);
                    toast.show();

                    if(utype.equals("Doctor"))
                    {
                        Intent intent = new Intent(MainActivity.this, DoctorHome.class);
                        startActivity(intent);
                    }
                    else if(utype.equals("Patient"))
                    {
                       Intent intent = new Intent(MainActivity.this, PatientNavigationDrawer.class);
                        startActivity(intent);

                    } else if (utype.equals("Nurse")) {
                        Intent intent = new Intent(MainActivity.this, NurseNavigationDrawer.class);
                        startActivity(intent);
                    } else
                    {
                        Toast toast2 = Toast.makeText(context, "DID not find!!", duration);
                        toast2.show();
                    }


                    /*
                    ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
                    userQuery.whereEqualTo("userType", "Doctor");
                    userQuery.whereEqualTo("objectId", parseUser.getObjectId());
                    userQuery.findInBackground(new FindCallback<ParseUser>() {
                        void done(List<ParseUser> results, ParseException e) {
                            // results has the list of users with a hometown team with a winning record

                        }
                    });*/



                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Signed In Error!!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

    }

    public void moveToEmail(View view) {
        Intent intent = new Intent(this, EnterEmail.class);
        startActivity(intent);
    }




    public void userSignUp(View view)
    {
      // moveToEmail(view);
      //  uname = (EditText) findViewById(R.id.editText2);
        email = (EditText) findViewById(R.id.editText3);
        pass = (EditText) findViewById(R.id.editText);


        //Parse.initialize(this, "h8up9mUy8RC8uubGJJKzPDbXANhqMlZ8jt7diWUH", "CxYmqiAgyaHArADvGZUSn9w2peO9wfzkbgYYdtwe");

        ParseUser user = new ParseUser();
     //   user.setUsername(uname.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(pass.getText().toString());
        user.put("userType", userType.toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){

                    Context context = getApplicationContext();
                    CharSequence text = "Signed Up!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();


                }else{

                    String tag = "PARSE";
                    Log.e(tag,e.toString());
                //    Log.e(tag,uname.getText().toString());
                    Log.e(tag, email.getText().toString());
                    Log.e(tag, pass.getText().toString());
                    Context context = getApplicationContext();
                    //CharSequence text = "Error! Could not sign up !!";

                    CharSequence text = e.toString();
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
            }
        });
    }


   /* public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButton:
                if (checked)
                    userType = "Doctor";
                break;
            case R.id.radioButton2:
                if (checked)
                    userType = "Patient";
                break;
        }

    }*/


}
