package com.example.alex.nasapp;

import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ImageView;

import com.example.alex.nasapp.ui.MainActivity;

import org.hamcrest.Description;
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
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class ApiConsumptionTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void marsRoverImageryAPIConsumptionTest() throws Exception {
        //Arrange

        //Act
        onView(allOf(withId(R.id.startButton), isDisplayed())).perform(click());
        SystemClock.sleep(5000);

        //Assert
        onView(withId(R.id.roverRecyclerView))
                .check(matches(hasDescendant(withText("Rover \"Curiosity\""))));
    }

    @Test
    public void asteroidNeoFeedAPIConsumptionTest() throws Exception {
        //Arrange
        onView(withId(R.id.viewPager)).perform(swipeLeft()).perform(swipeLeft());
        SystemClock.sleep(200);

        //Act
        onView(allOf(withId(R.id.startButton), isDisplayed())).perform(click());
        SystemClock.sleep(5000);
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Show hazardous"))).perform(click());

        //Assert
        onView(withId(R.id.asteroidRecyclerView))
                .check(matches(hasDescendant(withText("HAZARDOUS!"))));
    }

    @Test
    public void earthImageryApiConsumptionTest() throws Exception {
        //Arrange
        String longitude = "37.581280";
        String latitude = "55.891734";
        onView(withId(R.id.viewPager)).perform(swipeLeft());
        SystemClock.sleep(300);
        onView(allOf(withId(R.id.startButton), isDisplayed())).perform(click());

        //Act
        onView(withId(R.id.latEditText)).perform(clearText()).perform(typeText(latitude));
        onView(withId(R.id.latEditText)).perform(pressImeActionButton());
        onView(withId(R.id.longEditText)).perform(clearText()).perform(typeText(longitude));
        onView(withId(R.id.longEditText)).perform(pressImeActionButton());
        onView(withId(R.id.showImageButton)).perform(click());
        SystemClock.sleep(5000);

        //Assert
        BoundedMatcher hasDrawableMatcher = new BoundedMatcher <View, ImageView>(ImageView.class) {
            @Override
            protected boolean matchesSafely(ImageView imageView) {
                return imageView.getDrawable()!=null;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Image view doesn't have drawables");
            }
        };
        onView(withId(R.id.photoImageView))
                .check(matches(hasDrawableMatcher));
    }

}
