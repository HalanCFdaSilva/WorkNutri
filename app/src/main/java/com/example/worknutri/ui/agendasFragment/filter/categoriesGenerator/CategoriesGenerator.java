package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;

import java.util.List;

public interface CategoriesGenerator {

    ViewGroup generateCategory(LayoutInflater layoutInflater);
    <T>List<T> getSelecteds();

}
