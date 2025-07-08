package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.worknutri.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;


public class CategoriesGeneratorUtil {
    private final Context context;


    public CategoriesGeneratorUtil(Context context) {
        this.context = context;


    }

    public Chip generateChip(String filter) {

        Chip chip = new Chip(context);
        chip.setText(filter);
        ChipDrawable drawable = ChipDrawable.createFromAttributes(context, null, 0,
                R.style.Theme_themeFilter_Chip);
        chip.setChipDrawable(drawable);

        return chip;
    }

    public ViewGroup GenerateCategoryWithChipGroup(LayoutInflater inflater) {
        ViewGroup viewGroup =  (ViewGroup) inflater.inflate(R.layout.filter_category, null);
        ChipGroup chipGroup = new ChipGroup(context);
        chipGroup.setSelectionRequired(false);
        chipGroup.setId(R.id.filter_category_chipgroup);
        ViewGroup linearlayout = viewGroup.findViewById(R.id.filter_category_intern_layout);
        linearlayout.addView(chipGroup);
        return viewGroup;
    }
    public ViewGroup generateCategory(LayoutInflater inflater){
        return (ViewGroup) inflater.inflate(R.layout.filter_category, null);
    }
}
