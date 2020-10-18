package com.example.travel_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
//CustomList Adapter corresponding to tourism class object used to  show MenuActivity lisitems
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
        ImageView icon  = (ImageView)convertView.findViewById(R.id.Icon);
        // Populate the data into the template view using the data object

        Name.setText(user.name);
        Vicinity.setText(user.vicinity);
        Picasso.get().load(user.icon).into(icon);
        // Return the completed view to render on screen
        return convertView;
    }
}