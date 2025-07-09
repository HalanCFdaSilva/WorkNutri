package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import java.util.List;

public interface CategoriesGenerator {

    ViewGroup generateCategory(LayoutInflater layoutInflater);
    <T> List<Paciente> getSelecteds();

}
