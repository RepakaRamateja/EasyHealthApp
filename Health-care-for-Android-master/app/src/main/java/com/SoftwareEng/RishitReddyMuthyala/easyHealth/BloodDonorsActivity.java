package com.SoftwareEng.RishitReddyMuthyala.easyHealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

//Class to fetch the details of the donors
public class BloodDonorsActivity extends AppCompatActivity {

    EditText firstName,lastName,contact,address,bloodGroup;
    String fName,lName,con,addr,bGrp;
    Button createAccount;
    DatabaseReference mRef2;
   // final FirebaseDatabase donorDB=FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donors);

        firstName=(EditText)findViewById(R.id.first_name);
        lastName=(EditText)findViewById(R.id.last_name);
        contact=(EditText)findViewById(R.id.contact);
        address=(EditText)findViewById(R.id.address);
        bloodGroup=(EditText)findViewById(R.id.blood_group);
        createAccount=(Button)findViewById(R.id.account);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onAccountCreate();
            }
        });
    }

    /* 
     * This method gets called upon the creation of an account.
     */
    public void onAccountCreate()
    {
        //Object ne=new Object();
        Log.d("stupid", "onAccountCreate: ");
        fName=firstName.getText().toString();
        lName=lastName.getText().toString();
        con=contact.getText().toString();
        addr=address.getText().toString();
        bGrp=bloodGroup.getText().toString();

        mRef2 = FirebaseDatabase.getInstance().getReference().child("donordata").getRef();

        Map<String,Object> donorMap=new HashMap<String, Object>();
        Map<String,Object> x= new HashMap<String, Object>();


       String id= mRef2.push().getKey();
        Log.d("keyTAG", "onAccountCreate: "+mRef2.push().getKey());
            x.put("id",id);
            x.put("firstname", fName);
            x.put("lastname", lName);
            x.put("address", addr);
            x.put("bloodgroup", bGrp);
            x.put("contact", con);



            donorMap.put(fName,x);

            mRef2.updateChildren(donorMap);
            Toast.makeText(BloodDonorsActivity.this,"Your account has been successfully created. Thank you!",Toast.LENGTH_LONG).show();
            Intent backToLogin=new Intent(BloodDonorsActivity.this,LoginActivity.class);
            startActivity(backToLogin);


        //mRef2.setValue(donorMap);



            //Toast.makeText(BloodDonorsActivity.this, "Your details are not valid!", Toast.LENGTH_LONG).show();



    }




}
