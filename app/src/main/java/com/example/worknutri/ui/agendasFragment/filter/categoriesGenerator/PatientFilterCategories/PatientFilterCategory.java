package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.FilterCategories;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PatientFilterPojo;
import java.util.ArrayList;
import java.util.List;


public abstract class PatientFilterCategory extends FilterCategories {

    protected final PatientFilterPojo pojo;
    protected List<Paciente> pacientesInsideFilter;

    protected ViewGroup viewGroup;


    public PatientFilterCategory(Context context, PatientFilterPojo patientFilterPojo) {
        super(context);
        pojo = patientFilterPojo;
        pacientesInsideFilter = new ArrayList<>(pojo.getPatientList());

    }

    @Override
    public ViewGroup generateCategory(LayoutInflater layoutInflater) {
        viewGroup = super.generateCategory(layoutInflater);
        return viewGroup;
    }

    public List<Paciente> getSelecteds() {
        return pacientesInsideFilter;
    }

    @Override
    public void reset() {
        if (viewGroup != null) {
            resetLayout();
        }
        pacientesInsideFilter.clear();
        pacientesInsideFilter.addAll(pojo.getPatientList());
    }

    protected abstract void resetLayout();


}

