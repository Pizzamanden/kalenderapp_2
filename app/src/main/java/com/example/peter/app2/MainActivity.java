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
import android.widget.TextView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //vars
    // Arrays for recyclerView
    private ArrayList<String> mDates = new ArrayList<>();
    private ArrayList<String> mDays = new ArrayList<>();
    private ArrayList<String> mPlans = new ArrayList<>();

    // Dates


    // Others
    private String folderolURL = "http://www.folderol.dk/";
    private Calendar nCalendar = Calendar.getInstance();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Created");
        mDrawerLayout = findViewById(R.id.drawerLayout_nav);
        mToggleButton = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawerToggleOpen, R.string.drawerToggleClose);

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

        // Antal dato'er der skal laves i kalenderen
        int antal = 365;
        for(int i = 0; i < antal; i++){

            // Dato
            mDates.add(nCalendar.get(Calendar.DATE) + "");

            // Ugedag
            switch (nCalendar.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY:
                    mDays.add("Sun");
                    break;
                case Calendar.MONDAY:
                    mDays.add("Mon");
                    break;
                case Calendar.TUESDAY:
                    mDays.add("Tue");
                    break;
                case Calendar.WEDNESDAY:
                    mDays.add("Wed");
                    break;
                case Calendar.THURSDAY:
                    mDays.add("Thu");
                    break;
                case Calendar.FRIDAY:
                    mDays.add("Fri");
                    break;
                case Calendar.SATURDAY:
                    mDays.add("Sat");
                    break;
            }

            // Planer for i dag
            mPlans.add("Intet planlagt");

            //Incree med 1
            nCalendar.add(Calendar.DAY_OF_MONTH, +1);
        }

        initRecyclerView();
    }

    // Start på HTTP kald
    public void startHttpReq(View view)
    {
        String thisURL = folderolURL + "";
        makeHttpReq(thisURL);
        // Min Url
    }
    public void httpCallback(String response)
    {
        // Response is HTTP Response

        TextView thisView = findViewById(R.id.resultView);

        thisView.setText(response);
        // Not Used, saved for convenience
    }

    private void makeHttpReq(String url)
    {
        Log.d(TAG, "getHttpReq: Fired OkHttp Method");



        Log.d(TAG, "getHttpReq: Building Client");
        OkHttpClient client = new OkHttpClient();



        Log.d(TAG, "getHttpReq: Building Request");
        // Request as post in .add takes name and value
        RequestBody formBody = new FormBody.Builder()
                .add("thisispost", "mFirstPost")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();


        Log.d(TAG, "getHttpReq: Firing Call");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "getHttpReq: Failure");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "getHttpReq: Response");
                if(response.isSuccessful()){
                    Log.d(TAG, "getHttpReq: Response successful");
                    final String mResponse = response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            httpCallback(mResponse);
                        }
                    });
                }
            }
        });
    }

    private void initRecyclerView()
    {
        Log.d(TAG, "initRecyclerView: Making View");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mDates, mDays, mPlans, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
