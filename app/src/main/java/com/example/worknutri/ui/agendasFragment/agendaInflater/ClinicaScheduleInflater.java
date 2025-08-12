package com.example.worknutri.ui.agendasFragment.agendaInflater;

import android.content.Context;
import android.view.ViewGroup;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.NameScheduleInflater;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.clinicsTypes.CityClinicScheduleInflater;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.clinicsTypes.DistrictClinicScheduleInflater;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.clinicsTypes.PatientsClinicScheduleInflater;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.clinicsTypes.WeekClinicScheduleInflater;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;

public class ClinicaScheduleInflater {

    private final ClinicaFilterPojo clinicaFilterPojo;

    public ClinicaScheduleInflater(ClinicaFilterPojo clinicaFilterPojo) {
        this.clinicaFilterPojo = clinicaFilterPojo;
    }

    public void inflateSchedule(ViewGroup viewGroup, Context context) {
        ScheduleInflater<Clinica> scheduleInflater = new NameScheduleInflater<>(context);
        switch (clinicaFilterPojo.getUiState().getOrderBy()){
            case DAY_OF_WEEK:{
                scheduleInflater = new WeekClinicScheduleInflater(context, clinicaFilterPojo.getDayOfWorkList());
                break;
            }
            case CITY:{
                scheduleInflater = new CityClinicScheduleInflater(context);
                break;
            }
            case NUMBER_OF_PATIENTS:{
                scheduleInflater = new PatientsClinicScheduleInflater(context, clinicaFilterPojo.getPacientes());
                break;
            }
            case DISTRICT: scheduleInflater = new DistrictClinicScheduleInflater(context);

        }
        scheduleInflater.generateAgenda(viewGroup, clinicaFilterPojo.getClinicasSelected());
    }
}
