package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public abstract class FilterCategories {
    protected final CategoriesGeneratorUtil categoriesGeneratorUtil;
    protected ViewGroup viewGroup;

    protected FilterCategories(Context context) {
        categoriesGeneratorUtil = new CategoriesGeneratorUtil(context);
    }

    public ViewGroup generateCategory(LayoutInflater layoutInflater) {
        this.viewGroup = generateViewGroup(layoutInflater);
        return viewGroup;
    }
    protected abstract ViewGroup generateViewGroup(LayoutInflater layoutInflater);

    public abstract void resetCategory();
    protected abstract void resetChipGroup();

    protected abstract void resetSlider(float[] values);



}
