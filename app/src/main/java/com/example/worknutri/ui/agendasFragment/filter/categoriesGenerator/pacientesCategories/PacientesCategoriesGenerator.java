package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.pacientesCategories;

import android.content.Context;

import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.CategoriesGenerator;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.CategoriesGeneratorUtil;
import com.example.worknutri.ui.agendasFragment.filter.pojos.PacienteFilterPojo;

import java.util.ArrayList;
import java.util.List;


public abstract class PacientesCategoriesGenerator implements CategoriesGenerator {
    protected final CategoriesGeneratorUtil agendaFilter;
    protected final PacienteFilterPojo pojo;
    protected List<Paciente> pacientesInsideFilter;

    public PacientesCategoriesGenerator(Context context, PacienteFilterPojo pacienteFilterPojo) {
        agendaFilter = new CategoriesGeneratorUtil(context);
        pojo = pacienteFilterPojo;
        pacientesInsideFilter = new ArrayList<>(pojo.getPacienteSelected());
    }








}

