package com.SoftwareEng.RishitReddyMuthyala.easyHealth;

import android.view.View;


/* 
 * This is the interface for recycle click listener.
 */
public interface RecyclerClickListener {

    public void onItemClick(int position);
    public void onItemLongClick(View v, int position);
    public void onOverFlowMenuClick(View v, int position);
}
