package com.example.worknutri.ui.agendasFragment.filter.pojos.UiState.pacienteFilter;

import com.example.worknutri.ui.agendasFragment.filter.pojos.UiState.UiState;

import java.util.ArrayList;
import java.util.List;

public class PacienteFilterUiState extends UiState {
    private char generoSelected = 'N';
    private List<Integer> clinicaIdSelected = new ArrayList<>();
    private List<String> patologiasSelected = new ArrayList<>();

    private float[] valuesOfYearSlider;
    private float[] valuesOfWeightSlider; // Default height range from 0 to 200 cm

    private PacienteFilterOrderBy OrderBy = PacienteFilterOrderBy.NONE;


    public PacienteFilterUiState(int minValue, int maxValue) {
        valuesOfYearSlider = new float[]{minValue,maxValue};
        valuesOfWeightSlider = new float[]{0,0};
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

    public float[] getValuesOfYearSlider() {
        return valuesOfYearSlider;
    }

    public float[] getValuesOfWeightSlider() {
        return valuesOfWeightSlider;
    }
}
