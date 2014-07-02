package com.example.tfl_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.tfl_test.model.LineStatus;

import java.util.List;

public class TubeLineStatusAdapter  extends ArrayAdapter<LineStatus> {

    public TubeLineStatusAdapter(Context context, int resource) {
        super(context, resource);
    }

    public TubeLineStatusAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public TubeLineStatusAdapter(Context context, int resource, LineStatus[] objects) {
        super(context, resource, objects);
    }

    public TubeLineStatusAdapter(Context context, int resource, int textViewResourceId, LineStatus[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public TubeLineStatusAdapter(Context context, int resource, List<LineStatus> objects) {
        super(context, resource, objects);
    }

    public TubeLineStatusAdapter(Context context, int resource, int textViewResourceId, List<LineStatus> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final LineStatus lineStatus = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tube_line_list, parent, false);
        }

        TextView tubeLineName = (TextView)convertView.findViewById(R.id.tubeLineName);
        tubeLineName.setText(lineStatus.getLine().getName());
        tubeLineName.setBackgroundColor(0x894e24);
        tubeLineName.invalidate();

        TextView tubeLineStatus = (TextView)convertView.findViewById(R.id.tubeLineStatus);
        tubeLineStatus.setText(lineStatus.getStatus().getDescription());
        tubeLineStatus.setBackgroundColor(0x894e24);

        return convertView;
    }
}
