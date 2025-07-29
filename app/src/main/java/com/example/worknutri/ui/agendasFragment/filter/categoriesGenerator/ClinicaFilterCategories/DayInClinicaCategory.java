package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicaFilterCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;
import java.util.stream.Collectors;

public class DayInClinicaCategory extends ClinicaFilterCategory {

    private boolean isFirstTime = true;
    protected DayInClinicaCategory(Context context, ClinicaFilterPojo clinicaFilterPojo) {
        super(context, clinicaFilterPojo);
    }

    @Override
    public ViewGroup generateView(LayoutInflater layoutInflater) {
        ViewGroup viewGroup = categoriesGeneratorUtil.generateCategoryWithChipGroup(layoutInflater,"Dia na Clinica:");
        String[] diasSemana = context.getResources().getStringArray(R.array.dias_semana);
        ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);
        for (String dia : diasSemana){
            Chip chip = categoriesGeneratorUtil.generateChip(dia);
            onClickInChip(chip);
            selectChip(chip);
            chipGroup.addView(chip);
        }
        return viewGroup;
    }

    private void selectChip(Chip chip) {
        String dayOfWeek = chip.getText().toString();
        if (clinicaFilterPojo.getUiState().getDaysOfWeekSelected().contains(dayOfWeek))
            chip.setChecked(true);
    }

    private void onClickInChip(Chip chip) {
        chip.setOnCheckedChangeListener((buttonView, isChecked) -> {

            String dayOfWeek = chip.getText().toString();
            List<Clinica> clinicasFiltred = getClinicasInsideChip(dayOfWeek);
            List<String> daysOfWeekSelected = clinicaFilterPojo.getUiState().getDaysOfWeekSelected();
            if (isChecked){
                insertInClinicasSelecteds(clinicasFiltred);
                daysOfWeekSelected.add(dayOfWeek);
            }else {
                clinicasSelecteds.removeAll(clinicasFiltred);
                if (clinicasSelecteds.isEmpty())
                    reset();
                else
                    daysOfWeekSelected.remove(dayOfWeek);
            }
        });
    }

    private void insertInClinicasSelecteds(List<Clinica> clinicasFiltred) {
        if (isFirstTime){
            isFirstTime = false;
            clinicasSelecteds.clear();
        }

        for (Clinica clinica : clinicasFiltred){
            if(!clinicasSelecteds.contains(clinica)){
                clinicasSelecteds.add(clinica);
            }
        }
    }

    @NonNull
    private List<Clinica> getClinicasInsideChip(String dayOfWeek) {
        List<DayOfWork> daysOfWorkFiltred = clinicaFilterPojo.getDayOfWorkList().stream()
                .filter(dayOfWork -> dayOfWork.getDayOfWeek().equals(dayOfWeek)).collect(Collectors.toList());

        return clinicaFilterPojo.getClinicas().stream()
                .filter(clinica -> {
                    for (DayOfWork dayOfWork : daysOfWorkFiltred){
                        if (dayOfWork.getIdClinica() == clinica.getId())
                            return true;
                    }
                    return false;})
                .collect(Collectors.toList());
    }



    @Override
    public void reset() {
        super.reset();
        isFirstTime = true;
        clinicaFilterPojo.getUiState().getDaysOfWeekSelected().clear();

    }
}
