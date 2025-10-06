package com.example.worknutri.ui.popUp.datePicker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.ui.popUp.hourDatePopUp.datePicker.DayOfWorkViewBinder;

import org.junit.Before;
import org.junit.Test;

public class DayOfWorkViewBinderTest {

    private DayOfWork dayOfWork;
    private DayOfWorkViewBinder dayOfWorkViewBinder;
    private ViewGroup mockViewGroup;

    @Before
    public void setUp() {
        dayOfWork = new DayOfWork();
        dayOfWorkViewBinder = new DayOfWorkViewBinder(dayOfWork);

        mockViewGroup = mock(ViewGroup.class);
        NumberPicker mockHourStartPicker = mock(NumberPicker.class);
        NumberPicker mockHourEndPicker = mock(NumberPicker.class);
        NumberPicker mockWeekDayPicker = mock(NumberPicker.class);

        when(mockViewGroup.findViewById(com.example.worknutri.R.id.date_picker_pop_up_number_picker_hour_start))
                .thenReturn(mockHourStartPicker);
        when(mockViewGroup.findViewById(com.example.worknutri.R.id.date_picker_pop_up_number_picker_hour_end))
                .thenReturn(mockHourEndPicker);
        when(mockViewGroup.findViewById(com.example.worknutri.R.id.date_picker_pop_up_number_picker_week_day))
                .thenReturn(mockWeekDayPicker);

        when(mockHourStartPicker.getValue()).thenReturn(1);
        when(mockHourStartPicker.getDisplayedValues()).thenReturn(new String[]{"08:00", "09:00", "10:00"});
        when(mockHourEndPicker.getValue()).thenReturn(2);
        when(mockHourEndPicker.getDisplayedValues()).thenReturn(new String[]{"08:00", "09:00", "10:00"});
        when(mockWeekDayPicker.getValue()).thenReturn(0);
        when(mockWeekDayPicker.getDisplayedValues()).thenReturn(new String[]{"Segunda", "Terça", "Quarta"});
    }

    @Test
    public void testBind() {
        dayOfWorkViewBinder.bind(mockViewGroup);

        assertEquals("09:00", dayOfWork.getHoraInicio());
        assertEquals("10:00", dayOfWork.getHoraFim());
        assertEquals("Segunda", dayOfWork.getDayOfWeek());
    }

    @Test
    public void testGetDayOfWork() {
        assertSame(dayOfWork, dayOfWorkViewBinder.getDayOfWork());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testBindWithNullViewGroup() {
        dayOfWorkViewBinder.bind(null);
    }


    @Test
    public void testHourStartPickerIsNullDontUpdateHoraInicioInDayOfWork() {
        when(mockViewGroup.findViewById(com.example.worknutri.R.id.date_picker_pop_up_number_picker_hour_start))
                .thenReturn(null);

        String hourExpected = dayOfWork.getHoraInicio();
        dayOfWorkViewBinder.bind(mockViewGroup);
        assertEquals(hourExpected, dayOfWork.getHoraInicio());
        assertEquals("10:00", dayOfWork.getHoraFim());
        assertEquals("Segunda", dayOfWork.getDayOfWeek());
    }

    @Test
    public void testHourEndPickerIsNullDontUpdateHoraFinalInDayOfWork() {
        when(mockViewGroup.findViewById(com.example.worknutri.R.id.date_picker_pop_up_number_picker_hour_end))
                .thenReturn(null);

        String hourExpected = dayOfWork.getHoraFim();
        dayOfWorkViewBinder.bind(mockViewGroup);

        assertEquals("09:00", dayOfWork.getHoraInicio());
        assertEquals(hourExpected,dayOfWork.getHoraFim());
        assertEquals("Segunda", dayOfWork.getDayOfWeek());
    }

    @Test
    public void testWeekDayPickerIsNullDontUpdateDayOfWeekInDayOfWork() {
        when(mockViewGroup.findViewById(com.example.worknutri.R.id.date_picker_pop_up_number_picker_week_day))
                .thenReturn(null);

        String hourExpected = dayOfWork.getDayOfWeek();
        dayOfWorkViewBinder.bind(mockViewGroup);

        assertEquals("09:00", dayOfWork.getHoraInicio());
        assertEquals("10:00", dayOfWork.getHoraFim());
        assertEquals(hourExpected,dayOfWork.getDayOfWeek());

    }

}
