package com.example.worknutri.ui.agendasFragment.filter.clinicaFilter;


import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.CLINICA_FILTER_BUNDLE;
import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.CLINICA_FILTER_POJO;

import android.content.Context;
import android.os.Bundle;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.filter.RegistryFilterFragment;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicFilterCategories.ClinicFilterCategory;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicFilterCategories.ClinicFilterCategoryFactory;
import com.example.worknutri.ui.agendasFragment.filter.pojos.OrderFilterSelectedsBy;
import com.example.worknutri.ui.agendasFragment.filter.pojos.UiState;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;

import java.util.ArrayList;
import java.util.List;

public class ClinicaRegistryFilterFragment extends RegistryFilterFragment {

    private ClinicaFilterPojo clinicaFilterPojo;
    private final List<ClinicFilterCategory> categories = new ArrayList<>();

    @Override
    protected void generateFilter(Context context) {
         getPojo();
        insertCategotyInLayout(ClinicFilterCategoryFactory.generateDayInClinicaCategory(context, clinicaFilterPojo));
        insertCategotyInLayout(ClinicFilterCategoryFactory.generateHourWorkCategory(context, clinicaFilterPojo));
    }

    @Override
    protected UiState getUiState() {
        return clinicaFilterPojo.getUiState();
    }

    @Override
    protected List<OrderFilterSelectedsBy> getListOfSortChips() {
        return List.of(
                OrderFilterSelectedsBy.NAME_ASC,
                OrderFilterSelectedsBy.NAME_DESC,
                OrderFilterSelectedsBy.DISTRICT,
                OrderFilterSelectedsBy.CITY,
                OrderFilterSelectedsBy.DAY_OF_WEEK,
                OrderFilterSelectedsBy.NUMBER_OF_PATIENTS
        );
    }


    protected void insertCategotyInLayout(ClinicFilterCategory categoryGenerate) {
        super.insertCategotyInLayout(categoryGenerate);
        categories.add(categoryGenerate);
    }

    private void getPojo() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (arguments.containsKey(CLINICA_FILTER_POJO)) {
                clinicaFilterPojo = (ClinicaFilterPojo) arguments.getSerializable(CLINICA_FILTER_POJO);
            } else {
                dismiss();
            }
        }
    }

    @Override
    protected Bundle generateBundle() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CLINICA_FILTER_POJO, clinicaFilterPojo);
        return bundle;

    }

    @Override
    protected String getRequestKey() {
        return CLINICA_FILTER_BUNDLE;
    }


    @Override
    protected void getAllCategories() {
        clinicaFilterPojo.getClinicasSelected().clear();
        for (Clinica clinica : clinicaFilterPojo.getClinicas()) {
            boolean isSelected = true;
            for (ClinicFilterCategory category : categories) {


                if (!category.getSelecteds().contains(clinica)){
                    isSelected = false;
                    break;
                }
            }
            if (isSelected){
                clinicaFilterPojo.getClinicasSelected().add(clinica);
            }
        }

    }



    @Override
    protected void resetAllCategories() {
        for (ClinicFilterCategory category : categories) {
            category.reset();
        }
    }

}
