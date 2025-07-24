package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacienteFilterCategories;

import android.content.Context;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.FilterCategories;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;
import java.util.ArrayList;
import java.util.List;


public abstract class PacientesFilterCategory extends FilterCategories {

    protected final PacienteFilterPojo pojo;
    protected List<Paciente> pacientesInsideFilter;


    public PacientesFilterCategory(Context context, PacienteFilterPojo pacienteFilterPojo) {
        super(context);
        pojo = pacienteFilterPojo;
        pacientesInsideFilter = new ArrayList<>(pojo.getPacientes());

    }

    public List<Paciente> getSelecteds() {
        return pacientesInsideFilter;
    }

    @Override
    public void reset() {
        pacientesInsideFilter.clear();
        pacientesInsideFilter.addAll(pojo.getPacientes());
    }



}

