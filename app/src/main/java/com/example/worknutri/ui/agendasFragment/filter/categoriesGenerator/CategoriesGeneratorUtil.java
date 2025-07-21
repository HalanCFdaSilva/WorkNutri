package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.example.worknutri.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.RangeSlider;


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

    public ViewGroup generateCategoryWithChipGroup(LayoutInflater inflater,
                                                   String title) {
        ViewGroup viewGroup =  (ViewGroup) inflater.inflate(R.layout.filter_category, null);

        TextView text = viewGroup.findViewById(R.id.filter_category_title);
        text.setText(title);

        HorizontalScrollView scrollView = new HorizontalScrollView(context);
        ChipGroup chipGroup = new ChipGroup(context);
        chipGroup.setSelectionRequired(false);
        chipGroup.setId(R.id.filter_category_chipgroup);
        scrollView.addView(chipGroup);
        ViewGroup linearlayout = viewGroup.findViewById(R.id.filter_category_intern_layout);
        linearlayout.addView(scrollView);

        return viewGroup;
    }
    public ViewGroup generateCategory(LayoutInflater inflater,
                                      String title){
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.filter_category, null);
        TextView text = viewGroup.findViewById(R.id.filter_category_title);
        text.setText(title);
        return viewGroup;
    }


    public RangeSlider generateRangeSlider(float min, float max) {
        RangeSlider slider = new RangeSlider(context);
        slider.setId(R.id.filter_category_rangeslider);
        slider.setTickVisible(false);
        slider.setValueFrom(min);
        slider.setValueTo(max);
        return slider;
    }
}
