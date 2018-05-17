package com.back4app.quickstartexampleapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by jashwanthreddy on 11/27/17.
 */

/*This is implemented as a recycle view for the nurse to view the list of patient prescriptons.*/
public class RecycleViewNursePrescriptionsViewAdapter extends RecyclerView.Adapter<RecycleViewNursePrescriptionsViewAdapter.ViewHolder> {
    private List<ParseObject> mDataset;
    private List<ParseUser> PDataset;
    private int lastPosition = -1;
    private Context mcontext;
    // AdapterView.OnItemClickListener
    static RecycleViewNursePrescriptionsViewAdapter.OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        //    public void onItemLongClick(View view, int position);
        //public void onOverFlowMenuClick(View v, final int position);
    }

    public void setOnItemClickListener(final RecycleViewNursePrescriptionsViewAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public RecycleViewNursePrescriptionsViewAdapter(Context myContext, List<ParseObject> myDataset,List<ParseUser> Pdataset)
    {
        Log.d("RecycleViewAdapter", "Inside the constructor function " + myDataset.size());
        mDataset = myDataset;
        mcontext = myContext;
        PDataset=Pdataset;

    }

    // Return the size of dataset invoked by layout manager
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        //return position%3 ;
        if (position <= 4)
        {
            return 1;
        }
        else if (position >= getItemCount()-5)
        {
            return 3;
        }
        else
            return 2;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView patientName;
        public TextView patientPrescriptionDetail;

        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.cardview_nurse_view_prescriptions);
            //     imageview = (ImageView)v.findViewById(R.id.cardview_imageview);
            patientName = (TextView) v.findViewById(R.id.nurse_prescription_view_patient_name);
            patientPrescriptionDetail = (TextView) v.findViewById(R.id.nurse_prescription_view_patient_detail);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                            mItemClickListener.onItemClick(v, getAdapterPosition());
                        }
                    }
                }
            });
        }

        public void bindMovieData(final ParseObject prescription, final ParseUser patient) {
         //   patientName.setText((String)prescription.getString("Fname"));
            //  imageview.setImageResource((Integer)movie.get("image"));
         //   patientPrescriptionDetail.setText((String)prescription.getString("Lname"));
         /* Fill the patient name and prescription info correctly */
         patientName.setText((String)patient.getString("Fname")+ " "+patient.getString("Lname"));
           patientPrescriptionDetail.setText((String)prescription.getString("medicines"));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Log.d("RecycleViewAdapter", "onBindViewHolder: current position is " + position);
        ParseObject prescription = mDataset.get(position);
        ParseUser patient = PDataset.get(position);
        Log.d("RecycleViewAdapter", "onBindViewHolder: "+prescription.getString("medicine")+ " and patient  is "
                +prescription.getString("patientId"));
        holder.bindMovieData(prescription, patient);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewnurseviewprescriptions, parent, false);
                // set view layout, size, margins . paddings etc..
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

}
