package com.example.worknutri.ui.agendasFragment.filter.pacientesFilter;


import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.PACIENTE_FILTER_BUNDLE;
import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.PACIENTE_FILTER_POJO;

import android.content.Context;
import android.os.Bundle;

import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.FilterFragment;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacienteFilterCategories.PacientesFilterCategory;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacienteFilterCategories.antropometriaCategories.AntropometriaCategoryFactory;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PacienteFilterCategories.pacientesCategories.PacientesCategoryFactory;
import com.example.worknutri.ui.agendasFragment.filter.pojos.OrderFilterSelectedsBy;
import com.example.worknutri.ui.agendasFragment.filter.pojos.UiState;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class PacienteFilterFragment extends FilterFragment {

    private PacienteFilterPojo pojo;

    private final List<PacientesFilterCategory> categories = new ArrayList<>();


    @Override
    protected void generateFilter(Context context) {
        getPojo();

        insertCategotyInLayout(PacientesCategoryFactory.generateGenderCategory(context, pojo));
        insertCategotyInLayout(PacientesCategoryFactory.generateYearCategory(context, pojo));
        insertCategotyInLayout(PacientesCategoryFactory.generatePacienteInClinicaCategory(context, pojo));
        insertCategotyInLayout(AntropometriaCategoryFactory.createWeightCategory(context, pojo));
        insertCategotyInLayout(AntropometriaCategoryFactory.createHeightCategory(context, pojo));
        insertCategotyInLayout(AntropometriaCategoryFactory.createIMCCategory(context, pojo));

    }

    @Override
    protected UiState getUiState() {
        return pojo.getState();
    }

    @Override
    protected List<OrderFilterSelectedsBy> getListOfSortChips() {
        return List.of(
                OrderFilterSelectedsBy.NAME_ASC,
                OrderFilterSelectedsBy.NAME_DESC,
                OrderFilterSelectedsBy.IMC_CATEGORY,
                OrderFilterSelectedsBy.HEIGHT,
                OrderFilterSelectedsBy.WEIGHT,
                OrderFilterSelectedsBy.AGE
        );
    }

    private void getPojo() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (arguments.containsKey(PACIENTE_FILTER_POJO)) {
                pojo = (PacienteFilterPojo) arguments.getSerializable(PACIENTE_FILTER_POJO);
            } else {
                dismiss();
            }
        }
    }


    protected void insertCategotyInLayout(PacientesFilterCategory categoryGenerate) {
        super.insertCategotyInLayout(categoryGenerate);
        categories.add(categoryGenerate);
    }

    @Override
    protected void getAllCategories() {
        pojo.getPacienteSelected().clear();
        for (Paciente paciente : pojo.getPacientes()) {
            boolean isSelected = true;
            for (PacientesFilterCategory category : categories) {
                if (!category.getSelecteds().contains(paciente)){
                    isSelected = false;
                    break;
                }
            }
            if (isSelected){
                pojo.getPacienteSelected().add(paciente);
            }
        }

    }


    @Override
    protected Bundle generateBundle() {
        Bundle bundle = new Bundle();
        filterPacientesSelected();
        bundle.putSerializable(PACIENTE_FILTER_POJO, pojo);
        return bundle;
    }

    private void filterPacientesSelected(){
        pojo.setPacienteSelected( pojo.getPacienteSelected().stream().sorted(Comparator.comparing(Paciente::getNomePaciente)).collect(Collectors.toList()));

    }

    @Override
    protected String getRequestKey() {
        return PACIENTE_FILTER_BUNDLE;
    }

    @Override
    protected void resetAllCategories() {
        for (PacientesFilterCategory category : categories) {
            category.reset();
        }
    }


}
