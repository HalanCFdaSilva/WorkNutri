package com.example.worknutri.ui.agendasFragment.filter.pacientesFilter;


import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.PACIENTE_FILTER_BUNDLE;
import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.PACIENTE_FILTER_POJO;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.FilterFragment;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.CategoriesGenerator;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.antropometriaCategories.AntropometriaCategoryFactory;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.pacientesCategories.PacientesCategoryFactory;
import com.example.worknutri.ui.agendasFragment.filter.pojos.PacienteFilterPojo;
import java.util.Comparator;
import java.util.stream.Collectors;


public class PacienteFilterFragment extends FilterFragment {

    private PacienteFilterPojo pojo;


    @Override
    protected void generateFilter(Context context) {
        getPacientes();

        insertCategotyInLayout(PacientesCategoryFactory.generateGenderCategory(context, pojo));
        insertCategotyInLayout(PacientesCategoryFactory.generateYearCategory(context, pojo));
        insertCategotyInLayout(PacientesCategoryFactory.generatePacienteInClinicaCategory(context, pojo));
        insertCategotyInLayout(AntropometriaCategoryFactory.createWeightCategory(context, pojo));
        insertCategotyInLayout(AntropometriaCategoryFactory.createHeightCategory(context, pojo));
        insertCategotyInLayout(AntropometriaCategoryFactory.createIMCCategory(context, pojo));

    }

    @Override
    protected void getAllCategories() {
        pojo.getPacienteSelected().removeAll(pojo.getPacienteSelected());
        for (Paciente paciente : pojo.getPacientes()) {
            boolean isSelected = true;
            for (CategoriesGenerator category : categories) {
                Log.d("PacienteFilterFragment", "getAllCategories: " + paciente.getNomePaciente() + " isSelected: " + !category.getSelecteds().contains(paciente));

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
    protected void orderListOfSelecteds() {
        if (!pojo.getState().isInOrder()){
            switch (pojo.getState().getOrderBy()){
                case NOME_ASC:{
                    pojo.setPacienteSelected(pojo.getPacienteSelected().stream()
                            .sorted(Comparator.comparing(Paciente::getNomePaciente))
                            .collect(Collectors.toList()));
                    break;
                }
                case NOME_DESC:{
                    pojo.setPacienteSelected(pojo.getPacienteSelected().stream()
                            .sorted(Comparator.comparing(Paciente::getNomePaciente).reversed())
                            .collect(Collectors.toList()));
                    break;
                }
            }
        }
    }


    private void getPacientes() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (arguments.containsKey(PACIENTE_FILTER_POJO)) {
                pojo = (PacienteFilterPojo) arguments.getSerializable(PACIENTE_FILTER_POJO);
            } else {
                dismiss();
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



}
