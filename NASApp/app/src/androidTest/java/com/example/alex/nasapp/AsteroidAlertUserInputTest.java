package com.example.alex.nasapp;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.alex.nasapp.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
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
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class AsteroidAlertUserInputTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setUpTest(){
        String phone = "+79299940031";
        onView(withId(R.id.viewPager)).perform(swipeLeft()).perform(swipeLeft());
        SystemClock.sleep(200);
        onView(allOf(withId(R.id.startButton), isDisplayed())).perform(click());
        SystemClock.sleep(3000);
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Show hazardous"))).perform(click());
        onView(withId(R.id.asteroidRecyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.sendAlertSMSButton)).perform(click());
        onView(withId(R.id.phoneEditText)).perform(typeText(phone));
        onView(withId(R.id.phoneEditText)).perform(pressImeActionButton());
    }

    @Test
    public void  noPhoneEnteredByUser() throws Exception {
        // Arrange

        // Act
        onView(withId(R.id.phoneEditText)).perform(clearText());
        onView(withId(R.id.phoneEditText)).perform(pressImeActionButton());

        // Assert
        onView(withId(R.id.sendSMSButton)).check(matches(not(isEnabled())));
    }

    @Test
    public void  wrongPhoneEnteredByUser() throws Exception {
        // Arrange
        String wrongPhone = "+700000000000000000NNN";
        // Act
        onView(withId(R.id.phoneEditText)).perform(typeText(wrongPhone));
        onView(withId(R.id.phoneEditText)).perform(pressImeActionButton());
        onView(withId(R.id.sendSMSButton)).perform(click());

        // Assert
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("Some error occurred. Please check the recipient number and try again")))
                .check(matches(isDisplayed()));

    }

    @Test
    public void  noSMSTextEnteredByUser() throws Exception {
        // Arrange

        // Act
        onView(withId(R.id.smsEditText)).perform(clearText());
        onView(withId(R.id.smsEditText)).perform(pressImeActionButton());

        // Assert
        onView(withId(R.id.sendSMSButton)).check(matches(not(isEnabled())));
    }
}
