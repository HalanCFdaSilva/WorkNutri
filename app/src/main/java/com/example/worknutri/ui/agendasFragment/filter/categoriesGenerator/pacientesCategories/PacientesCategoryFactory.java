package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.pacientesCategories;

import android.content.Context;

import com.example.worknutri.ui.agendasFragment.filter.pojos.PacienteFilterPojo;

public class PacientesCategoryFactory {


    private final Context context;

    public PacientesCategoryFactory(Context context) {

        this.context = context;
    }

    public GenderCategoryGenerator generateGenderCategory(PacienteFilterPojo pojo) {
        return new GenderCategoryGenerator(context, pojo);
    }
    public YearCategoryGenerator generateYearCategory(PacienteFilterPojo pojo) {
        return new YearCategoryGenerator(context, pojo);
    }
}
