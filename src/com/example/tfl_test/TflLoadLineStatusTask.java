package com.example.tfl_test;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.tfl_test.model.ArrayOfLineStatus;
import com.example.tfl_test.model.LineStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.*;

/**
 * This asynchrounous task will fire an http request to TFL's api
 * and parse data received as XML.
 *
 * Then it will display the
 * information on the phone screen using each underground line
 * corresponding colour.
 */
public class TflLoadLineStatusTask extends AsyncTask<String, String, String> {

    private static final String TFL_LINE_STATUS_URL = "http://cloud.tfl.gov.uk/TrackerNet/LineStatus";

    private TflTestActivity tflTestActivity;
    private TableLayout tableLayout;

    public TflLoadLineStatusTask(TflTestActivity tflTestActivity, TableLayout tableLayout) {
        this.tflTestActivity = tflTestActivity;
        this.tableLayout = tableLayout;
    }

    /*
    Make call to retrieve data
     */
    @Override
    protected String doInBackground(String[] objects) {
            return readTubeLineInfoFromHttpRequest();

    }

    private String readTubeLineInfoFromHttpRequest() {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(TFL_LINE_STATUS_URL);
        request.setHeader("User-Agent", "set your desired User-Agent");
        InputStream inputStream = null;

        // Attempt getting the data, otherwise log exception and exit
        try {
            HttpResponse response = client.execute(request);

            StatusLine status = response.getStatusLine();
            if (status.getStatusCode() != 200) {
                throw new IOException("Invalid response from server: " + status.toString());
            }

            HttpEntity entity = response.getEntity();

            // Woho! got data, return parsed input stream, because
            // otherwise we will get a ConcurrentModificationException
            // when reading the input stream from onPostExecuteMethod (as it is
            // executed on a different thread)
            if (entity != null) {
                inputStream = entity.getContent();
                return convertStreamToString(inputStream);
            }

        }  catch (IOException e) {
            Log.d("error", e.getLocalizedMessage()); // error is printed in log, not shown in the application
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                Log.d("error", e.getLocalizedMessage());
            }
        }

        return null;
    }

    /*
    This will be executed after the background task has completed, it updates the user
    interface to display results.
     */
    @Override
    protected void onPostExecute(String tflXML) {

        if (tflXML != null && !tflXML.isEmpty()) {

            // workaround to get rid of first character
            tflXML = tflXML.substring(tflXML.indexOf("<"));

            try {

                // Read the xml contained in the string, serialise to a list of lines
                Serializer serializer = new Persister();
                ArrayOfLineStatus lineStatusList = serializer.read(ArrayOfLineStatus.class, tflXML);

                // Display pretty table with tube line and status, with corresponding colour
                int counter = 0;
                for (LineStatus lineStatus : lineStatusList.getLineStatusList()) {
                    Log.d("VIEW LINE STATUS", "Counter: " + counter);
                    String name = lineStatus.getLine().getName();
                    String status = lineStatus.getStatus().getDescription();

                    TableRow currentRow = (TableRow)tableLayout.getChildAt(counter);
                    TextView tubeLineName = (TextView)currentRow.getVirtualChildAt(0);
                    tubeLineName.setText(name);
                    tubeLineName.setBackgroundColor(TflLineColours.getColourForLineName(name));

                    TextView tubeLineStatus = (TextView)currentRow.getVirtualChildAt(1);
                    tubeLineStatus.setText(status);
                    counter++;

                }

            } catch (Exception e) {
                Log.d("error", e.getLocalizedMessage());
            }
        }
    }

    /*
     * Reads stream passed and appends to a String, if any exception is thrown
     * returns a null string.
     */
    private String convertStreamToString(InputStream is) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString().trim();
        } catch (UnsupportedEncodingException e1) {
            Log.d("error", e1.getLocalizedMessage());
        } catch (IOException e) {
            Log.d("error", e.getLocalizedMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.d("error", e.getLocalizedMessage());
            }
        }
        return null;
    }
}
