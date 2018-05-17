package com.SoftwareEng.RishitReddyMuthyala.easyHealth;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.List;
import java.util.Map;


/* 
 * This class holds the Donor view.
 */
public class MyFirebaseRecyclerAdapter1 extends FirebaseRecyclerAdapter<Donor, MyFirebaseRecyclerAdapter1.DonorViewHolder> {

    private Context mContext;
    private List<Map<String, ?>> dataSet;
    public static ImageView iv,popup;


    static RecyclerClickListener recyclerClickListener;


    public MyFirebaseRecyclerAdapter1(Class<Donor> modelClass, int modelLayout,
                                    Class<MyFirebaseRecyclerAdapter1.DonorViewHolder> holder, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, holder, ref);
        this.mContext = context;
    }



    public void setRecyclerClickListener(RecyclerClickListener recyclerClickListener)
    {
        this.recyclerClickListener=recyclerClickListener;

    }

    /* 
     * This method populates the holder for donor view.
     */
    @Override
    protected void populateViewHolder(MyFirebaseRecyclerAdapter1.DonorViewHolder donorViewHolder, Donor donor, int i) {


        donorViewHolder.address.setText(donor.getAddress());
        donorViewHolder.firstName.setText(donor.getFirstname());
        donorViewHolder.lastName.setText(donor.getLastname());
        donorViewHolder.contact.setText(donor.getContact());
        donorViewHolder.bloodGroup.setText(donor.getBloodgroup());




        //TODO: Populate viewHolder by setting the movie attributes to cardview fields
        //movieViewHolder.nameTV.setText(movie.getName());
    }

    //TODO: Populate ViewHolder and add listeners.
    public static  class DonorViewHolder extends RecyclerView.ViewHolder {


        public TextView firstName,address,lastName,contact,bloodGroup;


        public DonorViewHolder(View itemView) {
            super(itemView);
            firstName=(TextView)itemView.findViewById(R.id.firstname);
            address=(TextView)itemView.findViewById(R.id.addr);
            lastName=(TextView)itemView.findViewById(R.id.lastname);
            contact=(TextView)itemView.findViewById(R.id.cntct);
            bloodGroup=(TextView)itemView.findViewById(R.id.bgrp);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerClickListener.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerClickListener.onItemLongClick(v,getAdapterPosition());
                    return true;
                }
            });

            if(popup!=null)
            {
                popup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (recyclerClickListener!=null)
                        {
                            recyclerClickListener.onOverFlowMenuClick(v,getAdapterPosition());
                        }
                    }
                });

            }

        }
    }



}



