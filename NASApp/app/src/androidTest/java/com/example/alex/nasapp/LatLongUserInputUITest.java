package com.example.alex.nasapp;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.alex.nasapp.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class LatLongUserInputUITest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private String wrongValue;

    @Before
    public void setUpTest(){
        String longitude = "37.581280";
        String latitude = "55.891734";
        wrongValue = "-";
        onView(withId(R.id.viewPager)).perform(swipeLeft());
        SystemClock.sleep(200);
        onView(allOf(withId(R.id.startButton), isDisplayed())).perform(click());

        onView(withId(R.id.latEditText)).perform(typeText(latitude));
        onView(withId(R.id.latEditText)).perform(pressImeActionButton());
        onView(withId(R.id.longEditText)).perform(typeText(longitude));
        onView(withId(R.id.longEditText)).perform(pressImeActionButton());
    }

    @Test
    public void  noLatitudeEnteredByUser() throws Exception {
        // Arrange

        // Act
        onView(withId(R.id.latEditText)).perform(clearText());
        onView(withId(R.id.latEditText)).perform(pressImeActionButton());

        // Assert
        onView(withId(R.id.showImageButton)).check(matches(not(isEnabled())));
    }

    @Test
    public void noLongitudeEnteredByUser () throws Exception {
        // Arrange

        // Act
        onView(withId(R.id.longEditText)).perform(clearText());
        onView(withId(R.id.longEditText)).perform(pressImeActionButton());

        // Assert
        onView(withId(R.id.showImageButton)).check(matches(not(isEnabled())));
    }

    @Test
    public void wrongLatitudeEnteredByUser() throws Exception {
        // Arrange

        // Act
        onView(withId(R.id.latEditText)).perform(clearText()).perform(typeText(wrongValue));
        onView(withId(R.id.latEditText)).perform(pressImeActionButton());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.showImageButton)).perform(click());
         // Assert
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.lat_long_input_error_message)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void wrongLongitudeEnteredByUser() throws Exception {
        // Arrange

        // Act
        onView(withId(R.id.longEditText)).perform(clearText()).perform(typeText(wrongValue));
        onView(withId(R.id.longEditText)).perform(pressImeActionButton());
        Espresso.closeSoftKeyboard();
        SystemClock.sleep(100);
        onView(withId(R.id.showImageButton)).perform(click());
        // Assert
        onView(allOf(withId(android.support.design.R.id.snackbar_text),
                withText(R.string.lat_long_input_error_message)))
                .check(matches(isDisplayed()));
    }

}
