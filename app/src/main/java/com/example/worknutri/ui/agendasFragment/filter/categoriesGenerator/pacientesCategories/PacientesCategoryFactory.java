package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.pacientesCategories;

import android.content.Context;

import com.example.worknutri.ui.agendasFragment.filter.pojos.PacienteFilterPojo;

public abstract class PacientesCategoryFactory {




    public static GenderCategoryGenerator generateGenderCategory(Context context,PacienteFilterPojo pojo) {
        return new GenderCategoryGenerator(context, pojo);
    }
    public static YearCategoryGenerator generateYearCategory(Context context,PacienteFilterPojo pojo) {
        return new YearCategoryGenerator(context, pojo);
    }

    public static PacienteInClinicaCategoryGenerator generatePacienteInClinicaCategory(Context context,PacienteFilterPojo pojo) {
        return new PacienteInClinicaCategoryGenerator(context, pojo);
    }
}
