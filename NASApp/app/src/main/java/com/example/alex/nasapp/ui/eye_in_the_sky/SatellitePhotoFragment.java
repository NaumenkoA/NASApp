package com.example.alex.nasapp.ui.eye_in_the_sky;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.nasapp.R;
import com.example.alex.nasapp.adapters.MarsImageryAdapter;
import com.example.alex.nasapp.api.Service;
import com.example.alex.nasapp.model.eye_in_the_sky.SatellitePhoto;
import com.example.alex.nasapp.model.rover.RoverPhotos;
import com.example.alex.nasapp.ui.rover.CreatePostcardFragment;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SatellitePhotoFragment extends Fragment {


    private static final String SELECTED_LAT_LONG = "selected_lat_long";

    ImageView photoImageView;
    TextView dateTextView;
    RelativeLayout container;
    ProgressBar progressBar;

    public SatellitePhotoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_satellite_photo, container, false);

        this.container = (RelativeLayout) rootView.findViewById(R.id.photoImageContainer);
        photoImageView = (ImageView) rootView.findViewById(R.id.photoImageView);
        dateTextView = (TextView) rootView.findViewById(R.id.photoDateTextView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        LatLng latLng = getArguments().getParcelable(SELECTED_LAT_LONG);

        loadImageFromSatellite (latLng);

        return rootView;
    }

    private void loadImageFromSatellite(LatLng latLng) {
        showLoading (true);

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
            SatellitePhoto satellitePhoto = response.body();
            if (satellitePhoto != null) {
                Picasso.with(getActivity()).load(satellitePhoto.getUrl()).into(photoImageView);
                String date = satellitePhoto.getDate().substring(0,10);
                dateTextView.setText(getActivity().getResources().getString(R.string.photo_date, date));
                showLoading(false);
            }
        } else {
            onFailureResponse();
        }
    }

    private void onFailureResponse() {
        showLoading(false);
        Toast.makeText(getActivity(), "Some error occurred. Please try again:(", Toast.LENGTH_SHORT).show();
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
