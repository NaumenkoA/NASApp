package com.example.alex.nasapp.ui.rover;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

    public static final String ROVER_PHOTOS = "rover_photos";
    public static final String SELECTED_PHOTO_POSITION = "selected_photo_position";

    RelativeLayout relativeLayout;
    RoverPhotos roverPhotos;
    RecyclerView roverRecyclerView;
    ProgressBar progressBar;
    Button createPostcardButton;
    Photo selectedPhoto;
    Integer selectedPosition;
    OnCreatePostcardListener listener;

    public interface OnCreatePostcardListener {
        void createPostcard(Photo photo);
    }

    public RoverImageryFragment () {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ROVER_PHOTOS, roverPhotos);
        if (selectedPosition != null) {
            outState.putInt(SELECTED_PHOTO_POSITION, selectedPosition);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_rover_imagery, container, false);

        if (savedInstanceState != null) {
            roverPhotos = savedInstanceState.getParcelable(ROVER_PHOTOS);
            selectedPosition = savedInstanceState.getInt(SELECTED_PHOTO_POSITION, -1);
        }

        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relativeLayout);
        roverRecyclerView = (RecyclerView) rootView.findViewById(R.id.roverRecyclerView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        createPostcardButton = (Button) rootView.findViewById(R.id.createPostcardButton);

        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        int columns = (int)(dm.widthPixels/dm.density)/300;
        if (columns < 1) columns = 1;

        roverRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columns));
        roverRecyclerView.setAdapter(new MarsImageryAdapter(null, getActivity(), this));

        if (roverPhotos == null) {
            loadImagesFromMarsRover(1700, 0);
        } else {
            uploadPhotos();
        }

        if (selectedPosition != null && selectedPosition != -1) {
            selectedPhoto = roverPhotos.getPhotos().get(selectedPosition);
            createPostcardButton.setVisibility(View.VISIBLE);
            ((MarsImageryAdapter) roverRecyclerView.getAdapter()).setSelectedPosition(selectedPosition);
        }

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
            listener = (OnCreatePostcardListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnCreatePostcardListener");
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
                uploadPhotos();
                showLoading(false);
            }
        } else {
            onFailureResponse();
        }
    }

    private void uploadPhotos() {
        ((MarsImageryAdapter) roverRecyclerView.getAdapter()).upload(roverPhotos.getPhotos());
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
    public void onItemSelected(int position) {
        selectedPosition = position;
        selectedPhoto = roverPhotos.getPhotos().get(position);
        if (createPostcardButton.getVisibility() == View.INVISIBLE) {
            createPostcardButton.setVisibility(View.VISIBLE);
            Animator animator = AnimatorInflater.loadAnimator(getActivity(),
                    R.animator.button_animator);
            animator.setTarget(createPostcardButton);
            animator.start();
        }
    }
}
