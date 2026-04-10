package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.patientCategories;

import android.content.Context;

import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;

public abstract class PatientCategoryFactory {




    public static GenderCategory generateGenderCategory(Context context, PacienteFilterPojo pojo) {
        return new GenderCategory(context, pojo);
    }
    public static YearCategory generateYearCategory(Context context, PacienteFilterPojo pojo) {
        return new YearCategory(context, pojo);
    }

    public static PatientInClinicCategory generatePacienteInClinicaCategory(Context context, PacienteFilterPojo pojo) {
        return new PatientInClinicCategory(context, pojo);
    }
}
