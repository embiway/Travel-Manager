package com.example.travel_manager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
//import com.mapbox.mapboxsdk.maps.SupportMapFragment;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
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
          //  mMap.getUiSettings().setMyLocationButtonEnabled(false);
             init();
        }
    }
 private  static final String TAG="MapActivity";
 private static  final  String FINE_LOCATION=Manifest.permission.ACCESS_FINE_LOCATION;
  private static  final String COURSE_LOCATION=Manifest.permission.ACCESS_COARSE_LOCATION;
  private static final float DEFAULT_ZOOM=15f;
  //these cordiates are choosen so that we can cover whole world
  private static final LatLngBounds LAT_LNG_BOUNDS=new LatLngBounds(new LatLng(-40,-168),new LatLng(71,136));
  //widgets yaha se shurur ha search karne wala kaam
  private AutoCompleteTextView msearchText;
     //gps ke liye
    private ImageView mGps;
  private Boolean mLocationPermissionGranted=false;
  private GoogleMap mMap;
  private FusedLocationProviderClient mfusedLocationProviderClient;
  private PlaceAutocompleteAdapter placeAutocompleteAdapter;
  private GoogleApiClient mGoogleApiClient;
  private static  final int LOCATION_PERMISSION_REQUEST_CODE=1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG ,"We are here");
        setContentView(R.layout.activity_map);
        msearchText=(AutoCompleteTextView) findViewById(R.id.input_search);
        mGps=(ImageView) findViewById(R.id.ic_gps);
      Log.d(TAG,"We are here 2");
     getLocationPermission();
      init();
  Log.d(TAG ,"get the location");
    }
     private void init()
     {
         Log.d(TAG,"intit: initializing");
         mGoogleApiClient = new GoogleApiClient
                 .Builder(this)
                 .addApi(Places.GEO_DATA_API)
                 .addApi(Places.PLACE_DETECTION_API)
                 .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                 .build();
         placeAutocompleteAdapter=new PlaceAutocompleteAdapter(this,mGoogleApiClient,LAT_LNG_BOUNDS,null);
          msearchText.setAdapter(placeAutocompleteAdapter);
         //we here overwrite return key (enter key) we are not going to use onclickListner
           msearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
               @Override
               public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if(actionId== EditorInfo.IME_ACTION_SEARCH||actionId==EditorInfo.IME_ACTION_DONE||event.getAction()==KeyEvent.ACTION_DOWN
                        ||event.getAction()==KeyEvent.KEYCODE_ENTER)
                    {
                        //execute our method for searching
                         geoLocate();
                    }
                   return false;
               }
           });
           mGps.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   getDeviceLocation();
               }
           });
     }
      private void geoLocate()
      {
          Log.d(TAG,"geoLoacte: geoLoacating");
           String searchString=msearchText.getText().toString();
          Geocoder geocoder=new Geocoder(MapActivity.this);
          List<Address>list=new ArrayList<>();
          try{
              list=geocoder.getFromLocationName(searchString,1);
          }
          catch(IOException e)
          {
              Log.e(TAG,"geoLocation: IOExcetion: "+e.getMessage());
          }
           if(list.size()>0)
           {
               Address address=list.get(0);

              // Toast.makeText(this, address.toString(),Toast.LENGTH_SHORT).show();
               moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM,address.getAddressLine(0));
           }
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
                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),DEFAULT_ZOOM,"MyLocation");
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
     private void moveCamera(LatLng latLng,float zoom,String title)
     {
          //khud ke location pe marker na aaye uske liye yhe wala do line ka code ha
          if(!title.equals("MyLocation"))
          {
              MarkerOptions options=new MarkerOptions().position(latLng).title(title);
              mMap.addMarker(options);
          }
          hideSoftKeyboard();
         Log.d(TAG,"moveCamera:moving the camera to: lat: "+latLng.latitude+" ,lng "+latLng.longitude);
          mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
         //yh jo do line ha vo jo location search kari ha uska maker drop kaarne ke liye likhi

         //
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
// keyboard chupane ke liye
private void hideSoftKeyboard()
{
    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
}


}