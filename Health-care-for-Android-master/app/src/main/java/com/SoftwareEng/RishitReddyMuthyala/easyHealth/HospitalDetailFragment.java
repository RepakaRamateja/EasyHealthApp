package com.SoftwareEng.RishitReddyMuthyala.easyHealth;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
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
public class HospitalDetailFragment extends android.support.v4.app.Fragment {

    Button maps;String text;

    static HashMap<String, ?> m;
    TextView name,address,contact,link,visitlink;
    ImageView imageView;
    String location;
    ShareActionProvider shareActionProvider;
    public HospitalDetailFragment() {
        // Required empty public constructor
        m=new HashMap();
    }

    /* 
     * This method gets called upon the creation of Hospital Detail Activity.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_movie_detail, container, false);
        setHasOptionsMenu(true);
        if(getArguments()!=null)
        {

            m=(HashMap) getArguments().getSerializable("hospital");

        }


        location=m.get("address").toString();

        maps=(Button)rootView.findViewById(R.id.mapview);
        name=(TextView) rootView.findViewById(R.id.hname);
        address=(TextView) rootView.findViewById(R.id.haddr);
        //stars=(TextView) rootView.findViewById(R.id.movstars);
       // ratings=(TextView) rootView.findViewById(R.id.movrating);
        //director=(TextView) rootView.findViewById(R.id.movdirector);
        imageView=(ImageView) rootView.findViewById(R.id.hospimg);
        contact=(TextView)rootView.findViewById(R.id.hcnt);
        visitlink=(TextView)rootView.findViewById(R.id.hvisit);
        link=(TextView)rootView.findViewById(R.id.hvisitlink);
        //link.setMovementMethod(LinkMovementMethod.getInstance());



        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getMaps();
            }
        });



        //Spanned result= Html.fromHtml((String) m.get("link"));



        //Spanned result= Html.fromHtml((String) m.get("link"));

        //link.setText("<a href=\"http://www.google.com\">google</a> "));
        name.setText((String) m.get("name"));
        contact.setText((String)m.get("contact"));
        address.setText((String) m.get("address"));
        //link.setText(result);
        //link.setText((String)m.get("link"));
        //imageView.setImageResource((Integer)m.get("image"));
        Picasso.with(getContext()).load((String)m.get("url")).into(imageView);

        //String dynamicURL=m.get("link").toString();
        //String linkedText="<b>Official Website:</b>"+String.format("<a href=\"%s\">link</a>",dynamicURL);
        //link.setText(Html.fromHtml(linkedText));
        link.setMovementMethod(LinkMovementMethod.getInstance());
        return rootView;

    }

    public void getMaps()
    {
        Log.d("something", "getMaps: ");
        Intent maps=new Intent(getActivity(),MapsActivity.class);
        maps.putExtra("address",location);
        startActivity(maps);

    }


    public static HospitalDetailFragment newInstance(HashMap<String, ?> hospitalMap)
    {
       HospitalDetailFragment f7=new HospitalDetailFragment();

        m=hospitalMap;
        Bundle args=new Bundle();
        args.putSerializable("hospital",hospitalMap);
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

        shareActionProvider=(ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent intentShare=new Intent(Intent.ACTION_SEND);

        m=(HashMap) getArguments().getSerializable("hospital");

        intentShare.setType("text/plain");

        intentShare.putExtra(Intent.EXTRA_TEXT, (String) m.get("name"));
        shareActionProvider.setShareIntent(intentShare);

        super.onCreateOptionsMenu(menu,inflater);




    }


}
