package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.anthropometricCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.worknutri.R;
import com.example.worknutri.calcular.ClassificacaoImc;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.PatientFilterCategory;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ReseterOfCategory;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PatientFilterPojo;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class IMCCategory extends PatientFilterCategory {

    boolean firstTime = true;
    public IMCCategory(Context context, PatientFilterPojo patientFilterPojo) {
        super(context, patientFilterPojo);
    }

    @Override
    public ViewGroup generateView(LayoutInflater layoutInflater) {

        ViewGroup viewGroup = categoriesGeneratorUtil.generateCategoryWithChipGroup(layoutInflater, "IMC:");
        ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);
        List<ClassificacaoImc> classificacaoImcs = pojo.getState().getBmiClassificationsSelected();
        for (ClassificacaoImc classificacaoImc : ClassificacaoImc.values()) {
            Chip chip = categoriesGeneratorUtil.generateChip(classificacaoImc.toString());
            chip.setBackgroundColor(classificacaoImc.getColor());
            onSelectChip(chip, classificacaoImc);
            chipGroup.addView(chip);
            if (classificacaoImcs.contains(classificacaoImc)){
                chip.setChecked(true);
            }
        }


        return viewGroup;
    }

    private void onSelectChip(Chip chip,ClassificacaoImc classificacaoImc) {
        chip.setOnCheckedChangeListener((v, isChecked) -> {
            if (firstTime){
                pacientesInsideFilter.clear();
                firstTime = false;
            }
            List<Paciente> pacientesInsideCategory = getStreamOfPacientesInClassificacaoImc(classificacaoImc);
            List<ClassificacaoImc> classificacaoImcs = pojo.getState().getBmiClassificationsSelected();

            if (isChecked) {
                if (!classificacaoImcs.contains(classificacaoImc)) {
                    classificacaoImcs.add(classificacaoImc);
                }
                for (Paciente paciente : pacientesInsideCategory) {
                    if (!pacientesInsideFilter.contains(paciente)){
                        pacientesInsideFilter.add(paciente);
                    }
                }

            } else {
                pacientesInsideFilter.removeAll(pacientesInsideCategory);
                classificacaoImcs.remove(classificacaoImc);
                returnToStartIfHasNoFilterActive();
            }
        });
    }

    private List<Paciente> getStreamOfPacientesInClassificacaoImc(ClassificacaoImc classificacaoImc) {
        Stream<Antropometria> antropometriaStream = pojo.getAnthropometryList().stream();
        List<Antropometria> antropometriaSelected = antropometriaStream
                .filter(antropometria -> ClassificacaoImc.
                        tipoImc(Double.parseDouble(antropometria.getImc())) == classificacaoImc)
                .collect(Collectors.toList());
        Stream<Paciente> pacienteStream = pojo.getPatientList().stream();
        return pacienteStream.filter(paciente -> antropometriaSelected.stream().anyMatch(antropometria -> antropometria.getIdPaciente() == paciente.getId()))
                .collect(Collectors.toList());
    }

    private void returnToStartIfHasNoFilterActive() {
        if (pacientesInsideFilter.isEmpty()){
            reset();

        }
    }

    @Override
    protected void resetLayout() {
        firstTime = true;
        pojo.getState().getBmiClassificationsSelected().clear();
        ReseterOfCategory.resetChipGroup(viewGroup.findViewById(R.id.filter_category_chipgroup));
    }
}
