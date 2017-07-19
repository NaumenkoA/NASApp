package com.example.alex.nasapp.ui.asteroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.alex.nasapp.R;
import com.example.alex.nasapp.adapters.AsteroidAdapter;
import com.example.alex.nasapp.adapters.MarsImageryAdapter;
import com.example.alex.nasapp.api.Service;
import com.example.alex.nasapp.model.asteroid.AsteroidList;
import com.example.alex.nasapp.model.asteroid.NearEarthObjects;
import com.example.alex.nasapp.model.rover.RoverPhotos;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsteroidListFragment extends Fragment {


    public AsteroidListFragment() {

    }

    LinearLayout linearLayout;
    ProgressBar progressBar;
    RecyclerView asteroidRecyclerView;
    AsteroidList asteroidList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_asteroid_list, container, false);

        linearLayout = (LinearLayout) rootView.findViewById(R.id.linearLayout);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        asteroidRecyclerView = (RecyclerView) rootView.findViewById(R.id.asteroidRecyclerView);

        asteroidRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        asteroidRecyclerView.setAdapter(new AsteroidAdapter(getActivity(), null));

        loadAsteroidList();

        return rootView;
    }

    private void loadAsteroidList() {
        showLoading (true);

        Locale locale = Locale.getDefault();
        String startDate = new SimpleDateFormat("yyyy-MM-dd", locale).format(new Date());
        Service.getNasaApi().getAsteroidList("2017-07-20")
                .enqueue(new Callback<AsteroidList>() {
                    @Override
                    public void onResponse(Call<AsteroidList> call, Response<AsteroidList> response) {
                        onSuccessResponse (response);
                    }

                    @Override
                    public void onFailure(Call<AsteroidList> call, Throwable t) {
                        onFailureResponse ();
                    }
                });
    }

    private void onSuccessResponse(Response<AsteroidList> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    asteroidList = response.body();
                    if (asteroidList != null) {
                ((AsteroidAdapter) asteroidRecyclerView.getAdapter())
                        .upload(asteroidList.getNearEarthObjects().getAsteroid());
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
            linearLayout.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

}
