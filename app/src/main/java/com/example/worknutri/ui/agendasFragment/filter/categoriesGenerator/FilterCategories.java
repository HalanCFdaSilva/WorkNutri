package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public abstract class FilterCategories {
    protected final CategoriesGeneratorUtil categoriesGeneratorUtil;


    protected FilterCategories(Context context) {
        categoriesGeneratorUtil = new CategoriesGeneratorUtil(context);
    }

    public ViewGroup generateCategory(LayoutInflater layoutInflater) {
        return generateView(layoutInflater);

    }
    protected abstract ViewGroup generateView(LayoutInflater layoutInflater);

    public abstract void reset();





}
