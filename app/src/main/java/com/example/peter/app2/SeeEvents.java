package com.example.peter.app2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.peter.app2.recViews.SeeEventsRecViewAdapter;

public class SeeEvents extends AppCompatActivity {

    private static final String TAG = "SeeEvents";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_events);
        setTitle(R.string.seeEventsTitle);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: Making View");
        // TODO make witty comments
        RecyclerView mRecyclerView = findViewById(R.id.arecviewid);
        SeeEventsRecViewAdapter adapter = new SeeEventsRecViewAdapter("Yes");
        mRecyclerView.setAdapter(adapter);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
