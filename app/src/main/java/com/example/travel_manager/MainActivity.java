package com.example.travel_manager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
//import com.mapbox.api.geocoding.v5.models.CarmenFeature;
//import com.mapbox.mapboxsdk.geometry.LatLng;
//import com.mapbox.mapboxsdk.maps.MapView;
//import com.mapbox.mapboxsdk.maps.MapboxMap;

/**
 * Use the places plugin to take advantage of Mapbox's location search ("geocoding") capabilities. The plugin
 * automatically makes geocoding requests, has built-in saved locations, includes location picker functionality,
 * and adds beautiful UI into your Android project.
 */
public class MainActivity extends AppCompatActivity {//

//    private static String USGS_REQUEST_URL =
//            "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=19.0760,72.8777&radius=1500&type=food,restaurant&key=AIzaSyDP2SUMWv48KVcqTwQ096eO5AzuJ3UUuV0";
//    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
//    private MapView mapView;
//    private MapboxMap mapboxMap;
//    private CarmenFeature home;
//    private CarmenFeature work;
    private String geojsonSourceLayerId = "geojsonSourceLayerId";
    private String symbolIconId = "symbolIconId";
//     private  LatLng latLing ;
    double latitude;
    double longitude;
    private static  final String TAG="MainActivity";
     private  static  final int ERROR_DIALOG_REQUEST=9001;
private Button btnmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnmap=findViewById(R.id.btnMap);
 if(isServicesOK())
 {
     Log.d(TAG,"Going to start map");
     init();
 }


    }
    private void init(){
        Log.d(TAG,"We are in Init");

         btnmap.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Log.d(TAG,"we are going to move");
                 Intent intent=new Intent(MainActivity.this,MapActivity.class);

                   startActivity(intent);
             }
         });
    }
   public  boolean isServicesOK()
   {
       Log.d(TAG,"isServicesOK: checking google services version");
        int available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
         if(available== ConnectionResult.SUCCESS)
         {
              Log.d(TAG,"isServicesOK: Google play services is working");
              return  true;
         }
         else if(GoogleApiAvailability.getInstance().isUserResolvableError(available))
         {
             Log.d(TAG,"isServiceOK: an error occured but we can fix it");
             //google dega dialog how to solve error don't take tension
             Dialog dialog=GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,ERROR_DIALOG_REQUEST);
             dialog.show();
         }
         else
         {
             Toast.makeText(this, "You can't make map request", Toast.LENGTH_SHORT).show();
         }
         return false;
   }



}
