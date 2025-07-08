package com.example.worknutri.ui.agendasFragment.filter.NavsDirection;

import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.PACIENTE_FILTER_POJO;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.worknutri.ui.agendasFragment.filter.pojos.PacienteFilterPojo;

import java.io.Serializable;


public class NavDirectionPacienteFilter extends NavDirectionsFilter {
    private final PacienteFilterPojo pacientePojo;

    public NavDirectionPacienteFilter(int actionId, PacienteFilterPojo pojo) {
        super(actionId);
        this.pacientePojo = pojo;
    }

    @NonNull
    @Override
    public Bundle getArguments() {
        Bundle bundle = new Bundle();

        if (Serializable.class.isAssignableFrom(PacienteFilterPojo.class)) {
            bundle.putSerializable(PACIENTE_FILTER_POJO, pacientePojo);
        }else {
            throw new UnsupportedOperationException("Serializable is not supported for this type of List");
        }
        return bundle;
    }
}
