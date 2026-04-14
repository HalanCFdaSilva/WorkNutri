package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.patientCategories;

import android.content.Context;

import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PatientFilterPojo;

public abstract class PatientCategoryFactory {




    public static GenderCategory generateGenderCategory(Context context, PatientFilterPojo pojo) {
        return new GenderCategory(context, pojo);
    }
    public static YearCategory generateYearCategory(Context context, PatientFilterPojo pojo) {
        return new YearCategory(context, pojo);
    }

    public static PatientInClinicCategory generatePacienteInClinicaCategory(Context context, PatientFilterPojo pojo) {
        return new PatientInClinicCategory(context, pojo);
    }
}
