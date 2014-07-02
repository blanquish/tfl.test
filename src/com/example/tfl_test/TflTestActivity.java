package com.example.tfl_test;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

public class TflTestActivity extends Activity {

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listView = (ListView)findViewById(R.id.pathList);

        AsyncTask<String, String, String> httpGetTask = new TflLoadLineStatusTask(this, listView);
        httpGetTask.execute("");

    }
}
