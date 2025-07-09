package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.pacientesCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.pojos.PacienteFilterPojo;
import com.google.android.material.slider.RangeSlider;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class YearCategoryGenerator extends PacientesCategoriesGenerator{

    private final Context context;

    public YearCategoryGenerator(Context context, PacienteFilterPojo pacienteFilterPojo) {
        super(context, pacienteFilterPojo);
        this.context = context;

    }

    public ViewGroup generateCategory(LayoutInflater layoutInflater) {

        ViewGroup viewGroup = agendaFilter.generateCategory(layoutInflater, "Idade:");

        RangeSlider rangeSlider = generateRangeSlider();
        ViewGroup linearLayout = viewGroup.findViewById(R.id.filter_category_intern_layout);
        linearLayout.addView(rangeSlider);

        return viewGroup;
    }



    private RangeSlider generateRangeSlider() {
        RangeSlider slider = new RangeSlider(context);
        slider.setTickVisible(false);

        slider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                List<Float> values = slider.getValues();
                extracted(values.get(0), values.get(1));
            }
        });

        setValuesOfRangeSlider(slider);

        return slider;
    }

    private void extracted(float minValue, float maxValue) {
        for(Paciente paciente : pojo.getPacientes()) {
            if (paciente.getIdade() < minValue || paciente.getIdade() > maxValue) {
                pacientesInsideFilter.remove(paciente);
            } else if (!pacientesInsideFilter.contains(paciente)) {
                pacientesInsideFilter.add(paciente);
            }
        }
        pojo.getState().setValuesOfRangeSlider(minValue, maxValue);
    }

    private void setValuesOfRangeSlider(RangeSlider slider) {
        Optional<Paciente> max = pacientesInsideFilter.stream().max(Comparator.comparing(Paciente::getIdade));
        int maxValue = max.map(Paciente::getIdade).orElse(0);

        Optional <Paciente> min = pacientesInsideFilter.stream().min(Comparator.comparing(Paciente::getIdade));
        int minValue = min.map(Paciente::getIdade).orElse(0);

        slider.setValueFrom(minValue);
        slider.setValueTo(maxValue);
        slider.setStepSize(1);

        float[] valuesSelected = pojo.getState().getValuesOfRangeSlider();
        slider.setValues(valuesSelected[0], valuesSelected[1]);
        extracted(valuesSelected[0], valuesSelected[1]);
    }


}
