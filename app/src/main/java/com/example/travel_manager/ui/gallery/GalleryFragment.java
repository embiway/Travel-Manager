package com.example.travel_manager.ui.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.travel_manager.R;
import com.example.travel_manager.profile;
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
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class GalleryFragment extends Fragment {

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = (ImageView) view.findViewById(R.id.ProfilePic);
        Username = (EditText)view.findViewById(R.id.username);
        Description = (EditText)view.findViewById(R.id.description);
        Bio = (EditText)view.findViewById(R.id.Bio);
        Email = (TextView)view.findViewById(R.id.email);
        auth = FirebaseAuth.getInstance();
        Email.setText(auth.getCurrentUser().getEmail());
        firebaseDatabase = FirebaseDatabase.getInstance();
        uid = auth.getUid().toString();
        databaseReference = firebaseDatabase.getReference().child(uid+"photo");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("photo");
        databaseReference.addChildEventListener(new ChildEventListener() {
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

        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(image!=null) {
                    StorageReference photoref = storageReference.child(uid);
                    photoref.putFile(image).addOnSuccessListener(getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
        });

        view.findViewById(R.id.ProfilePic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER1);
                Toast.makeText(getActivity(), "keep working", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER1 && resultCode == RESULT_OK)
        {
            image = data.getData();
            Picasso.get().load(image).into(imageView);
        }
    }
}