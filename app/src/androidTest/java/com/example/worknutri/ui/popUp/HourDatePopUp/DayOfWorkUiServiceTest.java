package com.example.worknutri.ui.popUp.HourDatePopUp;

import static org.junit.Assert.*;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.support.FakeDayOfWorkDao;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.ActivityToTest;
import com.example.worknutri.ui.popUp.hourDatePopUp.DayOfWorkUiService;
import com.example.worknutri.ui.popUp.hourDatePopUp.datePicker.DatePickerPopUp;
import com.example.worknutri.ui.popUp.hourDatePopUp.daysOfWork.HourDateFragmentInserter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.lang.reflect.Field;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DayOfWorkUiServiceTest {

    private LinearLayout rootOfActivity;
    private DayOfWorkUiService service;

    @Before
    public void setUp() {
        Context context = TestUtil.getThemedContext();
        rootOfActivity = new LinearLayout(context);
        // use constructor that creates a real DatePickerPopUp via PopUpFactoryImpl
        service = new DayOfWorkUiService(new LinearLayout(context), rootOfActivity);
    }

    // helper to access private field datePickerPopUp
    private DatePickerPopUp getDatePickerPopUp() throws Exception {
        Field f = service.getClass().getDeclaredField("datePickerPopUp");
        f.setAccessible(true);
        return (DatePickerPopUp) f.get(service);
    }

    @Test
    public void onSaveButton_shouldAddNewDay_whenDateIsValid() throws Exception {
        // prepare a valid DayOfWork and inject into the DatePicker fields (via modifyDay)
        DayOfWork valid = new DayOfWork();
        valid.setDayOfWeek("Segunda");
        valid.setHoraInicio("08:00");
        valid.setHoraFim("17:00");
        valid.setId(0);

        DatePickerPopUp datePicker = getDatePickerPopUp();
        // fill popup with the valid values
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> datePicker.modifyDay(valid));

        // register save listener and perform click on the button
        service.onPickerDayOfWorkClickInSaveButton();

        Button btnSave = datePicker.getButtonSave();
        assertNotNull("Save button not found", btnSave);

        InstrumentationRegistry.getInstrumentation().runOnMainSync(btnSave::performClick);

        // verify that an inserter was added and that getAllDayOfWork returns the day
        List<DayOfWork> all = service.getAllDayOfWork();
        assertEquals(1, all.size());
        DayOfWork added = all.get(0);
        assertEquals("08:00", added.getHoraInicio());
        assertEquals("17:00", added.getHoraFim());
        assertEquals("SEGUNDA", added.getDayOfWeek());
    }

    @Test
    public void onSaveButton_shouldNotAddNewDay_whenDateIsInvalid() throws Exception {
        // Invalid DayOfWork (no day of week) should be rejected
        DayOfWork invalid = new DayOfWork();
        invalid.setDayOfWeek("");
        invalid.setHoraInicio("08:00");
        invalid.setHoraFim("17:00");
        invalid.setId(1);

        DatePickerPopUp datePicker = getDatePickerPopUp();
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> datePicker.modifyDay(invalid));

        service.onPickerDayOfWorkClickInSaveButton();

        Button btnSave = datePicker.getButtonSave();
        assertNotNull(btnSave);

        InstrumentationRegistry.getInstrumentation().runOnMainSync(btnSave::performClick);

        // should not have added any day
        List<DayOfWork> all = service.getAllDayOfWork();
        assertEquals(0, all.size());
    }

    @Test
    public void configureButtonDelete_shouldRemoveViewAndCallDaoDelete_whenDayHasId(){
        // create day with id > 0 to enable delete in DAO
        DayOfWork day = new DayOfWork();
        day.setId(42);
        day.setDayOfWeek("Terça");
        day.setHoraInicio("09:00");
        day.setHoraFim("18:00");

        FakeDayOfWorkDao fakeDao = new FakeDayOfWorkDao();


        // generate inserter (creates layout inside viewGroupToInsert)
        HourDateFragmentInserter inserter = service.generateHourDateFragmentInserter(day, fakeDao);

        // before: a view should have been added
        assertTrue(rootOfActivity.getChildCount() > 0);

        // get trash button and click
        View trash = inserter.getHourDateFragment().getTrashButton();
        assertNotNull(trash);

        InstrumentationRegistry.getInstrumentation().runOnMainSync(trash::performClick);

        // view should have been removed and DAO should have received delete
        assertEquals(0, rootOfActivity.getChildCount());
        assertTrue(fakeDao.deletedCalled);
        assertEquals(42, fakeDao.deletedDay.getId());
    }
    @Test
    public void configureButtonDelete_shouldNotCallDaoDelete_whenDayHasIdZero() {
        DayOfWork day = new DayOfWork();
        day.setId(0); // id zero should not call delete on DAO
        day.setDayOfWeek("Sábado");
        day.setHoraInicio("11:00");
        day.setHoraFim("14:00");

        FakeDayOfWorkDao fakeDao = new FakeDayOfWorkDao();

        HourDateFragmentInserter inserter = service.generateHourDateFragmentInserter(day, fakeDao);

        assertTrue(rootOfActivity.getChildCount() > 0);

        View trash = inserter.getHourDateFragment().getTrashButton();
        assertNotNull(trash);

        InstrumentationRegistry.getInstrumentation().runOnMainSync(trash::performClick);

        // view removed but DAO not called because id is zero
        assertEquals(0, rootOfActivity.getChildCount());
        assertFalse(fakeDao.deletedCalled);
    }

    @Test
    public void onClickFragment_shouldModifyDayInPopup() throws Exception {
        DayOfWork dayOfWorkExpected = new DayOfWork();
        dayOfWorkExpected.setId(7);
        dayOfWorkExpected.setDayOfWeek("Quarta");
        dayOfWorkExpected.setHoraInicio("10:00");
        dayOfWorkExpected.setHoraFim("19:00");

        FakeDayOfWorkDao fakeDao = new FakeDayOfWorkDao();

        ActivityScenario<ActivityToTest> activityScenario = ActivityScenario.launch(ActivityToTest.class);
        activityScenario.onActivity(activity -> {
            service = new DayOfWorkUiService(
                    activity.findViewById(com.example.worknutri.R.id.layout_test),
                    rootOfActivity);

            HourDateFragmentInserter inserter = service.generateHourDateFragmentInserter(dayOfWorkExpected, fakeDao);

            service.OnClickInHourDateFragment(inserter, dayOfWorkExpected);
            View layout = inserter.getHourDateFragment().getLayout();
            layout.performClick();

        });


        // verify if DatePicker has modified with same dayOfWorkExpected
        DatePickerPopUp datePicker = getDatePickerPopUp();
        DayOfWork current = datePicker.getDayOfWork();
        assertNotNull(current);
        assertEquals(7, current.getId());
        assertEquals(dayOfWorkExpected.getDayOfWeek(), current.getDayOfWeek());
    }

    @Test
    public void generatePopUpOfDatePickerToNewDayOfWorkShouldProvideEmptyDay()  {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);

        scenario.onActivity(activity -> {
            service = new DayOfWorkUiService(activity.findViewById(com.example.worknutri.R.id.layout_test),
                    rootOfActivity);
            DatePickerPopUp datePicker;
            try {
                datePicker = getDatePickerPopUp();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            service.generatePopUpOfDatePickerToNewDayOfWork();

            DayOfWork d = datePicker.getDayOfWork();
            assertNotNull(d);
            assertEquals(0, d.getId());
            assertEquals("", d.getDayOfWeek());
            assertEquals("", d.getHoraInicio());
            assertEquals("", d.getHoraFim());


        });

    }

    @Test
    public void generateHourDateFragmentInserter_shouldReuseInserter_whenSameId() {
        DayOfWork day = new DayOfWork();
        day.setId(5);
        day.setDayOfWeek("Friday");
        day.setHoraInicio("07:00");
        day.setHoraFim("15:00");

        FakeDayOfWorkDao fakeDao = new FakeDayOfWorkDao();

        HourDateFragmentInserter inserter1 = service.generateHourDateFragmentInserter(day, fakeDao);
        // generate again with same DayOfWork object must be retorn same inserter
        HourDateFragmentInserter inserter2 = service.generateHourDateFragmentInserter(day, fakeDao);

        assertSame("Inserter must be the same for same id", inserter1, inserter2);
        // only one view must have been add on container
        assertEquals(1, rootOfActivity.getChildCount());
    }



}