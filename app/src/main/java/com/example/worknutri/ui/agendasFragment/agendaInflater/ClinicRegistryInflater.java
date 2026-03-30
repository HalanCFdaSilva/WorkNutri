package com.example.worknutri.ui.agendasFragment.agendaInflater;

import android.content.Context;
import android.view.ViewGroup;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfRegistryInflater.NameRegistryInflater;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfRegistryInflater.clinicsTypes.CityClinicRegistryInflater;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfRegistryInflater.clinicsTypes.DistrictClinicRegistryInflater;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfRegistryInflater.clinicsTypes.PatientsClinicRegistryInflater;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfRegistryInflater.clinicsTypes.WeekClinicRegistryInflater;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;

public class ClinicRegistryInflater {

    private final ClinicaFilterPojo clinicaFilterPojo;

    public ClinicRegistryInflater(ClinicaFilterPojo clinicaFilterPojo) {
        this.clinicaFilterPojo = clinicaFilterPojo;
    }

    public void inflateSchedule(ViewGroup viewGroup, Context context) {
        RegistryInflater<Clinica> registryInflater = new NameRegistryInflater<>(context);
        switch (clinicaFilterPojo.getUiState().getOrderBy()){
            case DAY_OF_WEEK:{
                registryInflater = new WeekClinicRegistryInflater(context, clinicaFilterPojo.getDayOfWorkList());
                break;
            }
            case CITY:{
                registryInflater = new CityClinicRegistryInflater(context);
                break;
            }
            case NUMBER_OF_PATIENTS:{
                registryInflater = new PatientsClinicRegistryInflater(context, clinicaFilterPojo.getPacientes());
                break;
            }
            case DISTRICT: registryInflater = new DistrictClinicRegistryInflater(context);

        }
        registryInflater.generateAgenda(viewGroup, clinicaFilterPojo.getClinicasSelected());
    }
}
