package com.example.alex.nasapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alex.nasapp.R;
import com.example.alex.nasapp.model.asteroid.Asteroid;

import java.util.List;

public class AsteroidAdapter extends RecyclerView.Adapter <AsteroidAdapter.ViewHolder> {

    private List<Asteroid> asteroids;

    Context context;

    public AsteroidAdapter (Context context, List <Asteroid> asteroids) {
        this.asteroids = asteroids;
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        if (asteroids != null && asteroids.size() != 0) {
            return asteroids.size();
        } else {
            return 0;
        }
    }

    public void upload(List<Asteroid> asteroid) {
        asteroids = asteroid;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        TextView nameTextView;
        TextView diameterTextView;
        TextView velocityTextView;
        TextView approachDateTextView;
        TextView missDistanceTextView;
        TextView isHazardousTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            diameterTextView = (TextView) itemView.findViewById(R.id.diameterTextView);
            velocityTextView = (TextView) itemView.findViewById(R.id.velocityTextView);
            approachDateTextView = (TextView) itemView.findViewById(R.id.approachDateTextView);
            missDistanceTextView = (TextView) itemView.findViewById(R.id.missDistanceTextView);
            isHazardousTextView = (TextView) itemView.findViewById(R.id.isHazardousTextView);
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
                    changeNumberOfCharsAfterDot(asteroidMissDistance, 1)));
            diameterTextView.setText(context.getResources().getString(R.string.asteroid_diameter,
                    changeNumberOfCharsAfterDot(estimatedDiameterMin, 0),
                    changeNumberOfCharsAfterDot(estimatedDiameterMax, 0)));
            velocityTextView.setText(context.getResources().getString(R.string.asteroid_velocity,
                   changeNumberOfCharsAfterDot(asteroidVelocity, 1)));
            approachDateTextView.setText(asteroid.getCloseApproachData().get(0).getCloseApproachDate());
            if (asteroid.getPotentiallyHazardousAsteroid()) {
                linearLayout.setBackgroundColor(Color.parseColor("#f67d7d"));
                isHazardousTextView.setText(R.string.potentially_hazardous);
            } else {
                linearLayout.setBackgroundColor(Color.parseColor("#87f67d"));
                isHazardousTextView.setText(R.string.not_hazardous);
            }
        }

        private String changeNumberOfCharsAfterDot(String string, int charsAfterDot) {
            if (charsAfterDot == 0) {
                return string.substring(0, string.indexOf(".") - 1);
            } else {
                return string.substring(0, string.indexOf(".") + charsAfterDot + 1);
            }
        }
    }
}
