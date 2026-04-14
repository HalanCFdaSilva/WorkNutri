package com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter;

import com.example.worknutri.ui.agendasFragment.filter.pojos.UiState;

import java.util.ArrayList;
import java.util.List;

public class ClinicFilterUiState extends UiState {

    private final List<String> daysOfWeekSelected;
    private final float[] hoursSelected = new float[]{0,0};

    public ClinicFilterUiState() {
        daysOfWeekSelected = new ArrayList<>();
    }


    public List<String> getDaysOfWeekSelected() {
        return daysOfWeekSelected;
    }

    public float[] getHoursSelected() {
        return hoursSelected;
    }
}
