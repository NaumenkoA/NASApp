package com.example.alex.nasapp.ui.eye_in_the_sky;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.alex.nasapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SelectLatLongFragment extends Fragment implements OnMapReadyCallback{

    LatLng selectedLatLng;
    EditText latEditText;
    EditText longEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_select_lat_long, container, false);

        latEditText = (EditText) rootView.findViewById(R.id.latEditText);
        longEditText = (EditText) rootView.findViewById(R.id.longEditText);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                googleMap.clear();
                selectedLatLng = latLng;
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.addMarker(new MarkerOptions().position(latLng));
                latEditText.setText(String.valueOf(latLng.latitude));
                longEditText.setText(String.valueOf(latLng.longitude));
            }
        });
    }
}



