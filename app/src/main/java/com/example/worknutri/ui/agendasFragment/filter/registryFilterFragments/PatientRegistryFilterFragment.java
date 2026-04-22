package com.example.worknutri.ui.agendasFragment.filter.registryFilterFragments;


import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.PACIENTE_FILTER_BUNDLE;
import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.PACIENTE_FILTER_POJO;

import android.content.Context;
import android.os.Bundle;

import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.PatientFilterCategory;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.anthropometricCategories.AnthropometryCategoryFactory;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.patientCategories.PatientCategoryFactory;
import com.example.worknutri.ui.agendasFragment.filter.pojos.OrderFilterSelectedsBy;
import com.example.worknutri.ui.agendasFragment.filter.pojos.UiState;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PatientFilterPojo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class PatientRegistryFilterFragment extends RegistryFilterFragment {

    private PatientFilterPojo pojo;

    private final List<PatientFilterCategory> categories = new ArrayList<>();


    @Override
    protected void generateFilter(Context context) {
        getPojo();

        insertCategotyInLayout(PatientCategoryFactory.generateGenderCategory(context, pojo));
        insertCategotyInLayout(PatientCategoryFactory.generateYearCategory(context, pojo));
        insertCategotyInLayout(PatientCategoryFactory.generatePacienteInClinicaCategory(context, pojo));
        insertCategotyInLayout(AnthropometryCategoryFactory.createWeightCategory(context, pojo));
        insertCategotyInLayout(AnthropometryCategoryFactory.createHeightCategory(context, pojo));
        insertCategotyInLayout(AnthropometryCategoryFactory.createIMCCategory(context, pojo));

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
                pojo = (PatientFilterPojo) arguments.getSerializable(PACIENTE_FILTER_POJO);
            } else {
                dismiss();
            }
        }
    }


    protected void insertCategotyInLayout(PatientFilterCategory categoryGenerate) {
        super.insertCategotyInLayout(categoryGenerate);
        categories.add(categoryGenerate);
    }

    @Override
    protected void getAllCategories() {
        pojo.getPatientsSelected().clear();
        for (Paciente paciente : pojo.getPatientList()) {
            boolean isSelected = true;
            for (PatientFilterCategory category : categories) {
                if (!category.getSelecteds().contains(paciente)){
                    isSelected = false;
                    break;
                }
            }
            if (isSelected){
                pojo.getPatientsSelected().add(paciente);
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
        pojo.setPatientsSelected( pojo.getPatientsSelected().stream().sorted(Comparator.comparing(Paciente::getNomePaciente)).collect(Collectors.toList()));

    }

    @Override
    protected String getRequestKey() {
        return PACIENTE_FILTER_BUNDLE;
    }

    @Override
    protected void resetAllCategories() {
        for (PatientFilterCategory category : categories) {
            category.reset();
        }
    }


}
