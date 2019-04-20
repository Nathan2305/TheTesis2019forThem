package com.example.appforthem.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.example.appforthem.Clases.CircleTransform;
import com.example.appforthem.R;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.appforthem.Activities.LoginActivity.backendlessUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends android.support.v4.app.Fragment {
    ImageView photo;
    TextView datosUser;
    FloatingActionButton fab;
    public static final int REQUEST_IMAGE_CAPTURE = 101;
    static final String PREFERENCES = "PROFILE";

    int width;

    public ProfileFragment() {
        // Required empty public constructor
        // addHelper();
        // saveLocation();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("SE LLAMÓ A onCreate :");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("SE LLAMÓ A onCreateView :");
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        photo = view.findViewById(R.id.photo);
        datosUser = view.findViewById(R.id.datosUser);
        datosUser.setText(backendlessUser.getProperty("name").toString() + " " + backendlessUser.getProperty("last_name").toString());
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width / 3, width / 3);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.topMargin = (int) getResources().getDimension(R.dimen.space_action_bar_view_vertical);
        photo.setLayoutParams(layoutParams);

        if (backendlessUser.getProperty("photo") == null) {
            photo.setBackground(getResources().getDrawable(R.drawable.woman_profile2));
        } else {
            Picasso.get()
                    .load(backendlessUser.getProperty("photo").toString()).
                    transform(new CircleTransform()).resize(width / 3, width / 3).into(photo);
        }

        return view;
    }

    private void TakePhoto() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity((getActivity()).getPackageManager()) != null) {
            startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            photo.setImageBitmap(null);
            Backendless.Files.Android.upload(bitmap, Bitmap.CompressFormat.PNG, 100, backendlessUser.getEmail() + "_photo.png",
                    "profile_pictures", true, new AsyncCallback<BackendlessFile>() {
                        @Override
                        public void handleResponse(BackendlessFile response) {
                            System.out.println("File uploaded successfully.. " + " " + response.getFileURL());
                            updateUserProperty("photo", response.getFileURL());

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            showToast("Error Uploading file.. - " + fault.getMessage());
                        }
                    });
            Picasso.get()
                    .load(backendlessUser.getProperty("photo").toString()).
                    transform(new CircleTransform()).resize(width / 2, width / 2).into(photo);
        }
    }

    public void addHelper() {
        Backendless.Data.of(BackendlessUser.class).addRelation(backendlessUser, "helper_email",
                "email='helper2@gmail.com'", new AsyncCallback<Integer>() {
                    @Override
                    public void handleResponse(Integer response) {
                        Toast.makeText(getActivity(), "SUCCESS " + response.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getActivity(), "FALLÓ " + fault.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public void updateUserProperty(final String column, final String datatToUpdate) {
        backendlessUser.setProperty(column, datatToUpdate);

        Backendless.Persistence.save(backendlessUser, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                response.setProperty(column, datatToUpdate);

                showToast("SE ACTUALIZÓ 1RA-");
                Backendless.Persistence.save(response, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        showToast("SE GUARDÓ 1RA-");
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }


}



