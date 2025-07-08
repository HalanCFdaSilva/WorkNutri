package com.example.worknutri.ui.agendasFragment.filter.pojos.UiState.pacienteFilter;

import com.example.worknutri.ui.agendasFragment.filter.pojos.UiState.UiState;

import java.util.ArrayList;
import java.util.List;

public class PacienteFilterUiState extends UiState {
    private String generoSelected = "NONE";
    private int clinicaIdSelected = -1;
    private List<String> patologiasSelected = new ArrayList<>();

    private PacienteFilterOrderBy OrderBy = PacienteFilterOrderBy.NONE;




    public String getGeneroSelected() {
        return generoSelected;
    }

    public void setGeneroSelected(String generoSelected) {
        this.generoSelected = generoSelected;
    }

    public int getClinicaIdSelected() {
        return clinicaIdSelected;
    }

    public void setClinicaIdSelected(int clinicaIdSelected) {
        this.clinicaIdSelected = clinicaIdSelected;
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

}
