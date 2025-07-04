package com.example.worknutri.ui.agendasFragment.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.worknutri.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;

public class AgendaFilter {
    private final Context context;


    public AgendaFilter(Context context) {
        this.context = context;


    }

    public Chip generateChip(String filter) {

        Chip chip = new Chip(context);
        chip.setText(filter);
        ChipDrawable drawable = ChipDrawable.createFromAttributes(context, null, 0,
                com.google.android.material.R.style.Widget_Material3_Chip_Filter);
        chip.setChipDrawable(drawable);
        return chip;
    }

    public ViewGroup GenerateChipsCategory(LayoutInflater inflater,ViewGroup parent) {
        return (ViewGroup) inflater.inflate(R.layout.filter_category, parent, true);
    }
}
