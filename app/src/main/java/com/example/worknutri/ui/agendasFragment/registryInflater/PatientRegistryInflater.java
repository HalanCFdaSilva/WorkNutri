package com.example.worknutri.ui.agendasFragment.registryInflater;

import android.content.Context;
import android.view.ViewGroup;

import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.NameRegistryInflater;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.patientTypes.AgeRegistryInflater;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.patientTypes.HeightAntropometryRegistryInflater;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.patientTypes.IMCAntropometryRegistryInflater;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.patientTypes.WeightAntropometryRegistryInflater;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;

public class PatientRegistryInflater {
    private final PacienteFilterPojo pacienteFilterPojo;

    public PatientRegistryInflater(PacienteFilterPojo pacienteFilterPojo) {
        this.pacienteFilterPojo = pacienteFilterPojo;
    }

    public void inflateSchedule(ViewGroup viewGroup, Context context){
        RegistryInflater<Paciente> registryInflater = new NameRegistryInflater<>(context);
        switch (pacienteFilterPojo.getState().getOrderBy()) {
            case IMC_CATEGORY: {
                registryInflater = new IMCAntropometryRegistryInflater(context, pacienteFilterPojo.getAntropometriaList());
                break;
            }
            case HEIGHT: {
                registryInflater = new HeightAntropometryRegistryInflater(context,pacienteFilterPojo.getAntropometriaList());
                break;
            }
            case WEIGHT: {
                registryInflater = new WeightAntropometryRegistryInflater(context, pacienteFilterPojo.getAntropometriaList());
                break;
            }
            case AGE: registryInflater = new AgeRegistryInflater(context);

        }
        registryInflater.generateAgenda(viewGroup, pacienteFilterPojo.getPacienteSelected());
    }
}
