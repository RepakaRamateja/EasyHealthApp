package com.back4app.quickstartexampleapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jashwanthreddy on 2/14/17.
 */
/* This class is redirected from patient navigation drawer to view the list of doctors
* he can choose to book appointments. */
public class RecycleViewPatientHomeAdapter extends RecyclerView.Adapter<RecycleViewPatientHomeAdapter.ViewHolder> {
    private List<ParseObject> mDataset;
    RecycleViewNurseViewPrescriptions mRecycleViewAdapter;
    private int lastPosition = -1;
    private Context mcontext;
   // AdapterView.OnItemClickListener
    static  OnItemClickListener mItemClickListener;


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    //    public void onItemLongClick(View view, int position);
        //public void onOverFlowMenuClick(View v, final int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    // constructor
    public RecycleViewPatientHomeAdapter(Context myContext, List<ParseObject> myDataset)
    {
        Log.d("RecycleViewAdapter", "Inside the constructor function " + myDataset.size());
        mDataset = myDataset;
        mcontext = myContext;
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
      //  public ImageView imageview;
        public CardView cardView;
        public TextView firstName;
        public TextView lastName;
   //     public CheckBox checkBox;
   //     public ImageView overflow_image;
  //      public RatingBar ratingBar;
        public ViewHolder(View v) {
            super(v);
            cardView = (CardView)v.findViewById(R.id.cardview_patient_home);
       //     imageview = (ImageView)v.findViewById(R.id.cardview_imageview);
            firstName = (TextView)v.findViewById(R.id.patienthomedocfirstname);
            lastName = (TextView)v.findViewById(R.id.patienthomedoclastname);
       //     checkBox = (CheckBox)v.findViewById(R.id.cardview_checkBox);
       //     overflow_image = (ImageView)v.findViewById(R.id.cardview_overflow);
           /* ratingBar = (RatingBar)v.findViewById(R.id.cardview_ratingbar);
            ratingBar.setMax(5);
            ratingBar.setStepSize(0.1f);*/

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

          /*  v.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                  if (mItemClickListener != null) {
                      if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                         mItemClickListener.onItemLongClick(v, getAdapterPosition());
                      }
                  }
                    return true;
                }
            });*/
            /*overflow_image.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onOverFlowMenuClick(v, getAdapterPosition());
                }
            });*/
        }

        public void bindMovieData(final ParseObject doctor) {
            firstName.setText((String)doctor.getString("Fname"));
          //  imageview.setImageResource((Integer)movie.get("image"));
            lastName.setText((String)doctor.getString("Lname"));
          //  checkBox.setChecked((Boolean)movie.get("selection"));
           /* ratingBar.setMax(5);
            ratingBar.setStepSize(0.1f);*/
          //  Double dr = (Double)movie.get("rating");
          //  float fr = (float)dr.floatValue()/2.0f;
 /*           ratingBar.setRating((float)fr);*/
          /*  checkBox.setOnClickListener(new View.OnClickListener(){
                final HashMap<String, Boolean> temp = (HashMap<String,Boolean>)movie;
                @Override
                public void onClick(View v) {
                      temp.put("selection", true);
                }
            });*/
        }

    }

    // Replace the contents of the view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        // get the element from your dataset at this position
        // replace the contents of the view with parent
        Log.d("RecycleViewAdapter", "onBindViewHolder: current position is " + position);
        ParseObject doctor = mDataset.get(position);
        Log.d("RecycleViewAdapter", "onBindViewHolder: "+doctor.getString("Fname")+ " and speciality is "+doctor.getString("Speciality"));
        holder.bindMovieData(doctor);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v;
        // create a new view
        /*switch (viewType) {
            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow, parent, false);
                break;
            case 2:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow, parent, false);
                break;
            case 3:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow, parent, false);
                break;
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow, parent, false);
                break;
        }*/
       v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewpatienthome, parent, false);
        // set view layout, size, margins . paddings etc..
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
}
