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
            List<Paciente> pacientesInClinica = pojo.getPacientes().stream()
                    .filter(paciente -> paciente.getClinicaId() == clinica.getId())
                    .collect(Collectors.toList());

            if (!pacientesInClinica.isEmpty()) {
                Chip chip = generateChip(clinica, pacientesInClinica);
                chipGroup.addView(chip);
            }
        }
    }

    private Chip generateChip(Clinica clinica, List<Paciente> pacientesInClinica) {
        Chip chip = agendaFilter.generateChip(clinica.getNome());
        chip.setId((int) clinica.getId());

        onClickInChip(chip,pacientesInClinica);
        chip.setChecked(pojo.getState().getClinicaIdSelected().contains(Integer.valueOf(chip.getId())));
        return chip;
    }

    private void onClickInChip(Chip chip, List<Paciente> pacientesInClinica) {
        chip.setOnCheckedChangeListener((buttonView, isChecked) -> {

            List<Integer> clinicaIdSelected = pojo.getState().getClinicaIdSelected();
            Integer chipId = Integer.valueOf(chip.getId());

            if (isChecked){

                insertPacientesInFilter(pacientesInClinica);
                if (!clinicaIdSelected.contains(chipId)){
                    clinicaIdSelected.add(chipId);
                }
            }else {

                pacientesInsideFilter.removeAll(pacientesInClinica);
                clinicaIdSelected.remove(chipId);

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
