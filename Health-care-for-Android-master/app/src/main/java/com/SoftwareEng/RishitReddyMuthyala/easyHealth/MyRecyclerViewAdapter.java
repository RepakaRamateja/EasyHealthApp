package com.SoftwareEng.RishitReddyMuthyala.easyHealth;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


/* 
 * This class is for switching to recyler view.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Map<String, ?>> dataSet;
    private Context context;

    Boolean sortLayout=false;
    RecyclerClickListener recyclerClickListener;

    public void setSortLayout(Boolean enable)
    {
        sortLayout=enable;

    }

    public void setRecyclerClickListener(RecyclerClickListener recyclerClickListener)
    {
        this.recyclerClickListener=recyclerClickListener;

    }


    /* 
     * This method gets the item view type.
     */
    @Override
    public int getItemViewType(int position) {
        if(sortLayout)
        {
            if(position<5)
            {
                return 1;
            }

            else if(position>=5 && position<25)
            {
                return 2;
            }

            else
            {
                return 3;
            }


        }
        else
        {
            return 1;
        }
    }

    public MyRecyclerViewAdapter(Context myContext, List<Map<String, ?>> myDataSet)
    {

        context=myContext;
        dataSet=myDataSet;


    }

    /* 
     * This class acts as the view holder.
     */
    
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView iv,popup;
        public TextView title;
        public TextView description;
        public CheckBox checkBox;


        public void bindMovieData(Map<String, ?> movie)
        {
            title.setText((String) movie.get("name"));
            description.setText((String) movie.get("description"));

            iv.setImageResource((Integer) movie.get("image"));


        }



        public ViewHolder(View v)
        {
            super(v);

            iv=(ImageView)v.findViewById(R.id.img);
            title=(TextView)v.findViewById(R.id.name);
            description=(TextView)v.findViewById(R.id.desc);
            //popup=(ImageView)v.findViewById(R.id.popup);



            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerClickListener.onItemClick(getAdapterPosition());
                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
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


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        switch(viewType)
        {
            case 1: v= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card_list,parent,false);
                break;

            case 2: v= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card_list,parent,false);
                break;

            case 3: v= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card_list,parent,false);
                break;



        }

        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card_list,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Map<String, ?> movie=dataSet.get(position);
        holder.bindMovieData(movie);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }





}
