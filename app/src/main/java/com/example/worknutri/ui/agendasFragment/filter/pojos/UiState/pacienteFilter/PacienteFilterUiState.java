package com.example.worknutri.ui.agendasFragment.filter.pojos.UiState.pacienteFilter;

import com.example.worknutri.calcular.ClassificacaoImc;
import com.example.worknutri.ui.agendasFragment.filter.pojos.UiState.UiState;

import java.util.ArrayList;
import java.util.List;

public class PacienteFilterUiState extends UiState {
    private char generoSelected = 'N';
    private final List<Long> clinicaIdSelected = new ArrayList<>();
    private List<String> patologiasSelected = new ArrayList<>();

    private final List<ClassificacaoImc> classificacaoImcs = new ArrayList<>();
    private final float[] tupleOfYearSlider;
    private final float[] tupleOfWeightSlider;

    private final float[] tupleOfHeightSlider;

    private PacienteFilterOrderBy OrderBy = PacienteFilterOrderBy.NONE;


    public PacienteFilterUiState(int minValue, int maxValue) {
        tupleOfYearSlider = new float[]{minValue,maxValue};
        tupleOfWeightSlider = new float[]{0,0};
        tupleOfHeightSlider = new float[]{0,0};
    }

    public char getGeneroSelected() {
        return generoSelected;
    }

    public void setGeneroSelected(char generoSelected) {
        this.generoSelected = generoSelected;
    }

    public List<Long> getClinicaIdSelected() {
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

    public float[] getTupleOfYearSlider() {
        return tupleOfYearSlider;
    }

    public float[] getTupleOfWeightSlider() {
        return tupleOfWeightSlider;
    }

    public float[] getTupleOfHeightSlider() {
        return tupleOfHeightSlider;
    }

    public List<ClassificacaoImc> getClassificacaoImcs() {
        return classificacaoImcs;
    }
}
