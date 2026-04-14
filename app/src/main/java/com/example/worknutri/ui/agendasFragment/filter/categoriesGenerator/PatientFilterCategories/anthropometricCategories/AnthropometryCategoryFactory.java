package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.anthropometricCategories;

import android.content.Context;

import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PatientFilterPojo;

public abstract class AnthropometryCategoryFactory {

    public static WeightCategory createWeightCategory(Context context, PatientFilterPojo patientFilterPojo) {
        return new WeightCategory(context, patientFilterPojo);
    }

    public static HeightCategory createHeightCategory(Context context, PatientFilterPojo patientFilterPojo) {
        return new HeightCategory(context, patientFilterPojo);
    }
    public static IMCCategory createIMCCategory(Context context, PatientFilterPojo patientFilterPojo) {
        return new IMCCategory(context, patientFilterPojo);
    }
}
