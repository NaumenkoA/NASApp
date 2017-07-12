package com.example.alex.nasapp.ui.rover;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.alex.nasapp.R;
import com.example.alex.nasapp.adapters.MarsImageryAdapter;
import com.example.alex.nasapp.api.Service;
import com.example.alex.nasapp.model.rover.Photo;
import com.example.alex.nasapp.model.rover.RoverPhotos;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoverImageryFragment extends Fragment implements MarsImageryAdapter.ItemSelectedListener{

    RelativeLayout relativeLayout;
    RoverPhotos roverPhotos;
    RecyclerView roverRecyclerView;
    ProgressBar progressBar;
    Button createPostcardButton;
    Photo selectedPhoto;
    CreatePostcardListener listener;

    public interface CreatePostcardListener  {
        void createPostcard(Photo photo);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_rover_imagery, container, false);

        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relativeLayout);
        roverRecyclerView = (RecyclerView) rootView.findViewById(R.id.roverRecyclerView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        createPostcardButton = (Button) rootView.findViewById(R.id.createPostcardButton);

        roverRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        roverRecyclerView.setAdapter(new MarsImageryAdapter(null, getActivity(), this));

        loadImagesFromMarsRover(1700, 0);

        createPostcardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.createPostcard(selectedPhoto);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (CreatePostcardListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement CreatePostcardListener");
        }
    }

    private void loadImagesFromMarsRover(int sol, int page) {
        showLoading (true);
        Service.getNasaApi().getMarsRoverImages(sol, page)
                .enqueue(new Callback<RoverPhotos>() {
                    @Override
                    public void onResponse(Call<RoverPhotos> call, Response<RoverPhotos> response) {
                        onSuccessResponse (response);
                    }

                    @Override
                    public void onFailure(Call<RoverPhotos> call, Throwable t) {
                        onFailureResponse ();
                    }
                });
    }

    private void onSuccessResponse(Response<RoverPhotos> response) {
        if (response.code() == HttpURLConnection.HTTP_OK) {
            roverPhotos = response.body();
            if (roverPhotos != null) {
                ((MarsImageryAdapter) roverRecyclerView.getAdapter()).upload(roverPhotos.getPhotos());;
            }
            showLoading(false);
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
            relativeLayout.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            relativeLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onItemSelected(Photo photo) {
        selectedPhoto = photo;
        if (createPostcardButton.getVisibility() == View.INVISIBLE) {
            createPostcardButton.setVisibility(View.VISIBLE);
        }
    }
}
