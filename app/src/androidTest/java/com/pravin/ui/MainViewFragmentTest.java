package com.pravin.ui;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
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

import android.support.test.espresso.contrib.RecyclerViewActions;


@RunWith(AndroidJUnit4.class)
public class MainViewFragmentTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void fragmentInstantiated() {
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainViewFragment mainViewFragment = startMainViewFragment();
            }
        });
        onView(withId(R.id.data_recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void testSample(){
        if (getRowCount() > 0){
            onView(withId(R.id.data_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));

            onView(withId(R.id.data_recycler_view)).perform(RecyclerViewActions.actionOnItem(
                    hasDescendant(withText("Housing")), click()));

            onView(withId(R.id.title)).check(matches(withText("Housing")));
        }
    }

    private MainViewFragment startMainViewFragment() {
        MainActivity activity = (MainActivity) activityRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        MainViewFragment mainViewFragment = new MainViewFragment();
        transaction.add(mainViewFragment, "MainViewFragment");
        transaction.commit();
        return mainViewFragment;
    }

    private int getRowCount(){
        RecyclerView recyclerView = activityRule.getActivity().findViewById(R.id.data_recycler_view);
        return recyclerView.getAdapter().getItemCount();
    }
}
