package com.example.peter.app2.scrapcode;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.peter.app2.R;

import java.util.ArrayList;

public class SCRecViewAdapter extends RecyclerView.Adapter<SCRecViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    // Define ArrayList names to be used in Construct, and then in onBindViewHolder
    // private ArrayList<String> mArrayList = new ArrayList<>();

    public SCRecViewAdapter(ArrayList<String> dates, ArrayList<String> days, ArrayList<String> plans, Context context) {

        // Set ArrayLists (from here or where the adapter goes) to an ArrayList from here
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_weekdaytable, viewGroup, false);
        // Inflates first argument is the layout to be inflated as a list
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called");

        // Set viewHolder values (the views defined in ViewHolder) as an int from an array (Construct)
        // viewHolder.myVeryOwnView.setText(mArrayList.get(i));
        // mArrayList is defined in Construct
        // myVeryOwnView is defined in ViewHolder

    }

    @Override
    public int getItemCount(){

        // Return a size of one of the arrays from onBindViewHolder
        // return mArrayList.size();
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        // Define views to be used in ViewHolder (below)

        TextView textview431;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Set views (r.id."name") to their variable (Class ViewHolder)

        }
    }
}