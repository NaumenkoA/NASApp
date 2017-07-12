package com.example.alex.nasapp.model.menuFeatures;

import com.example.alex.nasapp.R;

import java.util.Arrays;
import java.util.List;

public class MenuFeatures {

    public static final int POSTCARD_FROM_MARS_ID = 10;
    public static final int EYE_IN_THE_SKY_ID = 11;
    public static final int ASTEROID_ALERT = 12;


    public static MenuFeature getMenuFeatures(int index) {
        return menuFeatures.get(index);
    }

    private static List<MenuFeature> menuFeatures = Arrays.asList(
            new MenuFeature(R.string.postcard_from_mars, R.drawable.rover_menu_image, R.string.rover_postcard_maker_feature, POSTCARD_FROM_MARS_ID),
            new MenuFeature(R.string.eye_in_the_sky, R.drawable.eye_in_sky_menu_image, R.string.eye_in_the_sky_feature, EYE_IN_THE_SKY_ID),
            new MenuFeature(R.string.asteroid_alert, R.drawable.asteroid_alert_menu_image, R.string.asteroid_alert_feature, ASTEROID_ALERT));
}
