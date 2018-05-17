package com.SoftwareEng.RishitReddyMuthyala.easyHealth;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/* 
 * This class is the activity bean for hospital application.
 */
public class HospitalApplication extends Application {

    /* 
     * This method gets called up on the creation of the activity.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        if(FirebaseApp.getApps(this).isEmpty()){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

    }

}
