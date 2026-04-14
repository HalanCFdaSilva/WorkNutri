package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicFilterCategories;

import android.content.Context;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.FilterCategories;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicFilterPojo;

import java.util.ArrayList;
import java.util.List;

public abstract class ClinicFilterCategory extends FilterCategories {


    protected List<Clinica> clinicasSelecteds;
    protected  final ClinicFilterPojo clinicFilterPojo;
    protected Context context;



    protected ClinicFilterCategory(Context context, ClinicFilterPojo clinicFilterPojo) {
        super(context);
        this.clinicFilterPojo = clinicFilterPojo;
        clinicasSelecteds = new ArrayList<>(clinicFilterPojo.getClinicsList());
        this.context = context;
    }

    public List<Clinica> getSelecteds() {
        return clinicasSelecteds;
    }


    @Override
    public void reset() {
        clinicasSelecteds.clear();
        clinicasSelecteds.addAll(clinicFilterPojo.getClinicsList());
    }
}
