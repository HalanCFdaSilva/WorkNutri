package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacienteFilterCategories.antropometriaCategories;

import android.content.Context;

import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;

public abstract class AntropometriaCategoryFactory {

    public static WeightCategory createWeightCategory(Context context, PacienteFilterPojo pacienteFilterPojo) {
        return new WeightCategory(context, pacienteFilterPojo);
    }

    public static HeightCategory createHeightCategory(Context context, PacienteFilterPojo pacienteFilterPojo) {
        return new HeightCategory(context, pacienteFilterPojo);
    }
    public static IMCCategory createIMCCategory(Context context, PacienteFilterPojo pacienteFilterPojo) {
        return new IMCCategory(context, pacienteFilterPojo);
    }
}
