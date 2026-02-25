package com.example.worknutri.support;

import static org.junit.Assert.assertEquals;

import android.app.Activity;

import androidx.test.core.app.ActivityScenario;

import com.example.worknutri.ui.agendasFragment.FragmentSelectedActivity;
import com.example.worknutri.ui.agendasFragment.ScheduleActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavViewInteractionTest<T extends Activity> {

    private final int navViewId;
    private final Class<T> activityClass;

    public NavViewInteractionTest(int navViewId, Class<T> activityClass) {
        this.navViewId = navViewId;
        this.activityClass = activityClass;
    }


    public void clickSingleNavItemOpenScheduleFragment(int itemId, FragmentSelectedActivity fragmentExpected) {


        try (ActivityScenario<T> localScenario = ActivityScenario.launch(activityClass)) {
            localScenario.onActivity(activity -> {
                BottomNavigationView nav = activity.findViewById(navViewId);
                if (nav == null) {
                    throw new IllegalArgumentException("No BottomNavigationView found with id: " + navViewId);
                }
                nav.getMenu().performIdentifierAction(itemId, 0);
            });

            assertEquals(fragmentExpected, ScheduleActivity.fragmentSelected);
        }
    }

}

