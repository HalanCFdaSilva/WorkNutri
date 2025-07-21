package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacienteFilterCategories.pacientesCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacienteFilterCategories.PacientesFilterCategories;
import com.example.worknutri.ui.agendasFragment.filter.pojos.PacienteFilterPojo;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.List;
import java.util.stream.Collectors;

public class GenderCategory extends PacientesFilterCategories {


    protected GenderCategory(Context context, PacienteFilterPojo pacienteFilterPojo) {
        super(context, pacienteFilterPojo);
    }

    protected ViewGroup generateViewGroup(LayoutInflater layoutInflater){
        ViewGroup viewGroup = agendaFilter.generateCategoryWithChipGroup(layoutInflater,"Gênero:");

        ChipGroup group = viewGroup.findViewById(R.id.filter_category_chipgroup);
        group.setSingleSelection(true);

        Chip chip = generateChip("Masculino");
        group.addView(chip);
        chip = generateChip("Feminino");
        group.addView(chip);


        return viewGroup;
    }



    public Chip generateChip(String genero) {
        Chip chip = agendaFilter.generateChip(genero);

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
    public void resetCategory() {
        resetChipGroup();
        pojo.getState().setGeneroSelected('N');
    }
}
