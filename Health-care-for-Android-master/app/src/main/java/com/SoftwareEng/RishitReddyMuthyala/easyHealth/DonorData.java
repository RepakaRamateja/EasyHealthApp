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
 * This class is used for saving donor data.
 */
public class DonorData {

    List<Map<String,?>> donorList;
    DatabaseReference mRef3;
    MyFirebaseRecyclerAdapter1 myFirebaseRecylerAdapter1;
    Context mContext1;

    public void setAdapter(MyFirebaseRecyclerAdapter1 mAdapter) {
        myFirebaseRecylerAdapter1 = mAdapter;
    }

    public void removeItemFromServer(Map<String,?> donor){
        if(donor!=null){
            String id = (String)donor.get("contact");
            mRef3.child(id).removeValue();
        }
    }

    public void addItemToServer(Map<String,?> donor){
        if(donor!=null){
            String id = (String) donor.get("contact");
            mRef3.child(id).setValue(donor);
        }
    }

    public DatabaseReference getFireBaseRef(){
        return mRef3;
    }
    public void setContext(Context context){mContext1 = context;}

    public List<Map<String, ?>> getDonorList() {
        return donorList;
    }

    public int getSize(){
        return donorList.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < donorList.size()){
            return (HashMap) donorList.get(i);
        } else return null;
    }


    public void onItemRemovedFromCloud(HashMap item){
        int position = -1;
        String id=(String)item.get("contact");
        for(int i=0;i<donorList.size();i++){
            HashMap donor = (HashMap)donorList.get(i);
            String mid = (String) donor.get("contact");
            if(mid.equals(id)){
                position= i;
                break;
            }
        }
        if(position != -1){
            donorList.remove(position);
            Toast.makeText(mContext1, "Item Removed:" + id, Toast.LENGTH_SHORT).show();

        }
    }

    public void onItemAddedToCloud(HashMap item){
        int insertPosition = 0;
        String id=(String)item.get("contact");
        for(int i=0;i<donorList.size();i++){
            HashMap donor = (HashMap)donorList.get(i);
            String mid = (String)donor.get("contact");
            if(mid.equals(id)){
                return;
            }
            if(mid.compareTo(id)<0){
                insertPosition=i+1;
            }else{
                break;
            }
        }
        donorList.add(insertPosition,item);
        // Toast.makeText(mContext, "Item added:" + id, Toast.LENGTH_SHORT).show();

    }

    public void onItemUpdatedToCloud(HashMap item){
        String id=(String)item.get("contact");
        for(int i=0;i<donorList.size();i++){
            HashMap donor = (HashMap)donorList.get(i);
            String mid = (String)donor.get("contact");
            if(mid.equals(id)){
                donorList.remove(i);
                donorList.add(i,item);
                //Log.d("My Test: NotifyChanged",id);

                break;
            }
        }

    }
    public void initializeDataFromCloud() {
        donorList.clear();
        mRef3.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: OnChildAdded", dataSnapshot.toString());
                HashMap<String,String> donor = (HashMap<String,String>)dataSnapshot.getValue();
                onItemAddedToCloud(donor);
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: OnChildChanged", dataSnapshot.toString());
                HashMap<String,String> donor = (HashMap<String,String>)dataSnapshot.getValue();
                onItemUpdatedToCloud(donor);
            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Log.d("MyTest: OnChildRemoved", dataSnapshot.toString());
                HashMap<String,String> donor = (HashMap<String,String>)dataSnapshot.getValue();
                onItemRemovedFromCloud(donor);
            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public DonorData(){

        donorList = new ArrayList<Map<String,?>>();
        mRef3 = FirebaseDatabase.getInstance().getReference().child("donordata").getRef();
        myFirebaseRecylerAdapter1 = null;
        mContext1 = null;

    }


    public int findFirst(String query){

        for(int i=0;i<donorList.size();i++){
            HashMap hm = (HashMap)getDonorList().get(i);
            if(((String)hm.get("name")).toLowerCase().contains(query.toLowerCase())){
                return i;
            }
        }
        return 0;

    }

}
