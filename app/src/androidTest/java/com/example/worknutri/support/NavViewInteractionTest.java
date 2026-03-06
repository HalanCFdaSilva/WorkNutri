package com.example.worknutri.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.worknutri.ui.agendasFragment.FragmentSelectedActivity;
import com.example.worknutri.ui.agendasFragment.ScheduleActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavViewInteractionTest<T extends Activity> {

    private final int navViewId;
    private final ActivityScenario<T> scenario;

    public NavViewInteractionTest(int navViewId, ActivityScenario<T> scenario) {
        this.navViewId = navViewId;
        this.scenario = scenario;
    }


    public void clickInNavItemOpenScheduleFragment(int itemId, FragmentSelectedActivity fragmentExpected) {
        scenario.onActivity(activity -> {
            BottomNavigationView nav = activity.findViewById(navViewId);
            if (nav == null) {
                throw new IllegalArgumentException("No BottomNavigationView found with id: " + navViewId);
            }
            nav.getMenu().performIdentifierAction(itemId, 0);
        });

        assertEquals(fragmentExpected, ScheduleActivity.fragmentSelected);

    }

    public void clickInNavItemOpenActivity(int itemId, Class<? extends Activity> activityExpected) {
        Instrumentation.ActivityMonitor monitor = InstrumentationRegistry.getInstrumentation()
                .addMonitor(activityExpected.getName(), null, false);

        scenario.onActivity(activity -> {
            BottomNavigationView nav = activity.findViewById(navViewId);
            if (nav == null) {
                throw new IllegalArgumentException("No BottomNavigationView found with id: " + navViewId);
            }
            nav.getMenu().performIdentifierAction(itemId, 0);
        });

        Activity started = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(monitor, 2000);
        assertNotNull( started);
        started.finish();
        InstrumentationRegistry.getInstrumentation().removeMonitor(monitor);


    }

}

