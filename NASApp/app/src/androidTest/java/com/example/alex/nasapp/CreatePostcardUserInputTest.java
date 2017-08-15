package com.example.alex.nasapp;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.alex.nasapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class CreatePostcardUserInputTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void noPostcardTextEnteredByUser() throws Exception {
        // Arrange
        onView(allOf(withId(R.id.startButton), isDisplayed())).perform(click());
        SystemClock.sleep(3000);
        onView(withId(R.id.roverRecyclerView)).perform(
        RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.createPostcardButton)).perform(click());
        onView(withId(R.id.postCardEditText)).perform(typeText("text"));
        onView(withId(R.id.postCardEditText)).perform(pressImeActionButton());

        //Act
        onView(withId(R.id.postCardEditText)).perform(clearText());
        onView(withId(R.id.postCardEditText)).perform(pressImeActionButton());

        // Assert
        onView(withId(R.id.submitButton)).check(matches(not(isEnabled())));
        }
    }
