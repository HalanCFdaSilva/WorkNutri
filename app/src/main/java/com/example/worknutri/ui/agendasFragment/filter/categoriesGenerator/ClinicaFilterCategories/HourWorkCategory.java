package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicaFilterCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.filter.pojos.PojoUtil;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;
import com.google.android.material.slider.RangeSlider;
import java.util.List;

public class HourWorkCategory extends ClinicaFilterCategory {
    protected HourWorkCategory(Context context, ClinicaFilterPojo clinicaFilterPojo) {
        super(context, clinicaFilterPojo);
    }

    @Override
    protected ViewGroup generateView(LayoutInflater layoutInflater) {
        ViewGroup viewGroup = categoriesGeneratorUtil.generateCategory(layoutInflater, "Horário de Trabalho:");
        RangeSlider rangeSlider = generateRangeSlider();
        rangeSlider.setStepSize(1);
        onSliderRangeSlider(rangeSlider);
        selectInitialValues(rangeSlider);
        ViewGroup linearLayout = viewGroup.findViewById(R.id.filter_category_intern_layout);
        linearLayout.addView(rangeSlider);
        return viewGroup;
    }



    private RangeSlider generateRangeSlider() {

        RangeSlider rangeSlider = categoriesGeneratorUtil.generateRangeSlider(0, 24*60);
        rangeSlider.setStepSize(15f);
        rangeSlider.setLabelFormatter(f-> String.format("%02d:%02d", (int) f / 60, (int) f % 60));
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

                PojoUtil.setValuesOfFloatTuple(clinicaFilterPojo.getUiState().getHoursSelected(), values.get(0), values.get(1));
                clinicasSelecteds.clear();
                selectClinicaInsideRange(values.get(0), values.get(1));
            }


        });
    }

    private void selectClinicaInsideRange(Float min, Float max) {

        List<Clinica> clinicas = clinicaFilterPojo.getClinicas();
        clinicaFilterPojo.getDayOfWorkList().forEach(dayOfWork -> {
            float horaInicio = getTimeInMinutes(dayOfWork.getHoraInicio());
            float horaFim = getTimeInMinutes(dayOfWork.getHoraFim());
            if (horaInicio >= min && horaFim <= max) {
                clinicas.stream().filter(clinica -> clinica.getId() == dayOfWork.getIdClinica())
                        .findFirst()
                        .ifPresent(clinica -> {
                            if (!clinicasSelecteds.contains(clinica))
                                clinicasSelecteds.add(clinica);
                        });

            }
        });

    }

    private float getTimeInMinutes(String horaInicio) {
        float hoursInMinute = Float.parseFloat(horaInicio.substring(0, 2))*60;
        float minutes = Float.parseFloat(horaInicio.substring(3, 5));
        return hoursInMinute + minutes;
    }

    private void selectInitialValues(RangeSlider rangeSlider) {
        float[] hoursSelected = clinicaFilterPojo.getUiState().getHoursSelected();
        if (hoursSelected[0] != 0 && hoursSelected[1] != 0) {
            rangeSlider.setValues(hoursSelected[0], hoursSelected[1]);
            selectClinicaInsideRange(hoursSelected[0], hoursSelected[1]);
        }else{
            rangeSlider.setValues(rangeSlider.getValueFrom(), rangeSlider.getValueTo());
        }

    }


    @Override
    public void reset() {
        super.reset();
        PojoUtil.setValuesOfFloatTuple(clinicaFilterPojo.getUiState().getHoursSelected(),0,0);

    }
}
