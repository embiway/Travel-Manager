package com.example.travel_manager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
//import com.mapbox.mapboxsdk.maps.SupportMapFragment;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        if (mLocationPermissionGranted) {
            getDeviceLocation();
            //blue mark karne ka liye
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
        }
    }
 private  static final String TAG="MapActivity";
 private static  final  String FINE_LOCATION=Manifest.permission.ACCESS_FINE_LOCATION;
  private static  final String COURSE_LOCATION=Manifest.permission.ACCESS_COARSE_LOCATION;
  private static final float DEFAULT_ZOOM=15f;
  private Boolean mLocationPermissionGranted=false;
  private GoogleMap mMap;
  private FusedLocationProviderClient mfusedLocationProviderClient;
  private static  final int LOCATION_PERMISSION_REQUEST_CODE=1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG ,"We are here");
        setContentView(R.layout.activity_map);
      Log.d(TAG,"We are here 2");
     getLocationPermission();
  Log.d(TAG ,"get the location");
    }
    private void getDeviceLocation()
    {
        Log.d(TAG,"getdeviceLocation:gettig the current devices location");
        mfusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
          try{
              if(mLocationPermissionGranted)
              {
                  Task location=mfusedLocationProviderClient.getLastLocation();
                   location.addOnCompleteListener(new OnCompleteListener() {
                       @Override
                       public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful())
                        {
                            Location currentLocation =(Location)task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),DEFAULT_ZOOM);
                        }
                        else
                        {
                            Toast.makeText(MapActivity.this,"unable to get current location",Toast.LENGTH_SHORT).show();
                        }
                       }
                   });
              }
          }
          catch (SecurityException e)
          {

          }
    }
     private void moveCamera(LatLng latLng,float zoom)
     {
         Log.d(TAG,"moveCamera:moving the camera to: lat: "+latLng.latitude+" ,lng "+latLng.longitude);
          mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
     }
    private  void initMap()
    {
        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
         mapFragment.getMapAsync(MapActivity.this);
    }
    //hmm yaha pe phir se explicitely check kar rha ki permissions de rakhi ha ya nahi
    private void getLocationPermission()
    {
        Log.d(TAG,"checking permissions");
        String[] permissions={Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
         if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)

         {
             if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COURSE_LOCATION)== PackageManager.PERMISSION_GRANTED)
             {
                 mLocationPermissionGranted=true;
                     initMap();
             }
             else
             {
                 ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
             }
         }
         else
         {
             ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
         }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         Log.d(TAG,"onRequestPermissionResult:callled");
        mLocationPermissionGranted=false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                  if(grantResults.length>0)
                  {
                       for(int i=0;i<grantResults.length;i++)
                       {
                           if(grantResults[i]!=PackageManager.PERMISSION_GRANTED)
                           {
                               mLocationPermissionGranted=false;
                                return;
                           }
                       }
                      mLocationPermissionGranted=true;
                      //intialize map
                       initMap();
                  }
            }
        }
    }


}