package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.antropometriaCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacientesFilterCategories;
import com.example.worknutri.ui.agendasFragment.filter.pojos.PacienteFilterPojo;
import com.example.worknutri.ui.agendasFragment.filter.pojos.PojoUtil;
import com.google.android.material.slider.RangeSlider;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HeightCategory extends PacientesFilterCategories {
    public HeightCategory(Context context, PacienteFilterPojo pacienteFilterPojo) {
        super(context, pacienteFilterPojo);
    }

    public ViewGroup generateCategory(LayoutInflater layoutInflater) {
        ViewGroup viewGroup = agendaFilter.generateCategory(layoutInflater, "Altura:");
        RangeSlider rangeSlider = generateRangeSlider();
        ViewGroup linearLayout = viewGroup.findViewById(com.example.worknutri.R.id.filter_category_intern_layout);
        linearLayout.addView(rangeSlider);
        return viewGroup;
    }



    private RangeSlider generateRangeSlider() {

        float maxValue = getValue(pojo.getAntropometriaList().stream()
                .max(Comparator.comparing(Antropometria::getAltura)));

        float minValue = getValue(pojo.getAntropometriaList().stream()
                .min(Comparator.comparing(Antropometria::getAltura)));


        RangeSlider slider = agendaFilter.generateRangeSlider(minValue, maxValue);

        onClickInSlider(slider);

        setInitialValue(slider, minValue, maxValue);


        return slider;
    }




    private float getValue(Optional<Antropometria> antropometriaOptional) {
        return antropometriaOptional.map(antropometria -> Float.valueOf(antropometria.getPeso()))
                .orElse(0f);
    }

    private void onClickInSlider(RangeSlider slider) {
        slider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {

                float minValue = slider.getValues().get(0);
                float maxValue = slider.getValues().get(1);
                float[] valuesOfWeightSlider = pojo.getState().getTupleOfHeightSlider();
                PojoUtil.setValuesOfFloatTuple(valuesOfWeightSlider, minValue, maxValue);
                selectPacientesInRange(valuesOfWeightSlider);

            }
        });
    }

    private void selectPacientesInRange(float[] values) {
        if (pojo.getPacientes().stream().findAny().isPresent()){
            for (Antropometria antropometria : pojo.getAntropometriaList()) {
                selectpacienteByAntropometria(values, antropometria);
            }
        }

    }

    private void selectpacienteByAntropometria(float[] values, Antropometria antropometria) {
        float heigth = Float.parseFloat(antropometria.getAltura());
        List<Paciente> pacientesFiltred = pojo.getPacientes().stream().filter(paciente -> paciente.getId() == antropometria.getIdPaciente())
                .collect(Collectors.toList());
        if (heigth >= values[0] && heigth <= values[1]) {
            if (!pacientesFiltred.isEmpty() && !pacientesInsideFilter.contains(pacientesFiltred.get(0))) {
                pacientesInsideFilter.add(pacientesFiltred.get(0));
            }
        }else if (!pacientesFiltred.isEmpty()){
            pacientesInsideFilter.remove(pacientesFiltred.get(0));

        }
    }

    private void setInitialValue(RangeSlider slider, float minValue, float maxValue) {
        float[] valuesOfWeightSlider = pojo.getState().getTupleOfHeightSlider();
        if (valuesOfWeightSlider[0] == 0 && valuesOfWeightSlider[1] == 0) {
            slider.setValues(minValue, maxValue);
        } else {
            slider.setValues(valuesOfWeightSlider[0], valuesOfWeightSlider[1]);
            selectPacientesInRange(valuesOfWeightSlider);
        }
    }
}
