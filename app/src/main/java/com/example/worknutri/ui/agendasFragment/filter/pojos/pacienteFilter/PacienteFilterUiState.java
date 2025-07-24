package com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter;

import com.example.worknutri.calcular.ClassificacaoImc;
import com.example.worknutri.ui.agendasFragment.filter.pojos.UiState;
import java.util.ArrayList;
import java.util.List;

public class PacienteFilterUiState extends UiState {
    private char generoSelected = 'N';
    private final List<Long> clinicaIdSelected = new ArrayList<>();

    private final List<ClassificacaoImc> classificacaoImcs = new ArrayList<>();
    private final float[] tupleOfYearSlider;
    private final float[] tupleOfWeightSlider;

    private final float[] tupleOfHeightSlider;




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
