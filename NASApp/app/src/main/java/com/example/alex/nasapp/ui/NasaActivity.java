package com.example.alex.nasapp.ui;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.alex.nasapp.R;
import com.example.alex.nasapp.model.asteroid.Asteroid;
import com.example.alex.nasapp.model.rover.Photo;
import com.example.alex.nasapp.ui.asteroid.AsteroidListFragment;
import com.example.alex.nasapp.ui.asteroid.SMSAlertFragment;
import com.example.alex.nasapp.ui.eye_in_the_sky.SatellitePhotoFragment;
import com.example.alex.nasapp.ui.eye_in_the_sky.SelectLatLongFragment;
import com.example.alex.nasapp.ui.rover.CreatePostcardFragment;
import com.example.alex.nasapp.ui.rover.RoverImageryFragment;
import com.google.android.gms.maps.model.LatLng;

//activity controlling fragments, which implement app functionality
public class NasaActivity extends AppCompatActivity
        implements RoverImageryFragment.OnCreatePostcardListener,
                    SelectLatLongFragment.OnLatLongSelectedListener,
                    AsteroidListFragment.OnSendSMSAlertListener
                    {

    FragmentManager fragmentManager;
    Fragment fragment = null;
    public static final String SELECTED_FEATURE_ID = "selected_feature_id";
    public static final String TAG_CURRENT_FRAGMENT = "current_fragment";
    public static final String TAG_ROVER_IMAGERY_FRAGMENT = "rover_imagery_fragment";
    public static final String TAG_SELECT_LAT_LONG_FRAGMENT = "select_lat_long_fragment";
    public static final String TAG_ASTEROID_LIST_FRAGMENT = "asteroid_list_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa);

        fragmentManager = getSupportFragmentManager();

        fragment = fragmentManager.findFragmentByTag(TAG_CURRENT_FRAGMENT);

        if (fragment == null) {

            int selectedFeatureId = getIntent().getIntExtra(SELECTED_FEATURE_ID, 0);

            switch (selectedFeatureId) {
                case 10:
                    fragment = fragmentManager.findFragmentByTag(TAG_ROVER_IMAGERY_FRAGMENT);
                    if (fragment == null) {
                        fragment = new RoverImageryFragment();
                    }
                    break;

                case 11:
                    fragment = fragmentManager.findFragmentByTag(TAG_SELECT_LAT_LONG_FRAGMENT);
                    if (fragment == null) {
                        fragment = new SelectLatLongFragment();
                    }
                    break;

                case 12:
                    fragment = fragmentManager.findFragmentByTag(TAG_ASTEROID_LIST_FRAGMENT);
                    if (fragment == null) {
                        fragment = new AsteroidListFragment();
                    }
                    break;

                default:
                    Toast.makeText(this, "Some error has occured:(", Toast.LENGTH_SHORT).show();
            }
        }

        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentPlaceholder, fragment, TAG_CURRENT_FRAGMENT)
                    .commit();
        }
    }


    @Override
    public void createPostcard(Photo photo) {

            CreatePostcardFragment createPostcardFragment = CreatePostcardFragment.newInstance(photo.getImgSrc());
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(TAG_ROVER_IMAGERY_FRAGMENT)
                    .replace(R.id.fragmentPlaceholder, createPostcardFragment, TAG_CURRENT_FRAGMENT)
                    .commit();
    }

    @Override
    public void showSatellitePhoto(LatLng latLng) {
        Fragment fragment = SatellitePhotoFragment.newInstance(latLng);
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentPlaceholder, fragment, TAG_CURRENT_FRAGMENT)
                .addToBackStack(TAG_SELECT_LAT_LONG_FRAGMENT)
                .commit();
    }

    @Override
    public void sendSMSAlert(Asteroid asteroid) {
        Fragment fragment = SMSAlertFragment.newInstance(asteroid);
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentPlaceholder, fragment, TAG_CURRENT_FRAGMENT)
                .addToBackStack(TAG_ASTEROID_LIST_FRAGMENT)
                .commit();
    }
}
