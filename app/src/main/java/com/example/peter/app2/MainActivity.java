package com.example.peter.app2;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //vars
    // Arrays for recyclerView
    private ArrayList<String> mDates = new ArrayList<>();
    private ArrayList<String> mDays = new ArrayList<>();
    private ArrayList<String> mPlans = new ArrayList<>();

    // Dates
    private Calendar nCalendar = Calendar.getInstance();
    private Calendar calThisDate =  Calendar.getInstance();


    // Others
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggleButton;
    private TextView recHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Created");
        mDrawerLayout = findViewById(R.id.drawerLayout_nav);
        mToggleButton = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawerToggleOpen, R.string.drawerToggleClose);
        recHeader = findViewById(R.id.cal_header);
        mDrawerLayout.addDrawerListener(mToggleButton);
        mToggleButton.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initContentArrays();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggleButton.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initContentArrays()
    {
        Log.d(TAG, "initContentArrays: Making Arrays");
        nCalendar.set(Calendar.DAY_OF_YEAR, 1);
        // Antal dato'er der skal laves i kalenderen
        int antal = 365;
        for(int i = 1; i <= antal; i++){

            // Dato
            mDates.add(nCalendar.get(Calendar.DATE) + "");

            // Ugedag
            String[] weekArr = {
                    getString(R.string.day7s),getString(R.string.day1s),getString(R.string.day2s),getString(R.string.day3s),getString(R.string.day4s),getString(R.string.day5s),getString(R.string.day6s)
            };
            mDays.add(weekArr[nCalendar.get(Calendar.DAY_OF_WEEK) - 1]);

            // Planer for i dag
            mPlans.add("Intet planlagt");

            //Incree med 1
            nCalendar.add(Calendar.DAY_OF_MONTH, +1);
        }
        nCalendar.set(Calendar.YEAR, calThisDate.get(Calendar.YEAR));
        initRecyclerView();
    }



    // RecyclerView Init
    private void initRecyclerView()
    {
        Log.d(TAG, "initRecyclerView: Making View");
        RecyclerView mRecyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mDates, mDays, mPlans, this);
        mRecyclerView.setAdapter(adapter);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstVisiblePosition;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisiblePosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                nCalendar.set(Calendar.DAY_OF_YEAR, firstVisiblePosition+1);
                // Måned
                String[] monthArr = {
                        getString(R.string.mon01),getString(R.string.mon02),getString(R.string.mon03),getString(R.string.mon04),
                        getString(R.string.mon05), getString(R.string.mon06),getString(R.string.mon07),getString(R.string.mon08),
                        getString(R.string.mon09),getString(R.string.mon10),getString(R.string.mon11),getString(R.string.mon12)
                };

                // Title som måned - år
                setTitle(monthArr[nCalendar.get(Calendar.MONTH)] + " - " + nCalendar.get(Calendar.YEAR));
                nCalendar.set(Calendar.DAY_OF_YEAR, firstVisiblePosition+1);
                recHeader.setText(getString(R.string.week) + ": " + nCalendar.get(Calendar.WEEK_OF_YEAR));

                if (dy > 0) {
                    // Finger up, content down
                    //recHeader.setText("Down");
                } else {
                    // Finger down, content up
                    //recHeader.setText("Up");
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                firstVisiblePosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Stopped scrolling with momentum (a "flick")
                    //recHeader.setText("Still moving");
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Scroll state in progress
                    //recHeader.setText("Scrolling");
                } else {
                    // Scrolling done, record state
                    //recHeader.setText("Scroll done");
                }
            }
        });
    }
}
