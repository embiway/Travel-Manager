package com.example.travel_manager;

import androidx.appcompat.app.AppCompatActivity;

// <<<<<<< tripupdate
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
// =======
// import android.app.Activity;
// import android.app.ProgressDialog;
// import android.content.Intent;
// import android.net.Uri;
// import android.os.AsyncTask;
// import android.os.Bundle;
// import android.util.JsonReader;
// import android.util.Log;
// import android.view.View;
// import android.widget.AdapterView;
// import android.widget.ListView;
// import android.widget.Toast;

// import com.google.gson.JsonArray;
// >>>>>>> master

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    ListView listView;
// <<<<<<< tripupdate
    private Button mytrip,ProfileSetting;
    private static  String USGS_REQUEST_URL =
            "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=19.0760,72.8777&radius=1500&type=food,restaurant&key=AIzaSyDP2SUMWv48KVcqTwQ096eO5AzuJ3UUuV0";
    Double latitude;
    Double longitude;
// =======
//     private static  String USGS_REQUEST_URL =
//             "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=19.0760,72.8777&radius=1500&type=food,restaurant&key=AIzaSyDP2SUMWv48KVcqTwQ096eO5AzuJ3UUuV0";
// >>>>>>> master
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
// <<<<<<< tripupdate
        mytrip=findViewById(R.id.mytrip);
        ProfileSetting = findViewById(R.id.save);
// =======
// >>>>>>> master
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            USGS_REQUEST_URL = extras.getString("USGS_REQUEST_URL");
        }
        listView = (ListView) findViewById(R.id.listview);
        ArrayList<TourismPlace> list = new ArrayList<TourismPlace>();
// <<<<<<< tripupdate
        //  Toast.makeText(this, Integer.toString(list.size()), Toast.LENGTH_SHORT).show();
// =======
//       //  Toast.makeText(this, Integer.toString(list.size()), Toast.LENGTH_SHORT).show();
// >>>>>>> master
//        TourismPlace u1 = new TourismPlace("icon","Delhi","near taj hotel","https://developers.google.com/places/web-service/supported_types#table2");
//        TourismPlace u2 = new TourismPlace("icon","Mumbai","near taj hotel","https://developers.google.com/places/web-service/supported_types#table2");
//        list.add(u1);
//        list.add(u2);

        TourismPlaceAdapter adapter = new TourismPlaceAdapter(this,list);
        listView.setAdapter(adapter);
// <<<<<<< tripupdate
        mytrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuActivity.this,MyTrip.class);
                intent.putExtra("list", list); //passing list
                startActivity(intent);
            }
        });
        ProfileSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

// =======
// >>>>>>> master
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TourismPlace selectedItem = (TourismPlace) parent.getItemAtPosition(position);
                // listview.setText("The best football player is : " + selectedItem);
                String str = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+selectedItem.photoUrl+"&key=AIzaSyDP2SUMWv48KVcqTwQ096eO5AzuJ3UUuV0";
//                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
//                httpIntent.setData(Uri.parse(str));
//                startActivity(httpIntent);
// <<<<<<< tripupdate
                Intent intent = new Intent(MenuActivity.this , PlaceImageActivity.class);
                intent.putExtra("photoUrl", str);
                intent.putExtra("latitude",selectedItem.latitude);
                intent.putExtra("longitude",selectedItem.longitude);
// =======
//                 Intent intent = new Intent(MenuActivity.this , PlaceImageandInfo.class);
//                 intent.putExtra("photoUrl", str);
// >>>>>>> master
                startActivity(intent);
            }

        });
        TourAsyncTask task = new TourAsyncTask();
        task.execute();
    }
    private void updateUi(ArrayList<TourismPlace> list) {
        //Toast.makeText(this, list.size(), Toast.LENGTH_SHORT).show();
        Log.i("cool",Integer.toString(list.size()));
        TourismPlaceAdapter adapter = new TourismPlaceAdapter(this,list);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }
    private class TourAsyncTask extends AsyncTask<URL, String, String> {

        ProgressDialog progDailog = new ProgressDialog(MenuActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }
        @Override
        protected String doInBackground(URL... urls) {
            // Create URL object

            URL url = createUrl(USGS_REQUEST_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }
            // Extract relevant fields from the JSON response and create an {@link Event} object
            Log.i("MY NAME IS SHUBHAM GUPTA",jsonResponse);
            progDailog.cancel();
            return jsonResponse;
        }

        /**
         * Update the screen with the given earthquake (which was the result of the
         * {@link}).
         */
        @Override
        protected void onPostExecute(String earthquake) {
            if (earthquake == null) {
                return;
            }
            Log.i("MY NAME IS SHUBHAM GUPTA",earthquake);
            ArrayList<TourismPlace> list = extractFeatureFromJson(earthquake);
            Log.i("djfeknfe",Integer.toString(list.size()));
            updateUi(list);
        }

        /**
         * Returns new URL object from the given string URL.
         */
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e("", "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;

            if(url == null)
                return jsonResponse;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } catch (IOException e) {
                // TODO: Handle the exception
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        /**
         * Return an {@link Event} object by parsing out information
         * about the first earthquake from the input earthquakeJSON string.
         */
        public  ArrayList<TourismPlace> extractFeatureFromJson(String tourPlaceJSON) {
            ArrayList<TourismPlace> tourismPlaces = new ArrayList<TourismPlace>();

            // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
            try {
                JSONObject root = new JSONObject(tourPlaceJSON);
                JSONArray results = root.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject object = results.getJSONObject(i);
                    String name = object.getString("name");
                    String icon = object.getString("icon");
                    String vicinity = object.getString("vicinity");
                    JSONArray PhotoArray = object.getJSONArray("photos");
                    JSONObject PhotoObj = PhotoArray.getJSONObject(0);
                    String url = PhotoObj.getString("photo_reference");
// <<<<<<< tripupdate
                    JSONObject geometryObject = object.getJSONObject("geometry");
                    JSONObject locationObject = geometryObject.getJSONObject("location");
                    Double latitude,longitude;
                    latitude = locationObject.getDouble("lat");
                    longitude = locationObject.getDouble("lng");
                    TourismPlace x = new TourismPlace(icon, name, vicinity, url,latitude,longitude);
// =======
//                     TourismPlace x = new TourismPlace(icon, name, vicinity, url);
// >>>>>>> master
                    tourismPlaces.add(x);
                }
            }catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }

            // Return the list of earthquakes
            return tourismPlaces;
        }
    }

}