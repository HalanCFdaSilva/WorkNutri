package com.example.worknutri.ui.agendasFragment.filter.pojos.UiState.pacienteFilter;

import com.example.worknutri.ui.agendasFragment.filter.pojos.UiState.UiState;

import java.util.ArrayList;
import java.util.List;

public class PacienteFilterUiState extends UiState {
    private char generoSelected = 'N';
    private List<Integer> clinicaIdSelected = new ArrayList<>();
    private List<String> patologiasSelected = new ArrayList<>();

    private float[] valuesOfRangeSlider; // Default values for age range slider

    private PacienteFilterOrderBy OrderBy = PacienteFilterOrderBy.NONE;


    public PacienteFilterUiState(int minValue, int maxValue) {
        valuesOfRangeSlider = new float[]{minValue,maxValue}; // Default age range from 0 to 100
    }

    public char getGeneroSelected() {
        return generoSelected;
    }

    public void setGeneroSelected(char generoSelected) {
        this.generoSelected = generoSelected;
    }

    public List<Integer> getClinicaIdSelected() {
        return clinicaIdSelected;
    }



    public List<String> getPatologiasSelected() {
        return patologiasSelected;
    }

    public void setPatologiasSelected(List<String> patologiasSelected) {
        this.patologiasSelected = patologiasSelected;
    }
    public PacienteFilterOrderBy getOrderBy() {
        return OrderBy;
    }

    public void setOrderBy(PacienteFilterOrderBy orderBy) {
        OrderBy = orderBy;
    }

    public float[] getValuesOfRangeSlider() {
        return valuesOfRangeSlider;
    }

    public void setValuesOfRangeSlider(float minValue, float maxValue) {
        this.valuesOfRangeSlider[0] = minValue;
        this.valuesOfRangeSlider[1] = maxValue;
    }
}
