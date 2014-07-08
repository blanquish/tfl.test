package com.example.tfl_test;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TableLayout;

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
