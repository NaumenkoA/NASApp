package com.example.alex.nasapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.nasapp.R;
import com.example.alex.nasapp.model.menuFeatures.MenuFeature;
import com.squareup.picasso.Picasso;

public class FeatureFragment extends Fragment {
        public static final String MENU_FEATURE = "menu_feature";

        MenuFeature menuFeature;

        public static FeatureFragment newInstance (MenuFeature menuFeature) {
            FeatureFragment featureFragment = new FeatureFragment();
            Bundle args = new Bundle();
            args.putParcelable(MENU_FEATURE, menuFeature);
            featureFragment.setArguments(args);
            return featureFragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_screen_slide_page, container, false);

            this.menuFeature = getArguments().getParcelable(MENU_FEATURE);

            ImageView featureImageView = (ImageView) rootView.findViewById(R.id.featureImageView);
            TextView featureNameTextView = (TextView) rootView.findViewById(R.id.featureNameTextView);
            Picasso.with(getActivity()).load(menuFeature.getImageResourceId()).into(featureImageView);

            featureNameTextView.setText(getActivity().getResources().getString(menuFeature.getFeatureNameId()));

            Button infoButton = (Button) rootView.findViewById(R.id.infoButton);
            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(getResources().getString(menuFeature.getFeatureDescriptionId()));
                    builder.setPositiveButton(R.string.ok, null);
                    builder.create().show();
                }
            });

            Button startButton = (Button) rootView.findViewById(R.id.startButton);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), NasaActivity.class);
                    intent.putExtra (NasaActivity.SELECTED_FEATURE_ID, menuFeature.getFeatureId());
                    startActivity(intent);
                }
            });

            return rootView;
        }
    }
