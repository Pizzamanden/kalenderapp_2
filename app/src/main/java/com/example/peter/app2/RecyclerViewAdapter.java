package com.example.peter.app2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mDates = new ArrayList<>();
    private ArrayList<String> mDays = new ArrayList<>();
    private ArrayList<String> mPlans = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> dates, ArrayList<String> days, ArrayList<String> plans, Context context) {
        this.mDates = dates;
        this.mDays = days;
        this.mPlans = plans;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_weekdaytable, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called");

        viewHolder.weekDate.setText(mDates.get(i));
        viewHolder.weekDay.setText(mDays.get(i));
        viewHolder.weekPlans.setText(mPlans.get(i));
    }

    @Override
    public int getItemCount(){

        return mDates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView weekDate;
        TextView weekDay;
        TextView weekPlans;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            weekDate = itemView.findViewById(R.id.textView_date);
            weekDay = itemView.findViewById(R.id.textView_day);
            weekPlans = itemView.findViewById(R.id.textView_plans);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}