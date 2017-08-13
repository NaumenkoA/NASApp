package com.example.alex.nasapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.nasapp.R;
import com.example.alex.nasapp.helpers.StringHelper;
import com.example.alex.nasapp.model.asteroid.Asteroid;

import java.util.List;

public class AsteroidAdapter extends RecyclerView.Adapter <AsteroidAdapter.ViewHolder> {

    private List<Asteroid> asteroids;
    private Context context;
    private int selectedItemPosition;
    private boolean itemWasSelected = false;
    private AsteroidSelectedListener listener;

    public AsteroidAdapter (Context context, List <Asteroid> asteroids, AsteroidSelectedListener listener) {
        this.asteroids = asteroids;
        this.context = context;
        this.listener = listener;
    }

    public void setSelectedAsteroidPosition(int selectedAsteroidPosition) {
        if (selectedAsteroidPosition != -1) {
            this.selectedItemPosition = selectedAsteroidPosition;
            itemWasSelected = true;
            this.notifyDataSetChanged();
        } else {
            //nothing is selected
            itemWasSelected = false;
        }
        notifyDataSetChanged();
    }

    public interface AsteroidSelectedListener {
        void onAsteroidSelected (int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.asteroid_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(asteroids.get(position));

        //check if binded item is selected by user
        if (itemWasSelected && selectedItemPosition == position) {
            holder.selectedImageView.setVisibility(View.VISIBLE);
        } else
        {
            holder.selectedImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (asteroids != null && asteroids.size() != 0) {
            return asteroids.size();
        } else {
            return 0;
        }
    }

    //upload new asteroid list to adapter
    public void upload(List<Asteroid> asteroid) {
        asteroids = asteroid;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView cardView;
        TextView nameTextView;
        TextView diameterTextView;
        TextView velocityTextView;
        TextView approachDateTextView;
        TextView missDistanceTextView;
        TextView isHazardousTextView;
        ImageView selectedImageView;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            selectedImageView = (ImageView) itemView.findViewById(R.id.selecteAsteroidImageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            diameterTextView = (TextView) itemView.findViewById(R.id.diameterTextView);
            velocityTextView = (TextView) itemView.findViewById(R.id.velocityTextView);
            approachDateTextView = (TextView) itemView.findViewById(R.id.approachDateTextView);
            missDistanceTextView = (TextView) itemView.findViewById(R.id.missDistanceTextView);
            isHazardousTextView = (TextView) itemView.findViewById(R.id.isHazardousTextView);
            itemView.setOnClickListener(this);
        }

        void bind (Asteroid asteroid) {
            nameTextView.setText(asteroid.getName());
            String estimatedDiameterMin = asteroid.getEstimatedDiameter().getMeters()
                    .getEstimatedDiameterMin().toString();
            String estimatedDiameterMax = asteroid.getEstimatedDiameter().getMeters()
                    .getEstimatedDiameterMax().toString();
            String asteroidVelocity =  asteroid.getCloseApproachData().get(0).
                    getRelativeVelocity().getKilometersPerSecond();
            String asteroidMissDistance = asteroid.getCloseApproachData().get(0)
                    .getMissDistance().getLunar();

            missDistanceTextView.setText(context.getResources().getString(R.string.asteroid_miss_distance,
                    StringHelper.changeNumberOfCharsAfterDot(asteroidMissDistance, 1)));
            diameterTextView.setText(context.getResources().getString(R.string.asteroid_diameter,
                    StringHelper.changeNumberOfCharsAfterDot(estimatedDiameterMin, 0),
                    StringHelper.changeNumberOfCharsAfterDot(estimatedDiameterMax, 0)));
            velocityTextView.setText(context.getResources().getString(R.string.asteroid_velocity,
                   StringHelper.changeNumberOfCharsAfterDot(asteroidVelocity, 1)));
            approachDateTextView.setText(asteroid.getCloseApproachData().get(0).getCloseApproachDate());
            if (asteroid.getPotentiallyHazardousAsteroid()) {
                cardView.setCardBackgroundColor(Color.parseColor("#f4c4c4"));
                isHazardousTextView.setText(R.string.potentially_hazardous);
            } else {
                cardView.setCardBackgroundColor(Color.parseColor("#c4f4c7"));
                isHazardousTextView.setText(R.string.not_hazardous);
            }
        }

        @Override
        public void onClick(View v) {
            selectedItemPosition = this.getAdapterPosition();
            if (!itemWasSelected) {
                itemWasSelected = true;
            }
            listener.onAsteroidSelected(selectedItemPosition);
        }
    }
}
