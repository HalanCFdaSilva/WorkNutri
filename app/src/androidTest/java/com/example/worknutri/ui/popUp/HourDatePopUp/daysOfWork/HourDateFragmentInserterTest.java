package com.example.worknutri.ui.popUp.HourDatePopUp.daysOfWork;

import android.widget.LinearLayout;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.ui.ActivityToTest;
import com.example.worknutri.ui.popUp.hourDatePopUp.daysOfWork.HourDateFragmentInserter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class HourDateFragmentInserterTest {

    private LinearLayout layoutToInsert;
    private DayOfWork dayOfWork;
    private HourDateFragmentInserter inserter;

    @Before
    public void setUp() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            layoutToInsert = new LinearLayout(activity);
            dayOfWork = new DayOfWork();
            dayOfWork.setDayOfWeek("Quarta");
            dayOfWork.setHoraInicio("10:00");
            dayOfWork.setHoraFim("16:00");
            inserter = new HourDateFragmentInserter(layoutToInsert, dayOfWork);
        });
    }

    @Test
    public void testFragmentIsInserted() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            inserter.generateAndInsertInLayoutOfHourDateFragment(dayOfWork);
            assertEquals(1, layoutToInsert.getChildCount());
            assertNotNull(inserter.getHourDateFragment().getLayout());
        });
    }

    @Test
    public void testSetDateOfDayOfWorkUpdatesData() {
        DayOfWork newDay = new DayOfWork();
        newDay.setDayOfWeek("Quinta");
        newDay.setHoraInicio("11:00");
        newDay.setHoraFim("17:00");

        inserter.setDateOfDayOfWork(newDay);
        assertEquals("Quinta", inserter.getDayOfWork().getDayOfWeek());
        assertEquals("11:00", inserter.getDayOfWork().getHoraInicio());
        assertEquals("17:00", inserter.getDayOfWork().getHoraFim());
    }

    @Test
    public void testDayOfWorkEquals() {
        DayOfWork sameIdDay = new DayOfWork();
        sameIdDay.setId(dayOfWork.getId());
        assertTrue(inserter.dayOfWorkEquals(sameIdDay));
    }

    @Test
    public void testGenerateAndInsertInLayoutOfHourDateFragment_DoesNotAddIfAlreadyPresent() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            // Insere o fragmento pela primeira vez
            inserter.generateAndInsertInLayoutOfHourDateFragment(dayOfWork);
            int countBefore = layoutToInsert.getChildCount();

            // Tenta inserir novamente (o if será falso)
            inserter.generateAndInsertInLayoutOfHourDateFragment(dayOfWork);
            int countAfter = layoutToInsert.getChildCount();

            // Garante que não foi adicionado novamente
            assertEquals(countBefore, countAfter);
        });
    }

    @Test
    public void testGetHourDateFragmentReturnsInstance() {
        assertNotNull(inserter.getHourDateFragment());

    }

    @Test
    public void testGetDayOfWorkReturnsInstance() {
        DayOfWork dayOfWorkInsideInserter = inserter.getDayOfWork();
        assertNotNull(dayOfWorkInsideInserter);
        assertEquals(dayOfWork, dayOfWorkInsideInserter);
    }

    @Test
    public void testConstructorInitializesFields() {
        assertNotNull(inserter.getHourDateFragment());
        assertEquals(dayOfWork, inserter.getDayOfWork());
        assertNotNull(layoutToInsert);
    }

    @Test
    public void testSetDateOfDayOfWorkWithNull() {
        DayOfWork originalDay = inserter.getDayOfWork();
        inserter.setDateOfDayOfWork(null);
        assertEquals(originalDay.getDayOfWeek(), inserter.getDayOfWork().getDayOfWeek());
        assertEquals(originalDay.getHoraInicio(), inserter.getDayOfWork().getHoraInicio());
        assertEquals(originalDay.getHoraFim(), inserter.getDayOfWork().getHoraFim());
    }

    @Test
    public void testDayOfWorkEqualsWithDifferentIdReturnFalse() {
        DayOfWork differentIdDay = new DayOfWork();
        differentIdDay.setId(dayOfWork.getId() + 1); // ID diferente
        assertFalse(inserter.dayOfWorkEquals(differentIdDay));
    }

    @Test
    public void testDayOfWorkEqualsWithEqualIdReturnTrue() {
        DayOfWork differentIdDay = new DayOfWork();
        differentIdDay.setId(dayOfWork.getId()); // ID diferente
        assertTrue(inserter.dayOfWorkEquals(differentIdDay));
    }
}
