package com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ClinicFilterUiStateTest {

    private ClinicFilterUiState clinicFilterUiState;

    @Before
    public void setUp() {
        clinicFilterUiState = new ClinicFilterUiState();
    }

    @Test
    public void testConstructorInitializesDaysOfWeekSelectedList() {
        assertNotNull(clinicFilterUiState.getDaysOfWeekSelected());
        assertTrue(clinicFilterUiState.getDaysOfWeekSelected().isEmpty());
    }

    @Test
    public void testConstructorInitializesHoursSelected() {
        assertNotNull(clinicFilterUiState.getHoursSelected());
        assertEquals(2, clinicFilterUiState.getHoursSelected().length);
    }

    @Test
    public void testHoursSelectedInitializedWithZeros() {
        float[] hoursSelected = clinicFilterUiState.getHoursSelected();
        assertEquals(0.0f, hoursSelected[0], 0.0f);
        assertEquals(0.0f, hoursSelected[1], 0.0f);
    }

    @Test
    public void testAddDayOfWeekToSelected() {
        List<String> daysOfWeek = clinicFilterUiState.getDaysOfWeekSelected();
        daysOfWeek.add("MONDAY");

        assertEquals(1, clinicFilterUiState.getDaysOfWeekSelected().size());
        assertEquals("MONDAY", clinicFilterUiState.getDaysOfWeekSelected().get(0));
    }

    @Test
    public void testAddMultipleDaysOfWeek() {
        List<String> daysOfWeek = clinicFilterUiState.getDaysOfWeekSelected();
        daysOfWeek.add("MONDAY");
        daysOfWeek.add("WEDNESDAY");
        daysOfWeek.add("FRIDAY");

        assertEquals(3, clinicFilterUiState.getDaysOfWeekSelected().size());
        assertTrue(clinicFilterUiState.getDaysOfWeekSelected().contains("MONDAY"));
        assertTrue(clinicFilterUiState.getDaysOfWeekSelected().contains("WEDNESDAY"));
        assertTrue(clinicFilterUiState.getDaysOfWeekSelected().contains("FRIDAY"));
    }

    @Test
    public void testRemoveDayOfWeekFromSelected() {
        List<String> daysOfWeek = clinicFilterUiState.getDaysOfWeekSelected();
        daysOfWeek.add("MONDAY");
        daysOfWeek.add("WEDNESDAY");
        daysOfWeek.remove("MONDAY");

        assertEquals(1, clinicFilterUiState.getDaysOfWeekSelected().size());
        assertEquals("WEDNESDAY", clinicFilterUiState.getDaysOfWeekSelected().get(0));
    }

    @Test
    public void testSetHourStartTime() {
        float[] hoursSelected = clinicFilterUiState.getHoursSelected();
        hoursSelected[0] = 9.0f;

        assertEquals(9.0f, clinicFilterUiState.getHoursSelected()[0], 0.0f);
    }

    @Test
    public void testSetHourEndTime() {
        float[] hoursSelected = clinicFilterUiState.getHoursSelected();
        hoursSelected[1] = 17.0f;

        assertEquals(17.0f, clinicFilterUiState.getHoursSelected()[1], 0.0f);
    }

    @Test
    public void testSetBothHours() {
        float[] hoursSelected = clinicFilterUiState.getHoursSelected();
        hoursSelected[0] = 8.5f;
        hoursSelected[1] = 18.5f;

        assertEquals(8.5f, clinicFilterUiState.getHoursSelected()[0], 0.0f);
        assertEquals(18.5f, clinicFilterUiState.getHoursSelected()[1], 0.0f);
    }

    @Test
    public void testClearDaysOfWeekSelected() {
        List<String> daysOfWeek = clinicFilterUiState.getDaysOfWeekSelected();
        daysOfWeek.add("MONDAY");
        daysOfWeek.add("WEDNESDAY");
        daysOfWeek.clear();

        assertTrue(clinicFilterUiState.getDaysOfWeekSelected().isEmpty());
    }

    @Test
    public void testHoursSelectedArrayLength() {
        assertEquals(2, clinicFilterUiState.getHoursSelected().length);
    }

    @Test
    public void testMultipleInstancesIndependent() {
        ClinicFilterUiState state1 = new ClinicFilterUiState();
        ClinicFilterUiState state2 = new ClinicFilterUiState();

        state1.getDaysOfWeekSelected().add("MONDAY");
        state1.getHoursSelected()[0] = 10.0f;

        assertEquals(1, state1.getDaysOfWeekSelected().size());
        assertEquals(0, state2.getDaysOfWeekSelected().size());
        assertEquals(0.0f, state2.getHoursSelected()[0], 0.0f);
    }

    @Test
    public void testGetDaysOfWeekSelectedReturnsSameList() {
        List<String> firstCall = clinicFilterUiState.getDaysOfWeekSelected();
        firstCall.add("TUESDAY");

        List<String> secondCall = clinicFilterUiState.getDaysOfWeekSelected();
        assertEquals(1, secondCall.size());
        assertEquals("TUESDAY", secondCall.get(0));
    }

    @Test
    public void testGetHoursSelectedReturnsSameArray() {
        float[] firstCall = clinicFilterUiState.getHoursSelected();
        firstCall[0] = 7.0f;

        float[] secondCall = clinicFilterUiState.getHoursSelected();
        assertEquals(7.0f, secondCall[0], 0.0f);
    }

    @Test
    public void testDaysOfWeekContainsDuplicate() {
        List<String> daysOfWeek = clinicFilterUiState.getDaysOfWeekSelected();
        daysOfWeek.add("MONDAY");
        daysOfWeek.add("MONDAY");

        // ArrayList allows duplicates, so we check if both are added
        assertEquals(2, clinicFilterUiState.getDaysOfWeekSelected().size());
    }
}

