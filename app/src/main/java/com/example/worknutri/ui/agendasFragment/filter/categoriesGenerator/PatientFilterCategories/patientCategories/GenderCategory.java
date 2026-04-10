package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.patientCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.PatientFilterCategory;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ReseterOfCategory;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.List;
import java.util.stream.Collectors;

public class GenderCategory extends PatientFilterCategory {


    protected GenderCategory(Context context, PacienteFilterPojo pacienteFilterPojo) {
        super(context, pacienteFilterPojo);
    }

    protected ViewGroup generateView(LayoutInflater layoutInflater){
        ViewGroup viewGroup = categoriesGeneratorUtil.generateCategoryWithChipGroup(layoutInflater,"Gênero:");

        ChipGroup group = viewGroup.findViewById(R.id.filter_category_chipgroup);
        group.setSingleSelection(true);

        Chip chip = generateChip("Masculino");
        group.addView(chip);
        chip = generateChip("Feminino");
        group.addView(chip);


        return viewGroup;
    }



    public Chip generateChip(String genero) {
        Chip chip = categoriesGeneratorUtil.generateChip(genero);

        chip.setChecked(pojo.getState().getGeneroSelected() == genero.charAt(0));
        if (chip.isChecked())
            setPacientesInsideFilter(pojo.getState().getGeneroSelected());

        onClickSave(chip, genero.charAt(0));
        return chip;

    }
    public void onClickSave(Chip chip,char genero) {

        chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pojo.getState().setInOrder(false);

            if (isChecked) {
               setPacientesInsideFilter(genero);


            } else {
                pojo.getState().setGeneroSelected('N');
                List<Paciente> pacientesFiltred = pojo.getPacientes().stream()
                        .filter(paciente -> paciente.getGenero() != genero).collect(Collectors.toList());
                pacientesInsideFilter.addAll(pacientesFiltred);
            }
        });
    }

    private void setPacientesInsideFilter(char genero) {
        pojo.getState().setGeneroSelected(genero);
        pacientesInsideFilter.removeIf(paciente ->paciente.getGenero() != genero );
    }

    @Override
    protected void resetLayout() {
        ReseterOfCategory.resetChipGroup(viewGroup.findViewById(R.id.filter_category_chipgroup));
        pojo.getState().setGeneroSelected('N');
    }
}
