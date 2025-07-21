package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacienteFilterCategories.antropometriaCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.worknutri.R;
import com.example.worknutri.calcular.ClassificacaoImc;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacienteFilterCategories.PacientesFilterCategories;
import com.example.worknutri.ui.agendasFragment.filter.pojos.PacienteFilterPojo;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class IMCCategory extends PacientesFilterCategories {

    boolean firstTime = true;
    public IMCCategory(Context context, PacienteFilterPojo pacienteFilterPojo) {
        super(context, pacienteFilterPojo);
    }

    @Override
    public ViewGroup generateViewGroup(LayoutInflater layoutInflater) {

        ViewGroup viewGroup = agendaFilter.generateCategoryWithChipGroup(layoutInflater, "IMC:");
        ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);
        List<ClassificacaoImc> classificacaoImcs = pojo.getState().getClassificacaoImcs();
        for (ClassificacaoImc classificacaoImc : ClassificacaoImc.values()) {
            Chip chip = agendaFilter.generateChip(classificacaoImc.toString());
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
            List<ClassificacaoImc> imcCategories = pojo.getState().getClassificacaoImcs();
            if (firstTime){
                pacientesInsideFilter.clear();
                firstTime = false;
            }
            List<Paciente> pacientesInsideCategory = getStreamOfPacientesInClassificacaoImc(classificacaoImc);
            List<ClassificacaoImc> classificacaoImcs = pojo.getState().getClassificacaoImcs();

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
        Stream<Antropometria> antropometriaStream = pojo.getAntropometriaList().stream();
        List<Antropometria> antropometriaSelected = antropometriaStream
                .filter(antropometria -> ClassificacaoImc.
                        tipoImc(Double.parseDouble(antropometria.getImc())) == classificacaoImc)
                .collect(Collectors.toList());
        Stream<Paciente> pacienteStream = pojo.getPacientes().stream();
        return pacienteStream.filter(paciente -> antropometriaSelected.stream().anyMatch(antropometria -> antropometria.getIdPaciente() == paciente.getId()))
                .collect(Collectors.toList());
    }

    private void returnToStartIfHasNoFilterActive() {
        if (pacientesInsideFilter.isEmpty()){
            resetCategory();

        }
    }

    @Override
    public void resetCategory() {

        firstTime = true;
        pojo.getState().getClassificacaoImcs().clear();
        resetChipGroup();
    }
}
