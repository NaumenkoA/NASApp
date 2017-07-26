package com.example.alex.nasapp.ui.asteroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alex.nasapp.R;
import com.example.alex.nasapp.model.asteroid.Asteroid;
import com.example.alex.nasapp.ui.eye_in_the_sky.SatellitePhotoFragment;
import com.google.android.gms.maps.model.LatLng;


public class SMSAlertFragment extends Fragment {


    private static final String SELECTED_ASTEROID = "selected_asteroid";

    public SMSAlertFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sms_alert, container, false);
    }

    public static SMSAlertFragment newInstance (Asteroid asteroid) {
        SMSAlertFragment smsAlertFragment = new SMSAlertFragment();
        Bundle args = new Bundle();
        args.putParcelable(SELECTED_ASTEROID, asteroid);
        smsAlertFragment.setArguments(args);
        return smsAlertFragment;
    }
}
