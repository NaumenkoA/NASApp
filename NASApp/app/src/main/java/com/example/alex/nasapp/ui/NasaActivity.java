package com.example.alex.nasapp.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.alex.nasapp.R;
import com.example.alex.nasapp.model.rover.Photo;
import com.example.alex.nasapp.ui.asteroid.AsteroidListFragment;
import com.example.alex.nasapp.ui.eye_in_the_sky.SatellitePhotoFragment;
import com.example.alex.nasapp.ui.eye_in_the_sky.SelectLatLongFragment;
import com.example.alex.nasapp.ui.rover.CreatePostcardFragment;
import com.example.alex.nasapp.ui.rover.RoverImageryFragment;
import com.google.android.gms.maps.model.LatLng;

public class NasaActivity extends AppCompatActivity
        implements RoverImageryFragment.OnCreatePostcardListener,
                    SelectLatLongFragment.OnLatLongSelectedListener {

    FragmentManager fragmentManager;
    public static final String SELECTED_FEATURE_ID = "selected_feature_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa);

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        int selectedFeatureId = getIntent().getIntExtra(SELECTED_FEATURE_ID, 0);

        switch (selectedFeatureId) {
            case 10:
                fragment = new RoverImageryFragment();
                break;

            case 11:
                fragment = new SelectLatLongFragment();
                break;

            case 12:
                fragment = new AsteroidListFragment();
                break;

            default:
                Toast.makeText(this, "Some error has occured:(", Toast.LENGTH_SHORT).show();
        }

        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentPlaceholder, fragment)
                    .commit();
        }
    }

    @Override
    public void createPostcard(Photo photo) {
        CreatePostcardFragment createPostcardFragment = CreatePostcardFragment.newInstance(photo.getImgSrc());
        fragmentManager.beginTransaction().replace(R.id.fragmentPlaceholder, createPostcardFragment)
                .commit();
    }

    @Override
    public void showSatellitePhoto(LatLng latLng) {
        Fragment fragment = SatellitePhotoFragment.newInstance(latLng);
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentPlaceholder, fragment)
                .commit();
    }
}
