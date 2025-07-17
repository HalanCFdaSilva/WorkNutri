package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.pacientesCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacientesFilterCategories;
import com.example.worknutri.ui.agendasFragment.filter.pojos.PacienteFilterPojo;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PacienteInClinicaCategory extends PacientesFilterCategories {

    private boolean hasNoFilterActive = true;
    protected PacienteInClinicaCategory(Context context, PacienteFilterPojo pacienteFilterPojo) {
        super(context, pacienteFilterPojo);
    }

    @Override
    public ViewGroup generateCategory(LayoutInflater layoutInflater) {
        ViewGroup viewGroup = agendaFilter.generateCategoryWithChipGroup(layoutInflater,"Clínica:");
        generateAllChips(viewGroup.findViewById(R.id.filter_category_chipgroup));

        return viewGroup;
    }

    private void generateAllChips(ChipGroup chipGroup) {

        for (Clinica clinica : pojo.getClinicas()) {
            if (pojo.getPacientes().stream().anyMatch(paciente -> paciente.getClinicaId() == clinica.getId())) {
                Chip chip = generateChip(clinica);
                chipGroup.addView(chip);
            }
        }
    }

    private Chip generateChip(Clinica clinica) {
        Chip chip = agendaFilter.generateChip(clinica.getNome());
        onClickInChip(chip,clinica);
        chip.setChecked(pojo.getState().getClinicaIdSelected().contains(clinica.getId()));
        return chip;
    }

    private void onClickInChip(Chip chip, Clinica clinica) {
        chip.setOnCheckedChangeListener((buttonView, isChecked) -> {

            List<Paciente> pacientesInClinica = pojo.getPacientes().stream()
                    .filter(paciente -> paciente.getClinicaId() == clinica.getId())
                    .collect(Collectors.toList());

            List<Long> clinicaIdSelected = pojo.getState().getClinicaIdSelected();

            if (isChecked){

                insertPacientesInFilter(pacientesInClinica);
                if (!clinicaIdSelected.contains(clinica.getId())){
                    clinicaIdSelected.add(clinica.getId());
                }
            }else {

                pacientesInsideFilter.removeAll(pacientesInClinica);
                clinicaIdSelected.remove(clinica.getId());

                returnToStartIfHasNoFilterActive();
            }
        });
    }

    private void insertPacientesInFilter(List<Paciente> pacientesInClinica) {
        if (hasNoFilterActive) {
            hasNoFilterActive = false;
            pacientesInsideFilter = new ArrayList<>(pacientesInClinica);
        } else {
            pacientesInsideFilter.addAll(pacientesInClinica);
        }
    }
    private void returnToStartIfHasNoFilterActive() {
        if (pacientesInsideFilter.isEmpty()){
            pacientesInsideFilter.addAll(pojo.getPacientes());
            hasNoFilterActive = true;
        }
    }


}
