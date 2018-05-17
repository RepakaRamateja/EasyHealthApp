package com.SoftwareEng.RishitReddyMuthyala.easyHealth;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

/*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */


/*
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class BloodBankDetailFragment extends android.support.v4.app.Fragment {

    Button maps1;String text;

    static HashMap<String, ?> m;
    TextView name1,address1,contact1,link1,visitlink1;
    ImageView imageView1;
    String location1;
    ShareActionProvider shareActionProvider1;
    public BloodBankDetailFragment() {
        // Required empty public constructor
        m=new HashMap();
    }

    /* 
     * This method is called upon the creation of the View.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_blood_bank_detail, container, false);
        setHasOptionsMenu(true);
        if(getArguments()!=null)
        {

            m=(HashMap) getArguments().getSerializable("bloodBank");

        }


        location1=m.get("address").toString();

        maps1=(Button)rootView.findViewById(R.id.mapview);
        name1=(TextView) rootView.findViewById(R.id.bbname);
        address1=(TextView) rootView.findViewById(R.id.bbaddr);
        //stars=(TextView) rootView.findViewById(R.id.movstars);
        // ratings=(TextView) rootView.findViewById(R.id.movrating);
        //director=(TextView) rootView.findViewById(R.id.movdirector);
        imageView1=(ImageView) rootView.findViewById(R.id.bbimg);
        contact1=(TextView)rootView.findViewById(R.id.bbcnt);
        visitlink1=(TextView)rootView.findViewById(R.id.bbvisit);
        link1=(TextView)rootView.findViewById(R.id.bbvisitlink);
        //link.setMovementMethod(LinkMovementMethod.getInstance());



        maps1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getMaps();
            }
        });



        //Spanned result= Html.fromHtml((String) m.get("link"));



        //Spanned result= Html.fromHtml((String) m.get("link"));
        Log.d("name", "onCreateView: "+m.get("name"));
        //link.setText("<a href=\"http://www.google.com\">google</a> "));
        name1.setText((String) m.get("name"));
        contact1.setText((String)m.get("contact"));
        address1.setText((String) m.get("address"));
        //link.setText(result);
        //link.setText((String)m.get("link"));
        //imageView.setImageResource((Integer)m.get("image"));
        Picasso.with(getContext()).load((String)m.get("url")).into(imageView1);

        String dynamicURL=m.get("link").toString();
        Log.d("link", "onCreateView: "+dynamicURL);
        String linkedText="<b>Official Website:</b>"+String.format("<a href=\"%s\">link</a>",dynamicURL);
        link1.setText(Html.fromHtml(linkedText));
        link1.setMovementMethod(LinkMovementMethod.getInstance());
        return rootView;

    }

    /* 
     * This method intializes the map activity.
     */
    public void getMaps()
    {
        Log.d("something", "getMaps: ");
        Intent maps=new Intent(getActivity(),MapsActivity.class);
        maps.putExtra("address",location1);
        startActivity(maps);

    }

    /* 
     * This method creates and returns an instane if BloodBankDetailFragment.
     */
    public static BloodBankDetailFragment newInstance(HashMap<String, ?> bloodBankMap)
    {
        BloodBankDetailFragment f7=new BloodBankDetailFragment();

        m=bloodBankMap;
        Bundle args=new Bundle();
        args.putSerializable("bloodBank",bloodBankMap);
        f7.setArguments(args);
        return f7;
    }


    /* 
     * This method gets called upon the creation of options menu.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        if(menu.findItem(R.id.share)==null)
        {
            inflater.inflate(R.menu.fragment_menu,menu);}

        MenuItem shareItem=menu.findItem(R.id.share);

        shareActionProvider1=(ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent intentShare=new Intent(Intent.ACTION_SEND);

        m=(HashMap) getArguments().getSerializable("bloodBank");

        intentShare.setType("text/plain");

        intentShare.putExtra(Intent.EXTRA_TEXT, (String) m.get("name"));
        shareActionProvider1.setShareIntent(intentShare);

        super.onCreateOptionsMenu(menu,inflater);




    }


}
