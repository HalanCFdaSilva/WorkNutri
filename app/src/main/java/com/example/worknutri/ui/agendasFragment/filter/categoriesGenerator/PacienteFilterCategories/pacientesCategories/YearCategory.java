package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacienteFilterCategories.pacientesCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacienteFilterCategories.PacientesFilterCategory;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ReseterOfCategory;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;
import com.example.worknutri.ui.agendasFragment.filter.pojos.PojoUtil;
import com.google.android.material.slider.RangeSlider;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class YearCategory extends PacientesFilterCategory {



    public YearCategory(Context context, PacienteFilterPojo pacienteFilterPojo) {
        super(context, pacienteFilterPojo);
    }

    protected ViewGroup generateView(LayoutInflater layoutInflater) {

        ViewGroup viewGroup = categoriesGeneratorUtil.generateCategory(layoutInflater, "Idade:");

        RangeSlider rangeSlider = generateRangeSlider();
        ViewGroup linearLayout = viewGroup.findViewById(R.id.filter_category_intern_layout);
        linearLayout.addView(rangeSlider);

        return viewGroup;
    }



    private RangeSlider generateRangeSlider() {
        Optional<Paciente> max = pojo.getPacientes().stream().max(Comparator.comparing(Paciente::getIdade));
        int maxValue = max.map(Paciente::getIdade).orElse(0);

        Optional <Paciente> min = pojo.getPacientes().stream().min(Comparator.comparing(Paciente::getIdade));
        int minValue = min.map(Paciente::getIdade).orElse(0);
        RangeSlider slider = categoriesGeneratorUtil.generateRangeSlider(minValue,maxValue);
        slider.setStepSize(1);

        slider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                List<Float> values = slider.getValues();
                selectPacienteInsideRange(values.get(0), values.get(1));
            }
        });

        setValuesifHasOldValues(slider);

        return slider;
    }

    private void selectPacienteInsideRange(float minValue, float maxValue) {
        for(Paciente paciente : pojo.getPacientes()) {
            if (paciente.getIdade() < minValue || paciente.getIdade() > maxValue) {
                pacientesInsideFilter.remove(paciente);
            } else if (!pacientesInsideFilter.contains(paciente)) {
                pacientesInsideFilter.add(paciente);
            }
        }
        PojoUtil.setValuesOfFloatTuple(pojo.getState().getTupleOfYearSlider(), minValue, maxValue);
    }

    private void setValuesifHasOldValues(RangeSlider slider) {
        float[] valuesSelected = pojo.getState().getTupleOfYearSlider();
        if (valuesSelected[0] != 0 && valuesSelected[1] != 0) {
            slider.setValues(valuesSelected[0], valuesSelected[1]);
            selectPacienteInsideRange(valuesSelected[0], valuesSelected[1]);
        }
    }


    @Override
    public void reset() {
        super.reset();
        ReseterOfCategory.resetSlider(viewGroup.findViewById(R.id.filter_category_rangeslider)
                ,pojo.getState().getTupleOfYearSlider());

    }
}
