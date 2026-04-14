package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicFilterCategories;

import android.content.Context;

import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicFilterPojo;

public abstract class ClinicFilterCategoryFactory {
    public static DayInClinicCategory generateDayInClinicaCategory(Context context, ClinicFilterPojo clinicFilterPojo) {
        return new DayInClinicCategory(context, clinicFilterPojo);
    }

    public static HourWorkCategory generateHourWorkCategory(Context context, ClinicFilterPojo clinicFilterPojo) {
        return new HourWorkCategory(context, clinicFilterPojo);
    }
}
