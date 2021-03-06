package com.example.alex.nasapp.ui.asteroid;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alex.nasapp.R;
import com.example.alex.nasapp.adapters.AsteroidAdapter;
import com.example.alex.nasapp.api.Service;
import com.example.alex.nasapp.model.asteroid.deserializer.AsteroidDeserializer;
import com.example.alex.nasapp.model.asteroid.Asteroid;
import com.example.alex.nasapp.model.asteroid.AsteroidList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsteroidListFragment extends Fragment implements AsteroidAdapter.AsteroidSelectedListener {


    public AsteroidListFragment() {

    }

    public static final String ASTEROID_LIST = "asteroid_list";
    public static final String SELECTED_ASTEROID = "selected_asteroid";

    RelativeLayout relativeLayout;
    ProgressBar progressBar;
    Spinner spinner;
    RecyclerView asteroidRecyclerView;
    AsteroidList asteroidList;
    Asteroid selectedAsteroid;
    Button sendAlertButton;
    OnSendSMSAlertListener listener;

    //listener for creating SMS alert
    public interface OnSendSMSAlertListener {
        void sendSMSAlert(Asteroid asteroid);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //rotation handling
        outState.putParcelable(ASTEROID_LIST, asteroidList);
        outState.putParcelable(SELECTED_ASTEROID, selectedAsteroid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_asteroid_list, container, false);

        //rotation handling
        if (savedInstanceState != null) {
            asteroidList = savedInstanceState.getParcelable(ASTEROID_LIST);
            selectedAsteroid = savedInstanceState.getParcelable(SELECTED_ASTEROID);
        }

        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relativeLayout);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        sendAlertButton = (Button) rootView.findViewById(R.id.sendAlertSMSButton);
        sendAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedAsteroid.getPotentiallyHazardousAsteroid()) {
                    Toast.makeText(getActivity(), "This asteroid is not hazardous. Let's pick something more dangerous!", Toast.LENGTH_SHORT).show();
                } else {
                    listener.sendSMSAlert(selectedAsteroid);
                }
            }
        });

        spinner = (Spinner) rootView.findViewById(R.id.spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.sort_asteroids_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (asteroidList == null) return;
                    switch (position) {
                        case 0:
                            refreshAdapterData(asteroidList.getAsteroidList());
                            notifyAdapterAboutSelectedItemPosition();
                            break;
                        case 1:
                            refreshAdapterData(asteroidList.getHazardousAsteroidList());
                            notifyAdapterAboutSelectedItemPosition();
                            break;
                    }
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

            asteroidRecyclerView = (RecyclerView) rootView.findViewById(R.id.asteroidRecyclerView);
            //adjust number of columns in RecyclerView to screen width
            DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
            int columns = (int)(dm.widthPixels/dm.density)/300;
            if (columns < 1) columns = 1;
            asteroidRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                   columns));
            asteroidRecyclerView.setAdapter(new AsteroidAdapter(getActivity(), null, this));


        if (asteroidList == null) {
            loadAsteroidList();
        }  else {
            refreshAdapterData(asteroidList.getAsteroidList());
        }

        if (selectedAsteroid != null) {
            if (sendAlertButton.getVisibility() == View.INVISIBLE) {
                sendAlertButton.setVisibility(View.VISIBLE);
            }
            notifyAdapterAboutSelectedItemPosition();
        }

        return rootView;
    }

    private void notifyAdapterAboutSelectedItemPosition() {
        int selectedPosition = -1;
        switch (spinner.getSelectedItemPosition()){
            case 0:
                selectedPosition = asteroidList.getAsteroidPosition(asteroidList.getAsteroidList(), selectedAsteroid);
                break;
            case 1:
                selectedPosition = asteroidList.getAsteroidPosition(asteroidList.getHazardousAsteroidList(), selectedAsteroid);
                break;
        }
            ((AsteroidAdapter) asteroidRecyclerView.getAdapter()).setSelectedAsteroidPosition(selectedPosition);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnSendSMSAlertListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSendSMSAlertListener");
        }
    }

    private void loadAsteroidList() {
        showLoading (true);

        Locale locale = Locale.getDefault();
        //get current date
        String startDate = new SimpleDateFormat("yyyy-MM-dd", locale).format(new Date());
        //make API call
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
                if (response.code() == HttpURLConnection.HTTP_OK && response.body() != null) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(AsteroidList.class, new AsteroidDeserializer());
                    Gson gson = gsonBuilder.create();
                    asteroidList = gson.fromJson(response.body().toString(), AsteroidList.class);

                    if (asteroidList != null) {
                        refreshAdapterData(asteroidList.getAsteroidList());
                        showLoading(false);
            }
        } else {
            onFailureResponse();
        }
    }

    private void refreshAdapterData(List<Asteroid> asteroids) {
        ((AsteroidAdapter)  asteroidRecyclerView.getAdapter()).upload(asteroids);
    }

    private void onFailureResponse() {
        showLoading(false);
        Snackbar.make(relativeLayout, getResources().getString(R.string.internet_error_message), Snackbar.LENGTH_LONG).show();
    }

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            relativeLayout.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            relativeLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onAsteroidSelected(int position) {
        switch (spinner.getSelectedItemPosition()) {
            case 0: selectedAsteroid = asteroidList.getAsteroidList().get(position);
                break;
            case 1: selectedAsteroid = asteroidList.getHazardousAsteroidList().get(position);
                break;
        }
        notifyAdapterAboutSelectedItemPosition();
        if (sendAlertButton.getVisibility() == View.INVISIBLE) {
            sendAlertButton.setVisibility(View.VISIBLE);
            Animator animator = AnimatorInflater.loadAnimator(getActivity(),
                    R.animator.button_animator);
            animator.setTarget(sendAlertButton);
            animator.start();
        }
    }


}
