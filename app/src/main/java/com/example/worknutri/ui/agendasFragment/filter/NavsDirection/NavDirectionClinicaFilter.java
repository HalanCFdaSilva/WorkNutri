package com.example.worknutri.ui.agendasFragment.filter.NavsDirection;

import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.CLINICA_FILTER_POJO;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.worknutri.ui.agendasFragment.filter.clinicaFilter.ClinicaRegistryFilterFragment;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicFilterPojo;

import java.io.Serializable;

public class NavDirectionClinicaFilter extends NavDirectionsFilter {

    private final ClinicFilterPojo clinicFilterPojo;


    public NavDirectionClinicaFilter(int actionId, ClinicFilterPojo clinicFilterPojo) {
        super(actionId);

        this.clinicFilterPojo = clinicFilterPojo;
    }



    @NonNull
    @Override
    public Bundle getArguments() {
        Bundle bundle = new Bundle();

        Serializable.class.isAssignableFrom(ClinicaRegistryFilterFragment.class);
        bundle.putSerializable(CLINICA_FILTER_POJO, clinicFilterPojo);
        return bundle;
    }
}
