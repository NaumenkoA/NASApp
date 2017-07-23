package com.example.alex.nasapp.ui.asteroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.alex.nasapp.R;
import com.example.alex.nasapp.adapters.AsteroidAdapter;
import com.example.alex.nasapp.api.Service;
import com.example.alex.nasapp.helpers.AsteroidDeserializer;
import com.example.alex.nasapp.model.asteroid.AsteroidList;
import com.google.android.gms.common.api.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
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
        Service.getNasaApi().getAsteroidList(startDate)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        onSuccessResponse (response);
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        onFailureResponse ();
                    }
                });
    }

    private void onSuccessResponse(Response<JsonElement> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(AsteroidList.class, new AsteroidDeserializer());
                    Gson gson = gsonBuilder.create();
                    asteroidList = gson.fromJson(response.body().toString(), AsteroidList.class);

                    if (asteroidList != null) {
                        ((AsteroidAdapter)  asteroidRecyclerView.getAdapter()).upload(asteroidList.getAsteroidList());
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
