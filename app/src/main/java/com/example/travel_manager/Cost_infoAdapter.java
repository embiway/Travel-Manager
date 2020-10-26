package com.example.travel_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class Cost_infoAdapter   extends ArrayAdapter<Cost_info>{
    public  Cost_infoAdapter(Context context, ArrayList<Cost_info> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Cost_info user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trip_info, parent, false);
        }
        // Lookup view for data population
        TextView place = (TextView) convertView.findViewById(R.id.PlaceName);
        TextView Cost = (TextView) convertView.findViewById(R.id.cost);
        // Populate the data into the template view using the data object

        place.setText(user.placeName);
        Cost.setText(user.Cost);
        // Return the completed view to render on screen
        return convertView;
    }
}

