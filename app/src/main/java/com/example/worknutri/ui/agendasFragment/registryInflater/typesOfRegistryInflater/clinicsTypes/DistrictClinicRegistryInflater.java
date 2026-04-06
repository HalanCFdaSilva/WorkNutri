package com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.clinicsTypes;

import android.content.Context;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.registryInflater.RegistryInflater;

import java.util.List;
import java.util.stream.Collectors;

public class DistrictClinicRegistryInflater extends RegistryInflater<Clinica> {
    public DistrictClinicRegistryInflater(Context context) {
        super(context);
    }

    @Override
    protected List<Clinica> filterElements(List<Clinica> elementsToAdd) {
        String divisor = getCategoryTitle(elementsToAdd.get(0));
        return elementsToAdd.stream().filter(clinica -> clinica.getBairro()
                .equalsIgnoreCase(divisor)).collect(Collectors.toList());
    }

    @Override
    protected String getCategoryTitle(Clinica firstElementOfCategory) {
        return firstElementOfCategory.getBairro();
    }
}
