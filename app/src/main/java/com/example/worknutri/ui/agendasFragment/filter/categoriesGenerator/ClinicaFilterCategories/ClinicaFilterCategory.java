package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicaFilterCategories;

import android.content.Context;
import android.view.ViewGroup;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.FilterCategories;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;

import java.util.ArrayList;
import java.util.List;

public abstract class ClinicaFilterCategory extends FilterCategories {


    protected List<Clinica> clinicasSelecteds;
    protected  final ClinicaFilterPojo clinicaFilterPojo;
    protected Context context;
    protected ViewGroup viewGroup;


    protected ClinicaFilterCategory(Context context, ClinicaFilterPojo clinicaFilterPojo) {
        super(context);
        this.clinicaFilterPojo = clinicaFilterPojo;
        clinicasSelecteds = new ArrayList<>(clinicaFilterPojo.getClinicas());
        this.context = context;
    }

    public List<Clinica> getSelecteds() {
        return clinicasSelecteds;
    }


    @Override
    public void reset() {
        clinicasSelecteds.clear();
        clinicasSelecteds.addAll(clinicaFilterPojo.getClinicas());
    }
}
