package com.example.worknutri.ui.agendasFragment.filter.NavsDirection;

import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.PACIENTE_FILTER_POJO;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PatientFilterPojo;

import java.io.Serializable;


public class NavDirectionPacienteFilter extends NavDirectionsFilter {
    private final PatientFilterPojo pacientePojo;

    public NavDirectionPacienteFilter(int actionId, PatientFilterPojo pojo) {
        super(actionId);
        this.pacientePojo = pojo;
    }

    @NonNull
    @Override
    public Bundle getArguments() {
        Bundle bundle = new Bundle();

        Serializable.class.isAssignableFrom(PatientFilterPojo.class);
        bundle.putSerializable(PACIENTE_FILTER_POJO, pacientePojo);
        return bundle;
    }
}
