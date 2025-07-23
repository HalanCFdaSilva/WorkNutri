package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacienteFilterCategories;

import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ReseterOfCategory;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.RangeSlider;

import java.util.List;

public class ReseterOfPacientesFilterCategories extends ReseterOfCategory {
    private final PacienteFilterPojo pacienteFilterPojo;
    private final List<Paciente> pacientesInsideFilter;

    public ReseterOfPacientesFilterCategories(PacienteFilterPojo pacienteFilterPojo, List<Paciente> pacientesInsideFilter) {
        this.pacienteFilterPojo = pacienteFilterPojo;
        this.pacientesInsideFilter = pacientesInsideFilter;
    }
    private void resetListOfpacientesSelected() {
        pacientesInsideFilter.clear();
        pacientesInsideFilter.addAll(pacienteFilterPojo.getPacientes());
    }

    @Override
    public void resetChipGroup(ChipGroup chipGroup) {
        super.resetChipGroup(chipGroup);
        resetListOfpacientesSelected();
    }

    @Override
    public void resetSlider(RangeSlider rangeSlider, float[] valuesInState) {
        super.resetSlider(rangeSlider, valuesInState);
        resetListOfpacientesSelected();
    }}
