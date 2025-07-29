package com.example.worknutri.ui.agendasFragment.filter.clinicaFilter;


import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.CLINICA_FILTER_BUNDLE;
import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.CLINICA_FILTER_POJO;

import android.content.Context;
import android.os.Bundle;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.filter.FilterFragment;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicaFilterCategories.ClinicaFilterCategory;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicaFilterCategories.ClinicaFilterCategoryFactory;
import com.example.worknutri.ui.agendasFragment.filter.pojos.OrderFilterSelectedsBy;
import com.example.worknutri.ui.agendasFragment.filter.pojos.UiState;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import kotlin.NotImplementedError;

public class ClinicaFilterFragment extends FilterFragment {

    private ClinicaFilterPojo clinicaFilterPojo;
    private final List<ClinicaFilterCategory> categories = new ArrayList<>();

    @Override
    protected void generateFilter(Context context) {
         getPojo();
        insertCategotyInLayout(ClinicaFilterCategoryFactory.generateDayInClinicaCategory(context, clinicaFilterPojo));
        insertCategotyInLayout(ClinicaFilterCategoryFactory.generateHourWorkCategory(context, clinicaFilterPojo));
    }

    @Override
    protected UiState getUiState() {
        return clinicaFilterPojo.getUiState();
    }

    @Override
    protected List<OrderFilterSelectedsBy> getListOfSortChips() {
        return List.of(
                OrderFilterSelectedsBy.NOME_ASC,
                OrderFilterSelectedsBy.NOME_DESC,
                OrderFilterSelectedsBy.BAIRRO,
                OrderFilterSelectedsBy.CITY,
                OrderFilterSelectedsBy.DAY_OF_WEEK,
                OrderFilterSelectedsBy.NUMBER_OF_PATIENTS
        );
    }


    protected void insertCategotyInLayout(ClinicaFilterCategory categoryGenerate) {
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
            for (ClinicaFilterCategory category : categories) {


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
    protected void orderListOfSelecteds() {
        if (!clinicaFilterPojo.getUiState().isInOrder()){
            switch (clinicaFilterPojo.getUiState().getOrderBy()){
                case NOME_ASC:{
                    clinicaFilterPojo.setClinicasSelected(clinicaFilterPojo.getClinicasSelected().stream()
                            .sorted(Comparator.comparing(Clinica::getNome))
                            .collect(Collectors.toList()));
                    break;
                }
                case NOME_DESC:{
                    clinicaFilterPojo.setClinicasSelected(clinicaFilterPojo.getClinicasSelected().stream()
                            .sorted(Comparator.comparing(Clinica::getNome).reversed())
                            .collect(Collectors.toList()));
                    break;
                }
                case CITY:{
                    clinicaFilterPojo.setClinicasSelected(clinicaFilterPojo.getClinicasSelected().stream()
                            .sorted(Comparator.comparing(Clinica::getCidade).reversed())
                            .collect(Collectors.toList()));
                    break;
                }
                case BAIRRO:{
                    clinicaFilterPojo.setClinicasSelected(clinicaFilterPojo.getClinicasSelected().stream()
                            .sorted(Comparator.comparing(Clinica::getBairro).reversed())
                            .collect(Collectors.toList()));
                    break;
                }
                case DAY_OF_WEEK: throw new NotImplementedError("Order by DAY_OF_WEEK is not implemented yet.");
                case NUMBER_OF_PATIENTS: throw new NotImplementedError("Order by NUMBER_OF_PATIENTS is not implemented yet.");
            }
        }
    }

    @Override
    protected void resetAllCategories() {
        for (ClinicaFilterCategory category : categories) {
            category.reset();
        }
    }

}
