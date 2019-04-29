package com.example.udacitybakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecipeListTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickOnRecyclerViewItem(){

    }

    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResource(){
        idlingResource = activityTestRule.getActivity().setIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void idlingResourceTest(){
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.ingredient_label)).check(matches(isDisplayed()));

        onView(withId(R.id.step_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.view_pager))
                .check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource(){
        if(idlingResource !=null){
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }
}
