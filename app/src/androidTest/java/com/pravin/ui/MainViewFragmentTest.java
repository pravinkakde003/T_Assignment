package com.pravin.ui;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;

import com.pravin.assignment.R;
import com.pravin.assignment.view.ui.MainActivity;
import com.pravin.assignment.view.ui.MainViewFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

import android.support.test.espresso.contrib.RecyclerViewActions;


@RunWith(AndroidJUnit4.class)
public class MainViewFragmentTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void fragmentInstantiated() throws Exception {
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    MainViewFragment mainViewFragment = startMainViewFragment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        onView(withId(R.id.data_recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void testClickOnItemInRecyclerView() throws Exception {
        if (getRowCount() > 0) {
            onView(withId(R.id.data_recycler_view)).perform(RecyclerViewActions.actionOnItem(
                    hasDescendant(withText("Housing")), click()));
            onView(withId(R.id.title)).check(matches(withText("Housing")));
        }
    }

    @Test
    public void testClickOnPosition() throws Exception {
        if (getRowCount() > 0) {
            onView(withId(R.id.data_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        }
    }


    @Test
    public void testEmptyRecyclerView() {
        RecyclerView recyclerView = activityRule.getActivity().findViewById(R.id.data_recycler_view);
        int itemCount = recyclerView.getAdapter().getItemCount();
        if (itemCount < 0) {
            Espresso.onView(withId(R.id.data_recycler_view)).check(ViewAssertions.matches(not(isDisplayed())));
            Espresso.onView(withId(R.id.noInternetLayout)).check(ViewAssertions.matches(isDisplayed()));
        }
    }

    private MainViewFragment startMainViewFragment() throws Exception {
        MainActivity activity = (MainActivity) activityRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        MainViewFragment mainViewFragment = new MainViewFragment();
        transaction.add(mainViewFragment, "MainViewFragment");
        transaction.commit();
        return mainViewFragment;
    }

    private int getRowCount() {
        RecyclerView recyclerView = activityRule.getActivity().findViewById(R.id.data_recycler_view);
        return recyclerView.getAdapter().getItemCount();
    }
}
