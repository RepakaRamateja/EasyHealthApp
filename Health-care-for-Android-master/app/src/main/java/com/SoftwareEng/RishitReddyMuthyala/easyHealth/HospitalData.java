package com.SoftwareEng.RishitReddyMuthyala.easyHealth;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* 
 * This method is for switching to Card View Activity.
 */
public class HospitalData {
    List<Map<String,?>> hospitalList;
    DatabaseReference mRef;
    MyFirebaseRecylerAdapter myFirebaseRecylerAdapter;
    Context mContext;

    public void setAdapter(MyFirebaseRecylerAdapter mAdapter) {
        myFirebaseRecylerAdapter = mAdapter;
    }

    /* 
     * This method gets called when the item is removed  from the server.
     */
    public void removeItemFromServer(Map<String,?> hospital){
        if(hospital!=null){
            String id = (String)hospital.get("id");
            mRef.child(id).removeValue();
        }
    }

    /* 
     * This method gets called up on the item addition to the server.
     */
    public void addItemToServer(Map<String,?> hospital){
        if(hospital!=null){
            String id = (String) hospital.get("id");
            mRef.child(id).setValue(hospital);
        }
    }

    public DatabaseReference getFireBaseRef(){
        return mRef;
    }
    public void setContext(Context context){mContext = context;}

    public List<Map<String, ?>> getHospitalList() {
        return hospitalList;
    }

    public int getSize(){
        return hospitalList.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < hospitalList.size()){
            return (HashMap) hospitalList.get(i);
        } else return null;
    }

    /* 
     * This method gets called when the item is removed from cloud.
     */
    public void onItemRemovedFromCloud(HashMap item){
        int position = -1;
        String id=(String)item.get("id");
        for(int i=0;i<hospitalList.size();i++){
            HashMap hospital = (HashMap)hospitalList.get(i);
            String mid = (String) hospital.get("id");
            if(mid.equals(id)){
                position= i;
                break;
            }
        }
        if(position != -1){
            hospitalList.remove(position);
            Toast.makeText(mContext, "Item Removed:" + id, Toast.LENGTH_SHORT).show();

        }
    }

    /* 
     * This method gets called when an item is added to the cloud.
     */
    public void onItemAddedToCloud(HashMap item){
        int insertPosition = 0;
        String id=(String)item.get("id");
        for(int i=0;i<hospitalList.size();i++){
            HashMap hospital = (HashMap)hospitalList.get(i);
            String mid = (String)hospital.get("id");
            if(mid.equals(id)){
                return;
            }
            if(mid.compareTo(id)<0){
                insertPosition=i+1;
            }else{
                break;
            }
        }
        hospitalList.add(insertPosition,item);
       // Toast.makeText(mContext, "Item added:" + id, Toast.LENGTH_SHORT).show();

    }

    /* 
     * This method gets called up on the updation of an item to the cloud.
     */
    public void onItemUpdatedToCloud(HashMap item){
        String id=(String)item.get("id");
        for(int i=0;i<hospitalList.size();i++){
            HashMap hospital = (HashMap)hospitalList.get(i);
            String mid = (String)hospital.get("id");
            if(mid.equals(id)){
                hospitalList.remove(i);
                hospitalList.add(i,item);
                Log.d("My Test: NotifyChanged",id);

                break;
            }
        }

    }
    
    /* 
     * This method is used for initializing data from cloud.
     */
    public void initializeDataFromCloud() {
       hospitalList.clear();
        mRef.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: OnChildAdded", dataSnapshot.toString());
                HashMap<String,String> hospital = (HashMap<String,String>)dataSnapshot.getValue();
                onItemAddedToCloud(hospital);
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: OnChildChanged", dataSnapshot.toString());
                HashMap<String,String> hospital = (HashMap<String,String>)dataSnapshot.getValue();
                onItemUpdatedToCloud(hospital);
            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Log.d("MyTest: OnChildRemoved", dataSnapshot.toString());
                HashMap<String,String> hospital = (HashMap<String,String>)dataSnapshot.getValue();
                onItemRemovedFromCloud(hospital);
            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public HospitalData(){

        hospitalList = new ArrayList<Map<String,?>>();
        mRef = FirebaseDatabase.getInstance().getReference().child("hospitaldata").getRef();
        myFirebaseRecylerAdapter = null;
        mContext = null;

    }


    public int findFirst(String query){

        for(int i=0;i<hospitalList.size();i++){
            HashMap hm = (HashMap)getHospitalList().get(i);
            if(((String)hm.get("name")).toLowerCase().contains(query.toLowerCase())){
                return i;
            }
        }
        return 0;

    }


    }


