package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicFilterCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.worknutri.R;
import com.example.worknutri.ui.agendasFragment.filter.pojos.PojoUtil;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicFilterPojo;
import com.google.android.material.slider.RangeSlider;
import java.util.List;
import java.util.Locale;

public class HourWorkCategory extends ClinicFilterCategory {
    protected HourWorkCategory(Context context, ClinicFilterPojo clinicFilterPojo) {
        super(context, clinicFilterPojo);
    }

    @Override
    protected ViewGroup generateView(LayoutInflater layoutInflater) {
        ViewGroup viewGroup = categoriesGeneratorUtil.generateCategory(layoutInflater, "Horário de Trabalho:");
        viewGroup.setId(R.id.filter_category_clinic_hour);
        RangeSlider rangeSlider = generateRangeSlider();
        rangeSlider.setStepSize(1);
        onSliderRangeSlider(rangeSlider);
        selectInitialValues(rangeSlider);
        ViewGroup linearLayout = viewGroup.findViewById(R.id.registry_filter_category_intern_layout);
        linearLayout.addView(rangeSlider);
        return viewGroup;
    }



    private RangeSlider generateRangeSlider() {

        RangeSlider rangeSlider = categoriesGeneratorUtil.generateRangeSlider(0, 24*60);
        rangeSlider.setStepSize(1f);
        rangeSlider.setLabelFormatter(f-> String.format(Locale.getDefault(),
                "%02d:%02d", (int) f / 60, (int) f % 60));
        onSliderRangeSlider(rangeSlider);
        selectInitialValues(rangeSlider);
        return rangeSlider;
    }

    private void onSliderRangeSlider(RangeSlider rangeSlider) {
        rangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {

            }
            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                List<Float> values = slider.getValues();

                PojoUtil.setValuesOfFloatTuple(clinicFilterPojo.getUiState().getHoursSelected(), values.get(0), values.get(1));
                updateSelectedClinicsByHourRange(values.get(0), values.get(1));

            }


        });
    }

    private void updateSelectedClinicsByHourRange(float hourStartInMinute, float hourEndInMinute) {
        clinicasSelecteds.clear();
        clinicasSelecteds.addAll(
                new ClinicsSelector(clinicFilterPojo.getClinicsList()).
                        byRangeOfHourWork(List.of(hourStartInMinute, hourEndInMinute), clinicFilterPojo.getDayOfWorkList())
        );
    }

    private void selectInitialValues(RangeSlider rangeSlider) {
        float[] hoursSelected = clinicFilterPojo.getUiState().getHoursSelected();
        if (hoursSelected[0] != 0 && hoursSelected[1] != 0) {
            rangeSlider.setValues(hoursSelected[0], hoursSelected[1]);
            updateSelectedClinicsByHourRange(hoursSelected[0], hoursSelected[1]);
        }else{
            rangeSlider.setValues(rangeSlider.getValueFrom(), rangeSlider.getValueTo());
        }

    }


    @Override
    public void reset() {
        super.reset();
        PojoUtil.setValuesOfFloatTuple(clinicFilterPojo.getUiState().getHoursSelected(),0,0);

    }
}
