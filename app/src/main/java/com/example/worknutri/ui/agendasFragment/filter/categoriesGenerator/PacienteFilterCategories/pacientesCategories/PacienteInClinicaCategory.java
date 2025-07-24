package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacienteFilterCategories.pacientesCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacienteFilterCategories.PacientesFilterCategory;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ReseterOfCategory;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.List;
import java.util.stream.Collectors;

public class PacienteInClinicaCategory extends PacientesFilterCategory {

    private boolean hasNoFilterActive = true;
    protected PacienteInClinicaCategory(Context context, PacienteFilterPojo pacienteFilterPojo) {
        super(context, pacienteFilterPojo);
    }


    protected ViewGroup generateView(LayoutInflater layoutInflater) {
        ViewGroup viewGroup = categoriesGeneratorUtil.generateCategoryWithChipGroup(layoutInflater,"Clínica:");
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
        Chip chip = categoriesGeneratorUtil.generateChip(clinica.getNome());
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
        if (hasNoFilterActive)
            pacientesInsideFilter.clear();
        hasNoFilterActive = false;

        for (Paciente paciente : pacientesInClinica) {
            if (!pacientesInsideFilter.contains(paciente)) {
                pacientesInsideFilter.add(paciente);
            }
        }
    }
    private void returnToStartIfHasNoFilterActive() {
        if (pacientesInsideFilter.isEmpty()){
            reset();

        }
    }

    @Override
    public void reset() {
        super.reset();
        hasNoFilterActive = true;
        pojo.getState().getClinicaIdSelected().clear();
        ReseterOfCategory.resetChipGroup(viewGroup.findViewById(R.id.filter_category_chipgroup));

    }
}
