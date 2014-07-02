package com.example.tfl_test;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import com.example.tfl_test.model.ArrayOfLineStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.*;

public class TflLoadLineStatusTask extends AsyncTask<String, String, String> {

    private static final String TFL_LINE_STATUS_URL = "http://cloud.tfl.gov.uk/TrackerNet/LineStatus";

    private TflTestActivity tflTestActivity;
    private ListView listView;

    public TflLoadLineStatusTask(TflTestActivity tflTestActivity, ListView listView) {
        this.tflTestActivity = tflTestActivity;
        this.listView = listView;
    }

    @Override
    protected String doInBackground(String[] objects) {

            return readTubeLineInfoFromHttpRequest();
//            return readTubeLineFromFile(tflTestActivity.getResources());

    }

    private String readTubeLineInfoFromHttpRequest() {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(TFL_LINE_STATUS_URL);
        request.setHeader("User-Agent", "set your desired User-Agent");
        InputStream inputStream = null;

        try {
            HttpResponse response = client.execute(request);

            StatusLine status = response.getStatusLine();
            if (status.getStatusCode() != 200) {
                throw new IOException("Invalid response from server: " + status.toString());
            }

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                inputStream = entity.getContent();
                return convertStreamToString(inputStream);
            }

        }  catch (IOException e) {
            Log.d("error", e.getLocalizedMessage());
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String tflXML) {

        if (tflXML != null && !tflXML.isEmpty()) {

            // big hack to get rid of first weird character
            tflXML = tflXML.substring(tflXML.indexOf("<"));

            try {

                Serializer serializer = new Persister();
                ArrayOfLineStatus lineStatus = serializer.read(ArrayOfLineStatus.class, tflXML);

                listView.setAdapter(new TubeLineStatusAdapter(tflTestActivity, android.R.layout.simple_list_item_1, lineStatus.getLineStatusList()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String readTubeLineFromFile(Resources resources) {
        AssetManager assetManager = resources.getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open("LineStatus3.xml");
            Log.d("READ", "It worked!");

            ByteArrayOutputStream content = new ByteArrayOutputStream();
            int readBytes;
            byte[] sBuffer = new byte[512];
            while ((readBytes = inputStream.read(sBuffer)) != -1) {
                content.write(sBuffer, 0, readBytes);
            }

            return new String(content.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            return sb.toString().trim();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
