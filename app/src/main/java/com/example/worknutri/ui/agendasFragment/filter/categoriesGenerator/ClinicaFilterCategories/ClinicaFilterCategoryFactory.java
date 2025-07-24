package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicaFilterCategories;

import android.content.Context;

import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;

public abstract class ClinicaFilterCategoryFactory {
    public static DayInClinicaCategory generateDayInClinicaCategory(Context context, ClinicaFilterPojo clinicaFilterPojo) {
        return new DayInClinicaCategory(context,clinicaFilterPojo);
    }

    public static HourWorkCategory generateHourWorkCategory(Context context, ClinicaFilterPojo clinicaFilterPojo) {
        return new HourWorkCategory(context, clinicaFilterPojo);
    }
}
