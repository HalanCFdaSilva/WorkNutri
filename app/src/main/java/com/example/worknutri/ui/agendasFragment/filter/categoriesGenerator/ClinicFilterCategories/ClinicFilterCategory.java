package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicFilterCategories;

import android.content.Context;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.FilterCategories;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;

import java.util.ArrayList;
import java.util.List;

public abstract class ClinicFilterCategory extends FilterCategories {


    protected List<Clinica> clinicasSelecteds;
    protected  final ClinicaFilterPojo clinicaFilterPojo;
    protected Context context;



    protected ClinicFilterCategory(Context context, ClinicaFilterPojo clinicaFilterPojo) {
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
