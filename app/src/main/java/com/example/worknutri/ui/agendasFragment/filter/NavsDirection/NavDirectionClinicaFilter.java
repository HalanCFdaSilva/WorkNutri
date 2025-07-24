package com.example.worknutri.ui.agendasFragment.filter.NavsDirection;

import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.CLINICA_FILTER_POJO;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.worknutri.ui.agendasFragment.filter.clinicaFilter.ClinicaFilterFragment;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;

import java.io.Serializable;

public class NavDirectionClinicaFilter extends NavDirectionsFilter {

    private final ClinicaFilterPojo clinicaFilterPojo;


    public NavDirectionClinicaFilter(int actionId, ClinicaFilterPojo clinicaFilterPojo) {
        super(actionId);

        this.clinicaFilterPojo = clinicaFilterPojo;
    }



    @NonNull
    @Override
    public Bundle getArguments() {
        Bundle bundle = new Bundle();

        Serializable.class.isAssignableFrom(ClinicaFilterFragment.class);
        bundle.putSerializable(CLINICA_FILTER_POJO, clinicaFilterPojo);
        return bundle;
    }
}
