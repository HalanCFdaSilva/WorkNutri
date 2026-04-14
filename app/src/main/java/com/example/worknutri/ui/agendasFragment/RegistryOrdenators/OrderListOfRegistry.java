package com.example.worknutri.ui.agendasFragment.RegistryOrdenators;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicFilterPojo;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PatientFilterPojo;

import java.util.List;

public class OrderListOfRegistry {

    public List<Clinica> orderToClinicSchedule(ClinicFilterPojo pojo) {

        ClinicOrdainer clinicOrdainer = new ClinicOrdainer(pojo.getClinicsSelected());
        switch (pojo.getUiState().getOrderBy()) {
            case NAME_ASC: return clinicOrdainer.byName(false);

            case NAME_DESC: return clinicOrdainer.byName(true);

            case DISTRICT: return clinicOrdainer.byDistrict();

            case CITY: return clinicOrdainer.byCity();

            case NUMBER_OF_PATIENTS: return clinicOrdainer
                    .byNumberPatients( pojo.getPatientList());

            case DAY_OF_WEEK: return clinicOrdainer.byDayOfWeek(pojo.getUiState().getDaysOfWeekSelected(), pojo.getDayOfWorkList());
            default: return pojo.getClinicsSelected(); // No ordering applied
        }
    }

    public List<Paciente> orderToAgendaPacientes(PatientFilterPojo patientFilterPojo) {
        PatientOrdainer patientOrdainer = new PatientOrdainer(patientFilterPojo.getPatientsSelected());
        switch (patientFilterPojo.getState().getOrderBy()) {
            case NAME_ASC: return patientOrdainer.byName(false);

            case NAME_DESC: return patientOrdainer.byName(true);

            case IMC_CATEGORY: return patientOrdainer.byBMICategory(patientFilterPojo.getAnthropometryList());

            case HEIGHT: return patientOrdainer.byHeight(patientFilterPojo.getAnthropometryList());

            case WEIGHT: return patientOrdainer.byWeight(patientFilterPojo.getAnthropometryList());
            case AGE: return patientOrdainer.byAge();

            default: return patientFilterPojo.getPatientsSelected();
        }
    }
}
