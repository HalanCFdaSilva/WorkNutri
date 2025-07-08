package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.pacientesCategories;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.pojos.PacienteFilterPojo;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GenderCategoryGenerator extends PacientesCategoriesGenerator{


    protected GenderCategoryGenerator(Context context, PacienteFilterPojo pacienteFilterPojo) {
        super(context, pacienteFilterPojo);
    }

    public ViewGroup generateCategory(LayoutInflater layoutInflater){
        ViewGroup viewGroup = agendaFilter.GenerateCategoryWithChipGroup(layoutInflater);

        TextView text = viewGroup.findViewById(R.id.filter_category_title);
        text.setText("Gênero:");

        ChipGroup group = viewGroup.findViewById(R.id.filter_category_chipgroup);
        group.setSingleSelection(true);

        Chip chip = generateChip("Masculino");
        group.addView(chip);
        chip = generateChip("Feminino");
        group.addView(chip);

        return viewGroup;
    }

    @Override
    public List<Object> getSelecteds() {
        return Collections.emptyList();
    }

    public Chip generateChip(String genero) {
        Chip chip = agendaFilter.generateChip(genero);
        chip.setChecked(pojo.getState().getGeneroSelected().equals(genero.substring(0,1)));
        onClickSave(chip, genero.charAt(0));
        return chip;

    }
    public void onClickSave(Chip chip,char genero) {

        chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pojo.getState().setInOrder(false);

            if (isChecked) {
                pojo.getState().setGeneroSelected(String.valueOf(genero));
                pacientesInsideFilter.removeIf(paciente ->paciente.getGenero() != genero );


            } else {
                pojo.getState().setGeneroSelected("NONE");
                List<Paciente> pacientesFiltred = pojo.getPacientes().stream()
                        .filter(paciente -> paciente.getGenero() != genero).collect(Collectors.toList());
                pacientesInsideFilter.addAll(pacientesFiltred);
            }
            Log.d("android runtime", "onSelectedChip: " + pacientesInsideFilter);
        });
    }
}
