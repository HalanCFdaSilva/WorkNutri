package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.pacientesCategories;

import android.content.Context;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.pojos.PacienteFilterPojo;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class YearCategoryGenerator extends PacientesCategoriesGenerator{

    private Context context;

    public YearCategoryGenerator(Context context, PacienteFilterPojo pacienteFilterPojo) {
        super(context, pacienteFilterPojo);
        this.context = context;

    }

    public ViewGroup generateCategory(LayoutInflater layoutInflater) {

        ViewGroup viewGroup = agendaFilter.generateCategory(layoutInflater);
        ((TextView)viewGroup.findViewById(R.id.filter_category_title)).setText("Idade:");
        RangeSlider rangeSlider = generateRangeSlider();
        ViewGroup linearLayout = viewGroup.findViewById(R.id.filter_category_intern_layout);
        linearLayout.addView(rangeSlider);

        return viewGroup;
    }



    private RangeSlider generateRangeSlider() {
        RangeSlider slider = new RangeSlider(context);
        setValuesOfRangeSlider(slider);
        slider.setTickVisible(false);

        slider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                List<Float> values = slider.getValues();
                for(Paciente paciente : pojo.getPacientes()) {
                    if (paciente.getIdade() < values.get(0) || paciente.getIdade() > values.get(1)) {
                        pacientesInsideFilter.remove(paciente);
                    } else if (!pacientesInsideFilter.contains(paciente)) {
                        pacientesInsideFilter.add(paciente);
                    }
                }
                Log.d("android runtime", "onStopTrackingTouch: " + pacientesInsideFilter);
            }
        });



        return slider;
    }

    private void setValuesOfRangeSlider(RangeSlider slider) {
        Optional<Paciente> max = pojo.getPacienteSelected().stream().max(Comparator.comparing(Paciente::getIdade));
        int maxValue = max.map(Paciente::getIdade).orElse(0);

        Optional <Paciente> min = pojo.getPacienteSelected().stream().min(Comparator.comparing(Paciente::getIdade));
        int minValue = min.map(Paciente::getIdade).orElse(0);

        slider.setValueFrom(minValue);
        slider.setValueTo(maxValue);
        slider.setStepSize(1);
        slider.setValues((float) minValue, (float) maxValue);
    }
    @Override
    public List<Paciente> getSelecteds() {
        return pacientesInsideFilter;
    }

}
