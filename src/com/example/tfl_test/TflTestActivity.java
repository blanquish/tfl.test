package com.example.tfl_test;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.xmlpull.v1.XmlPullParser.*;
import static org.xmlpull.v1.XmlPullParser.START_TAG;

public class TflTestActivity extends Activity {

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listView = (ListView)findViewById(R.id.pathList);

        AsyncTask<String, String, InputStream> httpGetTask = new TflLoadLineStatusTask(this, listView);

        httpGetTask.execute("");

    }


    public class TflLoadLineStatusTask extends AsyncTask<String, String, InputStream> {

        private static final String TFL_LINE_STATUS_URL = "http://cloud.tfl.gov.uk/TrackerNet/LineStatus";
        private  final String ns = null;


        private TflTestActivity tflTestActivity;
        private ListView listView;

        public TflLoadLineStatusTask(TflTestActivity tflTestActivity, ListView listView) {
            this.tflTestActivity = tflTestActivity;
            this.listView = listView;
        }

        @Override
        protected InputStream doInBackground(String[] objects) {

            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(TFL_LINE_STATUS_URL);
            request.setHeader("User-Agent", "set your desired User-Agent");

            try {
                HttpResponse response = client.execute(request);

                StatusLine status = response.getStatusLine();
                if (status.getStatusCode() != 200) {
                    throw new IOException("Invalid response from server: " + status.toString());
                }

                return response.getEntity().getContent();

            } catch (IOException e) {
                Log.d("error", e.getLocalizedMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(InputStream tflXML) {      // THIS IS THE MAIN THREAD, parse in background using SimpleXml

            // OTTO library for bus events

            if (tflXML != null) {

                try {
                    List<Entry> entries = parse(tflXML);

//                XmlPullParser parser = Xml.newPullParser();
//                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//                parser.setInput(tflXML, null);
//                parser.nextTag();

                    List<Entry> entries2 = Arrays.asList(
                            new Entry("title1", "summary1", "link1"),
                            new Entry("title2", "summary2", "link2"),
                            new Entry("title3", "summary3", "link3"),
                            new Entry("title4", "summary4", "link4"),
                            new Entry("title5", "summary5", "link5"));

                    listView.setAdapter(new ArrayAdapter<Entry>(tflTestActivity, android.R.layout.simple_list_item_1, entries));

                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } //finally {
//                    try {
//                        tflXML.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        }

        public List<Entry> parse(InputStream in) throws XmlPullParserException, IOException {
//            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();
                return readFeed(parser);
        }

        private List<Entry> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
            List<Entry> entries = new ArrayList<Entry>();

//            parser.require(XmlPullParser.START_TAG, ns, "ArrayOfLineStatus");
            parser.require(START_TAG, ns, "ArrayOfLineStatus");
            while (parser.next() != END_TAG) {
                if (parser.getEventType() != START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equals("LineStatus")) {
//                if (name.equals("Line")) {
                    entries.add(readEntry(parser));
                    parser.nextTag();
//                    parser.nextTag();
//                    Log.d("LINE", parser.getAttributeValue(null, "Name"));
//                } else if (name.equals("Status")) {
//                    Log.d("Status", parser.getAttributeValue(null, "Description"));
                } else {
                    skip(parser);
                }
            }
            return entries;
        }


        public class Entry {
            public final String name;
            public final String statusDescription;
            public final String statusCode;

            public Entry(String name, String statusCode, String statusDescription) {
                this.name = name;
                this.statusCode = statusCode;
                this.statusDescription = statusDescription;
            }
        }

        // Parses the contents of an entry. If it encounters a name, statusCode, or statusDescription tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
        private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
//            parser.require(START_TAG, ns, "LineStatus");
            String tubeLineName = null;
            String statusDescription = null;
            String statusCode = null;
            while (parser.next() != END_TAG) {
                if (parser.getEventType() != START_TAG) {
                    continue;
                }

                String name = parser.getName();
                if (name.equals("Line")) {
                    tubeLineName = parser.getAttributeValue(null, "Name");
                    parser.nextTag(); // this is the closing tag
                    parser.nextTag();
                    name = parser.getName();
                    if (name.equals("Status")) {
                        statusCode = parser.getAttributeValue(null, "ID");
                        statusDescription = parser.getAttributeValue(null, "Description");
                    } else {
                        skip(parser);
                    }
                } else {
                    skip(parser);
                }
            }
            return new Entry(tubeLineName, statusDescription, statusCode);
        }

        private String readTubeLineName(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(START_TAG, ns, "Line");
            String title = parser.getAttributeValue(null, "Name");
            parser.require(END_TAG, ns, "Line");
            return title;
        }

        private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
            if (parser.getEventType() != START_TAG) {
                throw new IllegalStateException();
            }
            int depth = 1;
            while (depth != 0) {
                switch (parser.next()) {
                    case END_TAG:
                        depth--;
                        break;
                    case START_TAG:
                        depth++;
                        break;
                }
            }
        }


    }


}
