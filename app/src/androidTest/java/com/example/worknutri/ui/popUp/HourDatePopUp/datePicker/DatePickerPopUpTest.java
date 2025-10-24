package com.example.worknutri.ui.popUp.HourDatePopUp.datePicker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.ActivityToTest;
import com.example.worknutri.ui.popUp.hourDatePopUp.datePicker.DatePickerPopUp;
import com.example.worknutri.util.StringsUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

@RunWith(AndroidJUnit4.class)
public class DatePickerPopUpTest {

    private ActivityScenario<ActivityToTest> scenario;

    @Before
    public void setUp() {
        scenario = ActivityScenario.launch(ActivityToTest.class);
    }



    @Test
    public void testGenerateNewLayoutSetsViewGroupInCorrectLayout() {
        scenario.onActivity(activity -> {
            LayoutInflater inflater = activity.getLayoutInflater();

            DatePickerPopUp popUp = new DatePickerPopUp(inflater);

            popUp.generateNewLayout();
            activity.showPopUp(popUp.getPopUpWindow());
        });

        Espresso.onView(ViewMatchers.withId(R.id.date_picker_pop_up_number_picker_hour_start))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSetLayoutErrorToDatePickerShowsError() {
        scenario.onActivity(activity -> {
            LayoutInflater inflater = activity.getLayoutInflater();
            DatePickerPopUp popUp = new DatePickerPopUp(inflater);
            activity.showPopUp(popUp.getPopUpWindow());
            popUp.setLayoutErrorToDatePicker(R.string.error_date_picker_hour_period_not_exist);

        });
        Espresso.onView(ViewMatchers.withId(R.id.date_picker_pop_up_textview_error))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testDatePickerIsCorrectReturnsFalseForInvalidPeriod() {

        DatePickerPopUp popUp = new DatePickerPopUp(LayoutInflater.from(TestUtil.getThemedContext()));
        popUp.generateNewLayout();
        popUp.configuraBotoes();
        popUp.getViewGroup().<android.widget.NumberPicker>findViewById(R.id.date_picker_pop_up_number_picker_hour_start).setValue(10);
        popUp.getViewGroup().<android.widget.NumberPicker>findViewById(R.id.date_picker_pop_up_number_picker_hour_end).setValue(5);


        boolean result = popUp.DatePickerIsCorrect(Collections.emptyList());
        assertFalse(result);
    }

    @Test
    public void testConvertToDayOfWorkBindsValues() {

        DatePickerPopUp popUp = new DatePickerPopUp(LayoutInflater.from(TestUtil.getThemedContext()));

        popUp.modifyDay(new DayOfWork());
        popUp.convertToDayOfWork();

        // Verifica se o objeto DayOfWork foi atualizado (exemplo: hora início não nula)
        assert popUp.getDayOfWork().getHoraInicio() != null;
    }




    @Test
    public void testConfiguraBotoesSetsPickersCorrectly() {
        Context context = TestUtil.getThemedContext();
        DatePickerPopUp popUp = new DatePickerPopUp(LayoutInflater.from(context));

        DayOfWork dayOfWork = new DayOfWork();
        dayOfWork.setHoraInicio("08");
        dayOfWork.setHoraFim("18");
        dayOfWork.setDayOfWeek("TERÇA");
        popUp.generateNewLayout();
        popUp.setDayOfWork(dayOfWork);
        popUp.configuraBotoes();

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        NumberPicker hourStart = popUp.getViewGroup().findViewById(R.id.date_picker_pop_up_number_picker_hour_start);
        NumberPicker hourEnd = popUp.getViewGroup().findViewById(R.id.date_picker_pop_up_number_picker_hour_end);
        NumberPicker weekDay = popUp.getViewGroup().findViewById(R.id.date_picker_pop_up_number_picker_week_day);

        int expectedStart = StringsUtil.convertHourStringInInt("08");
        int expectedEnd = StringsUtil.convertHourStringInInt("18");
        String displayedWeekDay = weekDay.getDisplayedValues()[weekDay.getValue()];

        assertEquals(expectedStart, hourStart.getValue());
        assertEquals(expectedEnd, hourEnd.getValue());
        assertEquals("TERÇA", displayedWeekDay);
        assertEquals(dayOfWork,popUp.getDayOfWork());

    }



    @Test
    public void testGetDayOfWorkReturnsInstance() {
        scenario.onActivity(activity -> {
            LayoutInflater inflater = activity.getLayoutInflater();
            DatePickerPopUp popUp = new DatePickerPopUp(inflater);

            assert popUp.getDayOfWork() != null;
        });
    }

    @Test
    public void testGetButtonSaveReturnsButton() {
        scenario.onActivity(activity -> {
            LayoutInflater inflater = activity.getLayoutInflater();
            DatePickerPopUp popUp = new DatePickerPopUp(inflater);

            popUp.generateNewLayout();
            Button button = popUp.getButtonSave();
            assert button != null;
            assert button.getId() == R.id.date_picker_pop_up_button_confirm;
        });
    }

}

