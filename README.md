Screenshots of EasyHealth
==========================
-Screenshots of the android app are provided in the Easyhealth Screenshots powerpoint presentation present in the github location.

Objectives of EasyHealth
=========================
-Select a doctor from the list of doctors provided for the patient.

-On selection of doctor, they can see the details of the doctor.

-Enable patients to book appointments with doctors of their preference.

-Patients can view prescriptions.

-Doctors can view their appointments on their dashboard.

-On click of any appointment, doctor can view the details of the patient. 

-The doctor can then write the prescriptions to the selected patients.

-Patients too have a view related to appointments where they can view all the appointments that they have booked.

-On click of the blood donor button in the left hand navigation of patient profile, users will be redirected to blood donor where they can use the features of the blood donor.

-Blood Donor shows the list of blood banks, hospitals and nearby blood donor details along with contact details for each one.


Installation of EasyHealth(and blood donor which would be used in EasyHealth)
==============================================================================
-Install Android Studio.

-Download, unzip and import the Doctor-Patient-Nurse-Appointment-system into the Android Studio.

-Download, unzip and import the Health-care-for-Android-master into a separate window of Android Studio for including the blood donor features.

-To run both the applications, follow the below procedures to run the apps.

-In Android Studio, click the Tools menu, select Android, and click AVD Manager to open the Android Virtual Device Manager.

-Create a new emulator by clicking the New button.

-Open the Android Virtual Device Manager again. 

-Now select the created emulator and click on start.

-Database for the EasyHealth app is located at https://www.back4app.com/

-Database credentials are username/email address- jashwanthreddy09@gmail.com and password - easyhealth.

-After logging in, click on dashboard.

-On the left hand navigation, under the browser tab, you would find a list of tables(Installation, Roles, Session, User, Appointments, Prescriptions) that we use as backend for our app.

-Database for the BloodDonor app is located at https://console.firebase.google.com

-Database credentials are username/email address - rishitmuthyala888@gmail.com and password - jfkB_2016.

-After logging in, select Trail1 from the projects.
-On the left hand navigation, select Database from the left hand navigation.
-Then on the right hand side, you can select and the explore the bloodbankdata, donordata and hospitaldata tables that are being used for the Blood Donor.


System requirements
=====================

-For the EasyHealth App(Not the blood donor app), you would need to do the following too.
-In the build.gradle make sure that below minimum versions

  -Compile SDK Version = 26 

  -Min SDK Version = 16.


-In the build.gradle make sure you have in the dependencies 
 'com.parse:parse-android:1.16.3' //update version to the latest one


-make sure that you have this in the repositories


 repositories {
 
      mavenCentral()
      jcenter()
        maven {
	              url "http://maven.google.com"
              }
	      
  }
  
-In the androidmanifest.xml make sure that you have 
1. < uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
2. < uses-permission android:name="android.permission.INTERNET"/>

- In the androidmanifest.xml file you also have defined this which are useful in connecting to your backend(back4app parse-api)

1. < meta-data android:name="com.parse.SERVER_URL" android:value="@string/back4app_server_url" />

2. < meta-data android:name="com.parse.APPLICATION_ID" android:value="@string/back4app_app_id" />
  
3. < meta-data android:name="com.parse.CLIENT_KEY" android:value="@string/back4app_client_key" />

- In the string.xml file make sure that you have 

< string name="back4app_server_url">https://parseapi.back4app.com/</string>
    
<!-- Change the following strings as required -->

< string name="back4app_app_id">efK08GFEEpuz0IEViSEYXDAPcLUQn3e8QYbybeW2</string>
    
< string name="back4app_client_key">4yQ26Qtl8zkzoUBKs7hCsh01l5c4zqlR6QZVnRF6</string>

- After testing all the app functionalities

- login to the back4app with the url https://www.back4app.com/ and

- credentials: username:jashwanthreddy09@gmail.com  password:easyhealth

- Now click on dashboard

- Now you can find information related to sessions in session table 

- And user related information in user table

- Installation related information in installation table

- Appointments related information in appointment table

- Prescription related information in prescription table

- This clearly shows that all the requirements are satisfied
