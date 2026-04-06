package com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.patientTypes;

import android.content.Context;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import java.util.List;


public class WeightAntropometryRegistryInflater extends AntropometryRegistryInflater {


    public WeightAntropometryRegistryInflater(Context context, List<Antropometria> antropometriaList) {
        super(context, antropometriaList);
    }


    @Override
    protected String getCategoryString(Antropometria antropometria) {
        return antropometria.getPeso();
    }

    @Override
    protected String getMeasureUnit() {
        return "Kg";
    }
}
