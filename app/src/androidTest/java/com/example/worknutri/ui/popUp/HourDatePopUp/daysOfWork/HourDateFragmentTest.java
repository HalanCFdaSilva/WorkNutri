package com.example.worknutri.ui.popUp.HourDatePopUp.daysOfWork;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.ui.ActivityToTest;
import com.example.worknutri.ui.popUp.hourDatePopUp.daysOfWork.HourDateFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class HourDateFragmentTest {

    private HourDateFragment fragment;
    private LayoutInflater inflater;
    private ActivityToTest activity;

    @Before
    public void setUp() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(act -> {
            activity = act;
            inflater = activity.getLayoutInflater();
            fragment = new HourDateFragment();
            fragment.generateNewLayout(inflater);
        });
    }

    @Test
    public void testSetDayOfweek() {
        activity.runOnUiThread(() -> fragment.setDayOfweek("Segunda"));
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        TextView tv = fragment.getLayout().findViewById(R.id.time_descrition_fragment_textView_day_of_week);
        assertEquals("Segunda", tv.getText().toString());
    }

    @Test
    public void testSetHourBegin() {
        fragment.setHourBegin("08:00");
        TextView tv = fragment.getLayout().findViewById(R.id.time_descrition_fragment_textView_hour_begin);
        assertEquals("08:00", tv.getText().toString());
    }

    @Test
    public void testSetHourEnd() {
        fragment.setHourEnd("18:00");
        TextView tv = fragment.getLayout().findViewById(R.id.time_descrition_fragment_textView_hour_end);
        assertEquals("18:00", tv.getText().toString());
    }

    @Test
    public void testRemoveButtonDelete() {
        fragment.removeButtonDelete();
        ImageButton btn = fragment.getTrashButton();
        assertEquals(View.GONE, btn.getVisibility());
    }

    @Test
    public void testSetFromDayOfWork() {
        DayOfWork day = new DayOfWork();
        day.setDayOfWeek("Terça");
        day.setHoraInicio("09:00");
        day.setHoraFim("17:00");
        fragment.setFromDayOfWork(day);
        TextView tvDay = fragment.getLayout().findViewById(R.id.time_descrition_fragment_textView_day_of_week);
        TextView tvBegin = fragment.getLayout().findViewById(R.id.time_descrition_fragment_textView_hour_begin);
        TextView tvEnd = fragment.getLayout().findViewById(R.id.time_descrition_fragment_textView_hour_end);
        assertEquals("Terça", tvDay.getText().toString());
        assertEquals("09:00", tvBegin.getText().toString());
        assertEquals("17:00", tvEnd.getText().toString());
    }

    @Test
    public void testLayoutStructure() {
        activity.runOnUiThread(() -> {
            View layout = fragment.getLayout();
            assertNotNull(layout.findViewById(R.id.time_descrition_fragment_cardView));
            ViewGroup linearLayout = layout.findViewById(R.id.time_descrition_fragment_linearLayout);
            assertNotNull(linearLayout);
            assertTrue(linearLayout instanceof LinearLayout);
            assertEquals(5, linearLayout.getChildCount());

            verifyChild(linearLayout.getChildAt(0), R.id.time_descrition_fragment_textView_day_of_week, TextView.class);
            verifyChild(linearLayout.getChildAt(1), R.id.time_descrition_fragment_textView_hour_begin, TextView.class);
            verifyChild(linearLayout.getChildAt(2), R.id.time_descrition_fragment_textView_hour_half, TextView.class);
            verifyChild(linearLayout.getChildAt(3), R.id.time_descrition_fragment_textView_hour_end, TextView.class);
            verifyChild(linearLayout.getChildAt(4), R.id.time_descrition_fragment_button_delete, ImageButton.class);

            assertEquals(View.VISIBLE, layout.findViewById(R.id.time_descrition_fragment_cardView).getVisibility());
            assertEquals(View.VISIBLE, linearLayout.getVisibility());
        });
    }

    private static void verifyChild(View viewChild, int expectedId,Class<?> expectedClass) {
        assertNotNull(viewChild);
        assertEquals(expectedId,viewChild.getId());
        assertTrue(expectedClass.isInstance(viewChild));
    }

}
