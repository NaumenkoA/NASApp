package com.example.alex.nasapp.ui.eye_in_the_sky;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.alex.nasapp.R;
import com.example.alex.nasapp.ui.rover.RoverImageryFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;

import static com.example.alex.nasapp.R.string.lat_long_input_error_message;
import static com.example.alex.nasapp.R.string.latitude;


public class SelectLatLongFragment extends Fragment implements OnMapReadyCallback {

    public interface OnLatLongSelectedListener {
    void showSatellitePhoto(LatLng latLng);
}

public SelectLatLongFragment () {

}

public static final String SELECTED_LAT_LONG = "selected_lat_long";
public static final String CAMERA_ZOOM_LEVEL = "camera_zoom_level";

    OnLatLongSelectedListener listener;

    LinearLayout rootLayout;
    LatLng selectedLatLng;
    EditText latEditText;
    EditText longEditText;
    Button showImageButton;
    SupportMapFragment mapFragment;
    GoogleMap googleMap;
    Float cameraZoom;

    private DisposableObserver <Boolean> disposable;
    Observable<CharSequence> observableLatEditText;
    Observable<CharSequence> observableLongEditText;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SELECTED_LAT_LONG, selectedLatLng);
        if (googleMap != null) {
            outState.putFloat(CAMERA_ZOOM_LEVEL, googleMap.getCameraPosition().zoom);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_select_lat_long, container, false);

        if (savedInstanceState != null) {
            selectedLatLng = savedInstanceState.getParcelable(SELECTED_LAT_LONG);
            cameraZoom = savedInstanceState.getFloat(CAMERA_ZOOM_LEVEL);
        }

        rootLayout = (LinearLayout) rootView.findViewById(R.id.rootLayout);
        latEditText = (EditText) rootView.findViewById(R.id.latEditText);
        longEditText = (EditText) rootView.findViewById(R.id.longEditText);
        showImageButton = (Button) rootView.findViewById(R.id.showImageButton);

        observableLatEditText = RxTextView.textChanges(latEditText);
        observableLongEditText = RxTextView.textChanges(longEditText);

        mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(this);
        getChildFragmentManager().beginTransaction().replace(R.id.mapContainer, mapFragment).commit();

        showImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double latitude = Double.parseDouble(latEditText.getText().toString());
                    double longitude = Double.parseDouble(longEditText.getText().toString());
                    listener.showSatellitePhoto(new LatLng(latitude, longitude));
                } catch (NumberFormatException e) {
                    Snackbar.make(rootLayout, getResources().getString(lat_long_input_error_message), Snackbar.LENGTH_LONG).show();
                }

            }
        });


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnLatLongSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnLatLongSelectedListener");
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        disposable = new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean value) {
                showImageButton.setEnabled(value);
            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onComplete() {

            }
        };

        Observable.combineLatest(
                observableLatEditText, observableLongEditText, new BiFunction<CharSequence, CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(CharSequence charSequence, CharSequence charSequence2) {
                        return  (charSequence.length() > 0
                                && charSequence2.length() > 0);

                    }
                }).subscribe (disposable);

    }

    @Override
    public void onStop() {
        super.onStop();
        disposable.dispose();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        this.googleMap = googleMap;

        if (selectedLatLng != null) {
            showPositionOnMap(selectedLatLng, googleMap);
        }

        if (cameraZoom != null) {
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(cameraZoom));
        }

       this.googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                googleMap.clear();
                selectedLatLng = latLng;
                showPositionOnMap(latLng, googleMap);
                latEditText.setText(String.valueOf(latLng.latitude));
                longEditText.setText(String.valueOf(latLng.longitude));
            }
        });
    }

    private void showPositionOnMap(LatLng latLng, GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.addMarker(new MarkerOptions().position(latLng));
    }

}



