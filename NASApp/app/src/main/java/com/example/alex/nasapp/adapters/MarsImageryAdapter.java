package com.example.alex.nasapp.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.alex.nasapp.R;
import com.example.alex.nasapp.model.rover.Photo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MarsImageryAdapter extends RecyclerView.Adapter <MarsImageryAdapter.ViewHolder> {

    private List <Photo> photos;
    private Activity activity;
    private int selectedItemPosition;
    private boolean photoWasSelected = false;
    private ItemSelectedListener listener;

    //set position of item selected by user
    public void setSelectedPosition(Integer selectedPosition) {
        this.selectedItemPosition = selectedPosition;
        photoWasSelected = true;
        notifyDataSetChanged();
    }

    public interface ItemSelectedListener {
        void onItemSelected (int selectedPosition);
    }

    public MarsImageryAdapter (List <Photo> photos, Activity activity, ItemSelectedListener listener) {
        this.photos = photos;
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    public MarsImageryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.mars_rover_image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(photos.get(position));

        //check if binded item is selected
        if (photoWasSelected && selectedItemPosition == position) {
            holder.selectedImageView.setVisibility(View.VISIBLE);
        } else
        {
            holder.selectedImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (photos != null && photos.size() != 0) {
            return photos.size();
        } else {
            return 0;
        }
    }

    public void upload(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView roverImageView;
        ImageView selectedImageView;
        TextView dateTextView;
        TextView roverNameTextView;
        RelativeLayout relativeLayout;
        ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            roverImageView = (ImageView) itemView.findViewById(R.id.marsRoverImageView);
            selectedImageView = (ImageView) itemView.findViewById(R.id.selectedPhotoImageView);
            dateTextView = (TextView) itemView.findViewById(R.id.roverNameTextView);
            roverNameTextView = (TextView) itemView.findViewById(R.id.earthDateTextView);
            itemView.setOnClickListener(this);
        }

        void bind (Photo photo) {
            Picasso.with(activity).load(photo.getImgSrc()).into(roverImageView, new Callback() {
                @Override
                public void onSuccess() {
                    stopLoading();
                }

                @Override
                public void onError() {
                    stopLoading();
                }
            });
            dateTextView.setText(photo.getEarthDate());
            roverNameTextView.setText(activity.getResources().getString(R.string.rover_name, photo.getRover().getName()));
        }

        //hide progress bar and show Mars Rover image to user
        private void stopLoading() {
            progressBar.setVisibility(View.INVISIBLE);
            relativeLayout.setVisibility(View.VISIBLE);
        }

        @Override
        public void onClick(View v) {
            selectedItemPosition = this.getAdapterPosition();
            if (!photoWasSelected) {
                photoWasSelected = true;
            }
            notifyDataSetChanged();
            listener.onItemSelected(selectedItemPosition);
        }
    }
}
