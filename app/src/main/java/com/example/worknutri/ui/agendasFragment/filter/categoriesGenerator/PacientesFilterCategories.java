package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator;

import android.content.Context;

import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.pojos.PacienteFilterPojo;

import java.util.ArrayList;
import java.util.List;


public abstract class PacientesFilterCategories implements CategoriesGenerator {
    protected final CategoriesGeneratorUtil agendaFilter;
    protected final PacienteFilterPojo pojo;
    protected List<Paciente> pacientesInsideFilter;
    protected final Context context;

    public PacientesFilterCategories(Context context, PacienteFilterPojo pacienteFilterPojo) {
        agendaFilter = new CategoriesGeneratorUtil(context);
        pojo = pacienteFilterPojo;
        pacientesInsideFilter = new ArrayList<>(pojo.getPacientes());
        this.context = context;
    }

    @Override
    public <T> List<Paciente> getSelecteds() {
        return pacientesInsideFilter;
    }
}

