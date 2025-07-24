package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicaFilterCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.ui.agendasFragment.filter.pojos.PojoUtil;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;
import com.google.android.material.slider.RangeSlider;

import java.util.Comparator;
import java.util.List;

public class HourWorkCategory extends ClinicaFilterCategory {
    protected HourWorkCategory(Context context, ClinicaFilterPojo clinicaFilterPojo) {
        super(context, clinicaFilterPojo);
    }

    @Override
    protected ViewGroup generateView(LayoutInflater layoutInflater) {
        ViewGroup viewGroup = categoriesGeneratorUtil.generateCategory(layoutInflater, "Horário de Trabalho:");
        RangeSlider rangeSlider = generateRangeSlider();
        rangeSlider.setMinSeparation(1);
        onSliderRangeSlider(rangeSlider);
        selectInitialValues(rangeSlider);
        return viewGroup;
    }



    private RangeSlider generateRangeSlider() {
        List<DayOfWork> dayOfWorkList = clinicaFilterPojo.getDayOfWorkList();
        float min = dayOfWorkList.stream()
                .min(Comparator.comparing(DayOfWork::getHoraInicio))
                .map(dayOfWork -> Float.valueOf(dayOfWork.getHoraInicio().substring(0,2))).orElse(0f);

        float max = dayOfWorkList.stream()
                .min(Comparator.comparing(DayOfWork::getHoraFim))
                .map(dayOfWork -> Float.valueOf(dayOfWork.getHoraFim().substring(0,2))).orElse(0f);

        return categoriesGeneratorUtil.generateRangeSlider(min, max);
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
            float horaInicio = Float.parseFloat(dayOfWork.getHoraInicio().substring(0,2));
            float horaFim = Float.parseFloat(dayOfWork.getHoraFim().substring(0,2));
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

    private void selectInitialValues(RangeSlider rangeSlider) {
        float[] hoursSelected = clinicaFilterPojo.getUiState().getHoursSelected();
        if (hoursSelected[0] != 0 && hoursSelected[1] != 0) {
            rangeSlider.setValues(hoursSelected[0], hoursSelected[1]);
            selectClinicaInsideRange(hoursSelected[0], hoursSelected[1]);
        }

    }


    @Override
    public void reset() {
        clinicasSelecteds.clear();

        PojoUtil.setValuesOfFloatTuple(clinicaFilterPojo.getUiState().getHoursSelected(),0,0);

    }
}
