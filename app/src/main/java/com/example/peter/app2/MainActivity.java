package com.example.peter.app2;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    // Method layout:
    // onCreate:                Constructer
    // onOptionsItemSelected:   For Drawer status
    // initStringArrays:        Fills arrays with string-array resources
    //                          Init: onCreate
    // setupDrawerClickable:    Makes listeners for drawer buttons
    //                          Init: onCreate
    // initContentArrays:       Fills arraylists with content for recyclerview
    //                          Init: onCreate
    // initRecyclerView:        Starts adapter and layout manager for recyclerview
    //                          Init: initContentArrays

    private static final String TAG = "MainActivity";

    //vars
    // Arrays for recyclerView
    private ArrayList<String> mDates = new ArrayList<>();
    private ArrayList<String> mDays = new ArrayList<>();
    private ArrayList<String> mPlans = new ArrayList<>();

    // Dates
    private Calendar nCalendar = Calendar.getInstance();
    private Calendar calThisDate =  Calendar.getInstance();


    // String Arrays
    public String[] mArrayWeekDays;
    public String[] mArrayWeekDaysS;
    public String[] mArrayWeekDaysSS;

    public String[] mArrayMonths;


    // Others
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggleButton;
    private NavigationView mNavView;
    private TextView recHeader;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Created");
        recHeader = findViewById(R.id.cal_header);
        mContext = this;


        initStringArrays();

        // Drawer and toggle
        mNavView = findViewById(R.id.drawer_nav_view);
        mDrawerLayout = findViewById(R.id.drawerLayout_nav);
        mToggleButton = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawerToggleOpen, R.string.drawerToggleClose);
        mDrawerLayout.addDrawerListener(mToggleButton);
        mToggleButton.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // RecyclerView Init
        initContentArrays();

        // Init drawer listeners
        setupDrawerClickable(mNavView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggleButton.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initStringArrays()
    {
        mArrayWeekDays = getResources().getStringArray(R.array.weekDays);
        mArrayWeekDaysS = getResources().getStringArray(R.array.weekDaysS);
        mArrayWeekDaysSS = getResources().getStringArray(R.array.weekDaysSS);

        mArrayMonths = getResources().getStringArray(R.array.months);
    }

    public void setupDrawerClickable(NavigationView navigationView)
    {
        // Setup dialog for exit/cancel
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d(TAG, "onNavigationItemSelected onClick: Agree Exit");

                // User wants to exit
                // TODO
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d(TAG, "onNavigationItemSelected onClick: Cancel Exit");

                // User cancels exit, just do nothing?
                // TODO
            }
        });
        builder.setMessage(R.string.exitDLMSG)
                .setTitle(R.string.exitDLTTL);
        final AlertDialog dialog = builder.create();



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                Intent i;

                if (id == R.id.nav_event) {
                    Log.d(TAG, "onNavigationItemSelected: event nav_event");

                    i = new Intent(MainActivity.this, AddEvent.class);
                    startActivity(i);
                } else if (id == R.id.nav_schedule) {
                    Log.d(TAG, "onNavigationItemSelected: sched nav_event");

                    i = new Intent(MainActivity.this, SeeEvents.class);
                    startActivity(i);
                } else if (id == R.id.nav_settings) {
                    Log.d(TAG, "onNavigationItemSelected: sett nav_event");

                    // TODO make setting activity
                } else if (id == R.id.nav_exit) {
                    Log.d(TAG, "onNavigationItemSelected: exit nav_event");

                    dialog.show();
                }
                    return true;
            }
        });
    }

    private void initContentArrays()
    {
        Log.d(TAG, "initContentArrays: Making Arrays");
        nCalendar.set(Calendar.DAY_OF_YEAR, 1);
        // Antal dato'er der skal laves i kalenderen
        int antal = 365;
        for(int i = 1; i <= antal; i++){
            // TODO find out if while or for
            // Dato
            mDates.add(nCalendar.get(Calendar.DATE) + "");

            // Week days, takes day of week from date and year
            mDays.add(mArrayWeekDaysS[nCalendar.get(Calendar.DAY_OF_WEEK) - 1]);

            // plans for today
            mPlans.add("Intet planlagt");

            //Increase with 1
            nCalendar.add(Calendar.DAY_OF_MONTH, +1);
        }
        setTitle(mArrayMonths[nCalendar.get(Calendar.MONTH)] + " - " + nCalendar.get(Calendar.YEAR));
        nCalendar.set(Calendar.YEAR, calThisDate.get(Calendar.YEAR));
        initRecyclerView();
    }



    // RecyclerView Init
    private void initRecyclerView()
    {
        Log.d(TAG, "initRecyclerView: Making View");
        // TODO make witty comments
        RecyclerView mRecyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mDates, mDays, mPlans, this);
        mRecyclerView.setAdapter(adapter);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.scrollToPosition(calThisDate.get(Calendar.DAY_OF_YEAR) - 3);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            // Int for
            int firstVisiblePosition;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // Triggered on scrolling on RecyclerView
                super.onScrolled(recyclerView, dx, dy);
                firstVisiblePosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                nCalendar.set(Calendar.DAY_OF_YEAR, firstVisiblePosition+1);

                // Title as Month - Year
                setTitle(mArrayMonths[nCalendar.get(Calendar.MONTH)] + " - " + nCalendar.get(Calendar.YEAR));
                nCalendar.set(Calendar.DAY_OF_YEAR, firstVisiblePosition+1);
                recHeader.setText(getString(R.string.week_word) + ": " + nCalendar.get(Calendar.WEEK_OF_YEAR));


                // Block for getting scroll status
                if (dy > 0) {
                    // Finger up, content down
                    //recHeader.setText("Down");
                } else {
                    // Finger down, content up
                    //recHeader.setText("Up");
                }
            }

            // Block for getting scroll status
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
