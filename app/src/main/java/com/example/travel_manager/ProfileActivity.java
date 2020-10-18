package com.example.travel_manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.squareup.picasso.Picasso;

// An Activity used to show profile pic and other details  , used firebase  storage and database to store details/
public class ProfileActivity extends AppCompatActivity {

    ImageView imageView;
    private FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ChildEventListener childEventListener;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Uri image;
    String uid;
    EditText Username,Description,Bio;
    TextView Email;
    private static final int RC_PHOTO_PICKER =  2,RC_PHOTO_PICKER1=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageView = (ImageView)findViewById(R.id.ProfilePic);
        Username = (EditText)findViewById(R.id.username);
        Description = (EditText)findViewById(R.id.description);
        Bio = (EditText)findViewById(R.id.Bio);
        Email = (TextView)findViewById(R.id.email);
        auth = FirebaseAuth.getInstance();
        Email.setText(auth.getCurrentUser().getEmail());
        firebaseDatabase = FirebaseDatabase.getInstance();
        uid = auth.getUid().toString();
        databaseReference = firebaseDatabase.getReference().child(uid+"photo");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("photo");
        databaseReference.addChildEventListener(new ChildEventListener() {//Listener continuously checking for a change in database and updating the view for user
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

//                String x = dataSnapshot.getValue(String.class);
//                Picasso.get().load(x).into(imageView);
                if(dataSnapshot!=null) {
                    profile p = dataSnapshot.getValue(profile.class);
                    Picasso.get().load(p.PhotoUrl).into(imageView);
                    Username.setText(p.name);
                    Description.setText(p.description);
                    Bio.setText(p.Bio);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public   void  imageSelect(View view) // opens an intent to select photo from gallery
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER1);
        Toast.makeText(this, "keep working", Toast.LENGTH_SHORT).show();
    }
    public  void  savePhoto(View view) // saves photo back to firebase storage and generated  image url for stored image and stored back in database
    {
        if(image!=null) {
            StorageReference photoref = storageReference.child(uid);
            photoref.putFile(image).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUrl = uri.toString();
                            //createNewPost(imageUrl);
                            String x = imageUrl;
                           Picasso.get().load(x).into(imageView);
//                            databaseReference.push().setValue(x);
                            profile p = new profile(Username.getText().toString(),Description.getText().toString(),Bio.getText().toString(),imageUrl);
                             databaseReference.push().setValue(p);
                        }
                    });
                }
            });
        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == RC_PHOTO_PICKER1 && resultCode == RESULT_OK )
        {
           image = data.getData();
            Picasso.get().load(image).into(imageView);
        }


    }

    }