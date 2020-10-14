package com.example.travel_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class TourismPlaceAdapter  extends ArrayAdapter<TourismPlace>{

    public  TourismPlaceAdapter(Context context, ArrayList<TourismPlace> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TourismPlace user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.place_view, parent, false);
        }
        // Lookup view for data population
        TextView Name = (TextView) convertView.findViewById(R.id.Name);
        TextView Vicinity = (TextView) convertView.findViewById(R.id.Vicinity);
        // Populate the data into the template view using the data object

        Name.setText(user.name);
        Vicinity.setText(user.vicinity);
        // Return the completed view to render on screen
        return convertView;
    }
}