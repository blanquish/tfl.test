package com.example.tfl_test;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TableLayout;

/**
 * Entry point for the application:
 *  1) creates the layout that will be used to display the data on the phone
 * 2) creates an instance of the task that will retrieve the data we need.
 */
public class TflTestActivity extends Activity {

    private TableLayout tableLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tube_line_status_layout);

        tableLayout = (TableLayout)findViewById(R.id.tubeLineList);

        AsyncTask<String, String, String> httpGetTask = new TflLoadLineStatusTask(this, tableLayout);
        httpGetTask.execute("");

    }
}
