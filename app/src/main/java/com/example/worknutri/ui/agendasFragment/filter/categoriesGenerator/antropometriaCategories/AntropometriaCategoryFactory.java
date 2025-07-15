package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.antropometriaCategories;

import android.content.Context;

import com.example.worknutri.ui.agendasFragment.filter.pojos.PacienteFilterPojo;

public abstract class AntropometriaCategoryFactory {

    public static WeigthCategory createPesoCategory(Context context, PacienteFilterPojo pacienteFilterPojo) {
        return new WeigthCategory(context, pacienteFilterPojo);
    }
}
