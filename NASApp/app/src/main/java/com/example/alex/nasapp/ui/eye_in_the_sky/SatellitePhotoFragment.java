package com.example.alex.nasapp.ui.eye_in_the_sky;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.alex.nasapp.R;
import com.example.alex.nasapp.api.Service;
import com.example.alex.nasapp.model.eye_in_the_sky.SatellitePhoto;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SatellitePhotoFragment extends Fragment {


    private static final String SELECTED_LAT_LONG = "selected_lat_long";
    public static final String SATELLITE_PHOTO = "satellite_photo";

    ImageView photoImageView;
    TextView dateTextView;
    LinearLayout container;
    ProgressBar progressBar;
    SatellitePhoto satellitePhoto;

    public SatellitePhotoFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //rotation handling
        outState.putParcelable(SATELLITE_PHOTO, satellitePhoto);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_satellite_photo, container, false);

        //rotation handling
        if (savedInstanceState != null) {
            satellitePhoto = savedInstanceState.getParcelable(SATELLITE_PHOTO);
        }

        this.container = (LinearLayout) rootView.findViewById(R.id.photoImageContainer);
        photoImageView = (ImageView) rootView.findViewById(R.id.photoImageView);
        dateTextView = (TextView) rootView.findViewById(R.id.photoDateTextView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        LatLng latLng = getArguments().getParcelable(SELECTED_LAT_LONG);

        if (satellitePhoto != null) {
            showSatellitePhotoData();
        } else {
            loadImageFromSatellite(latLng);
        }
        return rootView;
    }


    private void loadImageFromSatellite(LatLng latLng) {
        showLoading (true);
        //making API call
        //API returns error when trying to get photo on the latest date, so the date needs to be added to the query. The error is: "No imagery for specified date."
        Service.getNasaApi().getSatellitePhoto((float) latLng.latitude, (float) latLng.longitude, "2017-05-01")
                .enqueue(new Callback<SatellitePhoto>() {

                    @Override
                    public void onResponse(Call<SatellitePhoto> call, Response<SatellitePhoto> response) {
                        onSuccessResponse(response);
                    }

                    @Override
                    public void onFailure(Call<SatellitePhoto> call, Throwable t) {
                        onFailureResponse();
                    }
                });
    }

    private void onSuccessResponse(Response<SatellitePhoto> response) {
        if (response.code() == HttpURLConnection.HTTP_OK) {
            satellitePhoto = response.body();
            if (satellitePhoto != null) {
                showSatellitePhotoData();
            }
        } else {
            onFailureResponse();
        }
    }

    private void showSatellitePhotoData() {
        if (satellitePhoto.getUrl() == null || satellitePhoto.getDate() == null){
            latLongInputIsUncorrect();
        } else {
            Picasso.with(getActivity()).load(satellitePhoto.getUrl())
                    .into(photoImageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            showLoading(false);
                        }

                        @Override
                        public void onError() {
                            showLoading(false);
                        }
                    });
            String date = satellitePhoto.getDate().substring(0, 10);
            dateTextView.setText(getActivity().getResources().getString(R.string.photo_date, date));
        }
    }

    private void latLongInputIsUncorrect() {
        showLoading(false);
        Snackbar.make(container, getResources().getString(R.string.internet_error_message), Snackbar.LENGTH_LONG).show();
    }

    private void onFailureResponse() {
        showLoading(false);
        Snackbar.make(container, getResources().getString(R.string.lat_long_input_error_message), Snackbar.LENGTH_LONG).show();
}

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            container.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            container.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public static SatellitePhotoFragment newInstance (LatLng latLng) {
        SatellitePhotoFragment satellitePhotoFragment = new SatellitePhotoFragment();
        Bundle args = new Bundle();
        args.putParcelable(SELECTED_LAT_LONG, latLng);
        satellitePhotoFragment.setArguments(args);
        return satellitePhotoFragment;
    }

}
