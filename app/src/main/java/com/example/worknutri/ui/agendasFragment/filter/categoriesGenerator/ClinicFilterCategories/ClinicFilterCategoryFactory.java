package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicFilterCategories;

import android.content.Context;

import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;

public abstract class ClinicFilterCategoryFactory {
    public static DayInClinicCategory generateDayInClinicaCategory(Context context, ClinicaFilterPojo clinicaFilterPojo) {
        return new DayInClinicCategory(context,clinicaFilterPojo);
    }

    public static HourWorkCategory generateHourWorkCategory(Context context, ClinicaFilterPojo clinicaFilterPojo) {
        return new HourWorkCategory(context, clinicaFilterPojo);
    }
}
