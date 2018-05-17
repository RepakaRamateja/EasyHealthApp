package com.SoftwareEng.RishitReddyMuthyala.easyHealth;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;


/* 
 * This class holds the Blood bank view.
 */
public class MyFirebaseRecyclerAdapter2 extends FirebaseRecyclerAdapter<BloodBank, MyFirebaseRecyclerAdapter2.BloodBankViewHolder> {

    private Context mContext;
    private List<Map<String, ?>> dataSet;
    public static ImageView iv,popup;


    static RecyclerClickListener recyclerClickListener;


    public MyFirebaseRecyclerAdapter2(Class<BloodBank> modelClass, int modelLayout,
                                      Class<MyFirebaseRecyclerAdapter2.BloodBankViewHolder> holder, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, holder, ref);
        this.mContext = context;
    }



    public void setRecyclerClickListener(RecyclerClickListener recyclerClickListener)
    {
        this.recyclerClickListener=recyclerClickListener;

    }


    /* 
     * This method populates the view holder for blood bank.
     */
    @Override
    protected void populateViewHolder(MyFirebaseRecyclerAdapter2.BloodBankViewHolder bloodBankViewHolder, BloodBank bloodBank, int i) {


        //bloodBankViewHolder.address.setText(bloodBank.getAddress());
        bloodBankViewHolder.Name.setText(bloodBank.getName());
        bloodBankViewHolder.description.setText(bloodBank.getDescription());
        //bloodBankViewHolder.contact.setText(bloodBank.getContact());
        //bloodBankViewHolder.bloodGroup.setText(.getBloodgroup());
        Picasso.with(mContext).load(bloodBank.getUrl()).into(bloodBankViewHolder.iv2);





        //TODO: Populate viewHolder by setting the movie attributes to cardview fields
        //movieViewHolder.nameTV.setText(movie.getName());
    }

    //TODO: Populate ViewHolder and add listeners.
    public static  class BloodBankViewHolder extends RecyclerView.ViewHolder {


        public TextView description,address,lastName,contact,bloodGroup,Name,id;

        public ImageView iv2;

        public BloodBankViewHolder(View itemView) {
            super(itemView);
            Name=(TextView)itemView.findViewById(R.id.name1);
            description=(TextView)itemView.findViewById(R.id.desc1);
            // lastName=(TextView)itemView.findViewById(R.id.lastname);
            //contact=(TextView)itemView.findViewById(R.id.cntct1);
            //bloodGroup=(TextView)itemView.findViewById(R.id.bgrp);
            iv2=(ImageView)itemView.findViewById(R.id.img1);



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