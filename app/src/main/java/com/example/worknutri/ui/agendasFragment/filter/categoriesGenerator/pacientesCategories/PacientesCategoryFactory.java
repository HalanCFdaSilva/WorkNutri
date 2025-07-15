package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.pacientesCategories;

import android.content.Context;

import com.example.worknutri.ui.agendasFragment.filter.pojos.PacienteFilterPojo;

public abstract class PacientesCategoryFactory {




    public static GenderCategory generateGenderCategory(Context context, PacienteFilterPojo pojo) {
        return new GenderCategory(context, pojo);
    }
    public static YearCategory generateYearCategory(Context context, PacienteFilterPojo pojo) {
        return new YearCategory(context, pojo);
    }

    public static PacienteInClinicaCategory generatePacienteInClinicaCategory(Context context, PacienteFilterPojo pojo) {
        return new PacienteInClinicaCategory(context, pojo);
    }
}
