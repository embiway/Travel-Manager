package com.example.travel_manager;

import android.app.Activity;
import android.content.Intent;
// <<<<<<< tripupdate
// =======
import android.graphics.BitmapFactory;
// >>>>>>> master
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
// <<<<<<< tripupdate
// =======
import android.widget.Button;
import android.widget.Toast;
// >>>>>>> master

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

// <<<<<<< tripupdate
// =======
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
// >>>>>>> master
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
// <<<<<<< tripupdate
// =======
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
// >>>>>>> master
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
// <<<<<<< tripupdate

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

/**
 * Use the places plugin to take advantage of Mapbox's location search ("geocoding") capabilities. The plugin
 * automatically makes geocoding requests, has built-in saved locations, includes location picker functionality,
 * and adds beautiful UI into your Android project.
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {//

    private static String USGS_REQUEST_URL =
            "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=19.0760,72.8777&radius=1500&type=food,restaurant&key=AIzaSyDP2SUMWv48KVcqTwQ096eO5AzuJ3UUuV0";
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private CarmenFeature home;
    private CarmenFeature work;
    private String geojsonSourceLayerId = "geojsonSourceLayerId";
    private String symbolIconId = "symbolIconId";
     private  LatLng latLing ;
    double latitude;
    double longitude;
    private static  final String TAG="MainActivity";
     private  static  final int ERROR_DIALOG_REQUEST=9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String datas = extras.getString("CITY_NAME");
            if (datas != null) {
                //Toast.makeText(this, datas, Toast.LENGTH_SHORT).show();
                //Log.i("sdv",datas);

            }
        }
            Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_main);

// =======
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private static String USGS_REQUEST_URL;
    // variables for adding location layer
    private MapView mapView;
    private MapboxMap mapboxMap;
    private String geojsonSourceLayerId = "geojsonSourceLayerId";
    // variables for adding location layer
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;
    // variables for calculating and drawing a route
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
    // variables needed to initialize navigation
    private Button button;
    double latitude;
    double longitude;
    private  LatLng latLing ;
    private String symbolIconId = "symbolIconId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_main);
// >>>>>>> master
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

// <<<<<<< tripupdate
//     @Override
//     public void onMapReady(@NonNull final MapboxMap mapboxMap) {
//         this.mapboxMap = mapboxMap;
//         mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
//             @Override
//             public void onStyleLoaded(@NonNull Style style) {
//                 initSearchFab();

//                 // Add the symbol layer icon to map for future use
// //                style.addImage(symbolIconId, BitmapFactory.decodeResource(
// //                        MainActivity.this.getResources(), R.mipmap.ic_launcher));

//                 // Create an empty GeoJSON source using the empty feature collection
//                 setUpSource(style);

//                 // Set up a new symbol layer for displaying the searched location's feature coordinates
//                 setupLayer(style);
// =======
    //method when map is ready
    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(getString(R.string.navigation_guidance_day), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);

                initSearchFab();
                setupLayer(style);
                setUpSource(style);
                addDestinationIconSymbolLayer(style);

                mapboxMap.addOnMapClickListener(MainActivity.this);
                button = findViewById(R.id.startButton);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean simulateRoute = true;
                        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                .directionsRoute(currentRoute)
                                .shouldSimulateRoute(simulateRoute)
                                .build();
                        NavigationLauncher.startNavigation(MainActivity.this, options);
                    }
                });
// >>>>>>> master
            }
        });
    }

// <<<<<<< tripupdate
// =======
    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
                GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
                loadedMapStyle.addSource(geoJsonSource);
                SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
                destinationSymbolLayer.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);
    }

    @SuppressWarnings( {"MissingPermission"})
    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        Point destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());

        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }

        getRoute(originPoint, destinationPoint);
        button.setEnabled(true);
        button.setBackgroundResource(R.color.mapboxBlue);
        return true;
    }

// >>>>>>> master
    private void initSearchFab() {
        findViewById(R.id.fab_location_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken(Mapbox.getAccessToken() != null ? Mapbox.getAccessToken() : getString(R.string.mapbox_access_token))
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                .limit(10)
                                .build(PlaceOptions.MODE_CARDS))
                        .build(MainActivity.this);
                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
            }
        });
    }

// <<<<<<< tripupdate
//     private void setUpSource(@NonNull Style loadedMapStyle) {
//         loadedMapStyle.addSource(new GeoJsonSource(geojsonSourceLayerId));
//     }

//     private void setupLayer(@NonNull Style loadedMapStyle) {
//         loadedMapStyle.addLayer(new SymbolLayer("SYMBOL_LAYER_ID", geojsonSourceLayerId).withProperties(
//                 iconImage(symbolIconId),
//                 iconOffset(new Float[]{0f, -8f})
//         ));
//     }

// =======
// >>>>>>> master
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {

            // Retrieve selected location's CarmenFeature
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);

            // Create a new FeatureCollection and add a new Feature to it using selectedCarmenFeature above.
            // Then retrieve and update the source designated for showing a selected location's symbol layer icon

            if (mapboxMap != null) {
                Style style = mapboxMap.getStyle();
                if (style != null) {
                    GeoJsonSource source = style.getSourceAs(geojsonSourceLayerId);
                    if (source != null) {
                        source.setGeoJson(FeatureCollection.fromFeatures(
                                new Feature[]{Feature.fromJson(selectedCarmenFeature.toJson())}));
                    }

                    // Move map camera to the selected location
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target( latLing =new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),
                                            ((Point) selectedCarmenFeature.geometry()).longitude()))
                                    .zoom(14)
                                    .build()), 4000);
                    latitude = latLing.getLatitude();
                    longitude = latLing.getLongitude();
                           double latitude  = latLing.getAltitude();
       double longitude = latLing.getLongitude();
// <<<<<<< tripupdate
//       Log.i("geocordinates",Double.toString(latitude)+"   "+Double.toString(longitude));
//       String Location = Double.toString(latitude)+","+Double.toString(longitude);
// =======
                    Log.i("geocordinates",Double.toString(latitude)+"   "+Double.toString(longitude));
                    String Location = Double.toString(latitude)+","+Double.toString(longitude);
// >>>>>>> master
                    USGS_REQUEST_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+Location+"&radius=1500&type=food,restaurant&key=AIzaSyDP2SUMWv48KVcqTwQ096eO5AzuJ3UUuV0";
                    Intent intent = new Intent(this , MenuActivity.class);
                    intent.putExtra("USGS_REQUEST_URL", USGS_REQUEST_URL);
                    startActivity(intent);
                }
            }
        }
    }

// <<<<<<< tripupdate



//     // Add the mapView lifecycle to the activity's lifecycle methods
//     @Override
//     public void onResume() {
//         super.onResume();
//         mapView.onResume();
//     }

//     @Override
// =======
    private void setUpSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(geojsonSourceLayerId));
    }

    private void setupLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addLayer(new SymbolLayer("SYMBOL_LAYER_ID", geojsonSourceLayerId).withProperties(
                iconImage(symbolIconId),
                iconOffset(new Float[]{0f, -8f})
        ));
    }

    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

                        // Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
        // Activate the MapboxMap LocationComponent to show user location
        // Adding in LocationComponentOptions is also an optional parameter
            locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
        // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationComponent(mapboxMap.getStyle());
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }
    @Override
// >>>>>>> master
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
// <<<<<<< tripupdate
//     protected void onStop() {
//         super.onStop();
//         mapView.onStop();
//     }

//     @Override
//     public void onPause() {
// =======
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
// >>>>>>> master
        super.onPause();
        mapView.onPause();
    }

    @Override
// <<<<<<< tripupdate
//     public void onLowMemory() {
//         super.onLowMemory();
//         mapView.onLowMemory();
// =======
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
// >>>>>>> master
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
// <<<<<<< tripupdate
//     protected void onSaveInstanceState(Bundle outState) {
//         super.onSaveInstanceState(outState);
//         mapView.onSaveInstanceState(outState);
// =======
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
// >>>>>>> master
    }
}
