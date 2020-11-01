package com.example.travel_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

// An Activity Intended to show images of a  tourism place
public class PlaceImageActivity extends AppCompatActivity {

    String photourl;
    private static String USGS_REQUEST_URL="";
    ImageView imageView,imageWeather;
    Double latitude;
    Double longitude;
    String place;
    TextView textView,des,temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_image);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            photourl = extras.getString("photoUrl");
            latitude = extras.getDouble("latitude");
            longitude = extras.getDouble("longitude");
            place  = extras.getString("place");
        }
        USGS_REQUEST_URL = "https://api.openweathermap.org/data/2.5/weather?lat="+latitude.toString()+"&lon="+longitude.toString()+"&appid=1973b55da1e58fa50358243291a5f3cc";
        imageView = (ImageView)findViewById(R.id.PlaceImage);
        imageWeather = (ImageView)findViewById(R.id.imageweather);
        des = (TextView)findViewById(R.id.des);
        temp = (TextView)findViewById(R.id.temp);
        textView = (TextView)findViewById(R.id.tv_name);
        //imageView.setImageResource(R.drawable.ic_launcher_background);
        textView.setText(place);
        Picasso.get().load(photourl).into(imageView);
        Toast.makeText(this, Double.toString(latitude)  +" "+ Double.toString(longitude), Toast.LENGTH_SHORT).show();
        PlaceAsyncTask task = new PlaceAsyncTask();  // executing asynctask to download  details of city
        task.execute();
    }
    private void updateUi(ArrayList<TourismPlace> list) {
//        //Toast.makeText(this, list.size(), Toast.LENGTH_SHORT).show();
//        Log.i("cool",Integer.toString(list.size()));
//        TourismPlaceAdapter adapter = new TourismPlaceAdapter(this,list);
//        adapter.notifyDataSetChanged();
//        listView.setAdapter(adapter);
    }
    private class PlaceAsyncTask extends AsyncTask<URL, String, String> {// TourAsynctask  extending  Asynctask

        ProgressDialog progDailog = new ProgressDialog(PlaceImageActivity.this);
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

            Log.i("sasdwfd",USGS_REQUEST_URL);
            URL url = createUrl(USGS_REQUEST_URL);
            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
                Log.i("UR:",jsonResponse);
            } catch (IOException e) {
                // TODO Handle the IOException
            }
            // Extract relevant fields from the JSON response and create an {@link Event} object
            Log.i("MY NAME IS SHUBHAM GUPTA","vg");
            if(jsonResponse=="")
            Log.i("MY NAME IS SHUBHAM GUPTA","jsresponse");
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
            Log.i("xxx",earthquake);
            extractFeatureFromJson(earthquake);
//            ArrayList<TourismPlace> list = extractFeatureFromJson(earthquake);
//            Log.i("djfeknfe",Integer.toString(list.size()));
//            updateUi(list);
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
            if(jsonResponse==null)
            Toast.makeText(PlaceImageActivity.this,"jsonResponse", Toast.LENGTH_SHORT).show();
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
                JSONArray results = root.getJSONArray("weather");
                JSONObject object = results.getJSONObject(0);
                String icon = object.getString("icon");
                String description = object.getString("description");
                JSONObject main = root.getJSONObject("main");
                Double temp_min = main.getDouble("temp_min") - 273.15;
                Double temp_max = main.getDouble("temp_max") - 273.15;
                String tmin = temp_min.toString();
                String tmax = temp_max.toString();
               // Log.i("ress", description+"  "+Double.toString(temp)+ " "+icon);
                des.setText(description);
                String iconurl = "https://openweathermap.org/img/wn/"+icon+"@2x.png";
                Picasso.get().load(iconurl).into(imageWeather);
                temp.setText("min:"+temp_min.toString()+"C"+"/max:"+temp_max.toString()+"C");
//                for (int i = 0; i < results.length(); i++) {
//                    JSONObject object = results.getJSONObject(i);
//                    String name = object.getString("name");
//                    String icon = object.getString("icon");
//                    String vicinity = object.getString("vicinity");
//                    JSONArray PhotoArray = object.getJSONArray("photos");
//                    JSONObject PhotoObj = PhotoArray.getJSONObject(0);
//                    String url = PhotoObj.getString("photo_reference");
//                    JSONObject geometryObject = object.getJSONObject("geometry");
//                    JSONObject locationObject = geometryObject.getJSONObject("location");
//                    Double latitude,longitude;
//                    latitude = locationObject.getDouble("lat");
//                    longitude = locationObject.getDouble("lng");
//                    TourismPlace x = new TourismPlace(icon, name, vicinity, url,latitude,longitude);
//                    tourismPlaces.add(x);
//                }
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