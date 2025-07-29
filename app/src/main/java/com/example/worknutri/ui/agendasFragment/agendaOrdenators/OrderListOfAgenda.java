package com.example.worknutri.ui.agendasFragment.agendaOrdenators;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;

import java.util.List;

public class OrderListOfAgenda {

    public List<Clinica> orderToAgendaClinicas(ClinicaFilterPojo pojo) {

        ClinicaOrder clinicaOrder = new ClinicaOrder(pojo.getClinicasSelected());
        switch (pojo.getUiState().getOrderBy()) {
            case NAME_ASC: return clinicaOrder.byName(false);

            case NAME_DESC: return clinicaOrder.byName(true);

            case BAIRRO: return clinicaOrder.byBairro();

            case CITY: return clinicaOrder.byCidade();

            case NUMBER_OF_PATIENTS: return clinicaOrder
                    .byNumberPatients( pojo.getPacientes());

            case DAY_OF_WEEK: return clinicaOrder.byDayOfWeek(pojo.getUiState().getDaysOfWeekSelected(), pojo.getDayOfWorkList());
            default: return pojo.getClinicasSelected(); // No ordering applied
        }
    }

    public List<Paciente> orderToAgendaPacientes(PacienteFilterPojo pacienteFilterPojo) {
        PacienteOrder pacienteOrder = new PacienteOrder(pacienteFilterPojo.getPacienteSelected());
        switch (pacienteFilterPojo.getState().getOrderBy()) {
            case NAME_ASC: return pacienteOrder.byName(false);

            case NAME_DESC: return pacienteOrder.byName(true);

            case IMC_CATEGORY: return pacienteOrder.byIMCCategory(pacienteFilterPojo.getAntropometriaList());

            case HEIGHT: return pacienteOrder.byHeight(pacienteFilterPojo.getAntropometriaList());

            case WEIGHT: return pacienteOrder.byWeight(pacienteFilterPojo.getAntropometriaList());
            case AGE: return pacienteOrder.byAge();

            default: return pacienteFilterPojo.getPacienteSelected();
        }
    }
}
