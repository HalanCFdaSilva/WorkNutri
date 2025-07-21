package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacienteFilterCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.CategoriesGenerator;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.CategoriesGeneratorUtil;
import com.example.worknutri.ui.agendasFragment.filter.pojos.PacienteFilterPojo;

import java.util.ArrayList;
import java.util.List;


public abstract class PacientesFilterCategories implements CategoriesGenerator {
    protected final CategoriesGeneratorUtil agendaFilter;
    protected final PacienteFilterPojo pojo;
    protected List<Paciente> pacientesInsideFilter;
    protected final Context context;
    protected ViewGroup viewGroup;

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



    @Override
    public ViewGroup generateCategory(LayoutInflater layoutInflater) {
        this.viewGroup = generateViewGroup(layoutInflater);
        return viewGroup;
    }
    protected abstract ViewGroup generateViewGroup(LayoutInflater layoutInflater);


    protected  void resetChipGroup() {
        ReseterOfPacientesFilterCategories reseter = new ReseterOfPacientesFilterCategories(pojo, pacientesInsideFilter);
        reseter.resetChipGroup(viewGroup.findViewById(R.id.filter_category_chipgroup));

    }

    protected  void resetSlider(float[] values) {
        ReseterOfPacientesFilterCategories reseter = new ReseterOfPacientesFilterCategories(pojo, pacientesInsideFilter);
        reseter.resetSlider(viewGroup.findViewById(R.id.filter_category_rangeslider),values);

    }




    }

