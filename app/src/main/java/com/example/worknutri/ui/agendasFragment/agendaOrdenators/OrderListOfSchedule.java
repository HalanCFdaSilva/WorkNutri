package com.example.worknutri.ui.agendasFragment.agendaOrdenators;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;

import java.util.List;

public class OrderListOfSchedule {

    public List<Clinica> orderToClinicSchedule(ClinicaFilterPojo pojo) {

        ClinicOrdainer clinicOrdainer = new ClinicOrdainer(pojo.getClinicasSelected());
        switch (pojo.getUiState().getOrderBy()) {
            case NAME_ASC: return clinicOrdainer.byName(false);

            case NAME_DESC: return clinicOrdainer.byName(true);

            case DISTRICT: return clinicOrdainer.byDistrict();

            case CITY: return clinicOrdainer.byCity();

            case NUMBER_OF_PATIENTS: return clinicOrdainer
                    .byNumberPatients( pojo.getPacientes());

            case DAY_OF_WEEK: return clinicOrdainer.byDayOfWeek(pojo.getUiState().getDaysOfWeekSelected(), pojo.getDayOfWorkList());
            default: return pojo.getClinicasSelected(); // No ordering applied
        }
    }

    public List<Paciente> orderToAgendaPacientes(PacienteFilterPojo patientFilterPojo) {
        PatientOrdainer patientOrdainer = new PatientOrdainer(patientFilterPojo.getPacienteSelected());
        switch (patientFilterPojo.getState().getOrderBy()) {
            case NAME_ASC: return patientOrdainer.byName(false);

            case NAME_DESC: return patientOrdainer.byName(true);

            case IMC_CATEGORY: return patientOrdainer.byBMICategory(patientFilterPojo.getAntropometriaList());

            case HEIGHT: return patientOrdainer.byHeight(patientFilterPojo.getAntropometriaList());

            case WEIGHT: return patientOrdainer.byWeight(patientFilterPojo.getAntropometriaList());
            case AGE: return patientOrdainer.byAge();

            default: return patientFilterPojo.getPacienteSelected();
        }
    }
}
