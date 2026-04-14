package com.example.worknutri.ui.agendasFragment.registryInflater;

import android.content.Context;
import android.view.ViewGroup;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.NameRegistryInflater;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.clinicsTypes.CityClinicRegistryInflater;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.clinicsTypes.DistrictClinicRegistryInflater;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.clinicsTypes.PatientsClinicRegistryInflater;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.clinicsTypes.WeekClinicRegistryInflater;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicFilterPojo;

public class ClinicRegistryInflater {

    private final ClinicFilterPojo clinicFilterPojo;

    public ClinicRegistryInflater(ClinicFilterPojo clinicFilterPojo) {
        this.clinicFilterPojo = clinicFilterPojo;
    }

    public void inflateSchedule(ViewGroup viewGroup, Context context) {
        RegistryInflater<Clinica> registryInflater = new NameRegistryInflater<>(context);
        switch (clinicFilterPojo.getUiState().getOrderBy()){
            case DAY_OF_WEEK:{
                registryInflater = new WeekClinicRegistryInflater(context, clinicFilterPojo.getDayOfWorkList());
                break;
            }
            case CITY:{
                registryInflater = new CityClinicRegistryInflater(context);
                break;
            }
            case NUMBER_OF_PATIENTS:{
                registryInflater = new PatientsClinicRegistryInflater(context, clinicFilterPojo.getPatientList());
                break;
            }
            case DISTRICT: registryInflater = new DistrictClinicRegistryInflater(context);

        }
        registryInflater.generateAgenda(viewGroup, clinicFilterPojo.getClinicsSelected());
    }
}
