package com.example.worknutri.ui.agendasFragment.filter.pacientesFilter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UiState implements Serializable {
    private String generoSelected = "NONE";
    private int clinicaIdSelected = -1;
    private List<String> patologiasSelected = new ArrayList<>();
    private boolean inOrder = true;
    private String OrderBy = "NONE";



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

    public boolean isInOrder() {
        return inOrder;
    }

    public void setInOrder(boolean inOrder) {
        this.inOrder = inOrder;
    }

}
