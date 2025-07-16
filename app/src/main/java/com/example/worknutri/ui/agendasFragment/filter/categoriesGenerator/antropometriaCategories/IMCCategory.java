package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.antropometriaCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.worknutri.R;
import com.example.worknutri.calcular.ClassificacaoImc;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacientesFilterCategories;
import com.example.worknutri.ui.agendasFragment.filter.pojos.PacienteFilterPojo;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class IMCCategory extends PacientesFilterCategories {

    public IMCCategory(Context context, PacienteFilterPojo pacienteFilterPojo) {
        super(context, pacienteFilterPojo);
    }

    @Override
    public ViewGroup generateCategory(LayoutInflater layoutInflater) {
        ViewGroup viewGroup = agendaFilter.generateCategoryWithChipGroup(layoutInflater, "IMC:");
        ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);
        for (ClassificacaoImc classificacaoImc : ClassificacaoImc.values()) {
            Chip chip = agendaFilter.generateChip(classificacaoImc.toString());
            chip.setBackgroundColor(classificacaoImc.getColor());
            chipGroup.addView(chip);
        }

        return viewGroup;
    }

}
