package com.example.peter.app2;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        setTitle(R.string.addEventTitle);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initSpinner();
    }

    private void initSpinner(){
        Spinner spinner = findViewById(R.id.spinner_eventtype);
            // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.addevent_eventtypes, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
    }

    public void buttonCancel(View view){
        // User wants to cancel creation of an event
        // Give user a popup: Cancel and Accept
        // Cancel: User continues in creation of Event
        // Accept: User abandons the creation of an event, take user back to calendar
    }

    public void buttonConfirm(View view){
        // UI changes and delays for showing butter smooth animations,
        final Handler handler = new Handler();

        // Hide current views with small delay
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.scrollview_contentroot).setVisibility(View.GONE);;
            }
        }, 400);

        // Show loading animation, medium delay in total for facade of doing stuff with data
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            }
        }, 1800);

        // After large delay, actual information starts to get processed
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                httpPOSTdata();
            }
        }, 2600);

    }

    // Method for creating post, sending post, and creating call and wait for callback
    private void httpPOSTdata(){

    }

    // Method to be called after callback in httpPOSTdata happens, ends with new activity
    private void doneAddingEvent(){

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
