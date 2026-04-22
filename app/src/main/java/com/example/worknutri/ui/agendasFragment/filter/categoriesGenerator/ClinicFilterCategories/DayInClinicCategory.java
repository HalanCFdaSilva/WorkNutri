package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicFilterCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicFilterPojo;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class DayInClinicCategory extends ClinicFilterCategory {

    private boolean isFirstTime = true;
    protected DayInClinicCategory(Context context, ClinicFilterPojo clinicFilterPojo) {
        super(context, clinicFilterPojo);
    }

    @Override
    public ViewGroup generateView(LayoutInflater layoutInflater) {
        ViewGroup viewGroup = categoriesGeneratorUtil.generateCategoryWithChipGroup(layoutInflater,"Dia na Clinica:");
        viewGroup.setId(R.id.filter_category_clinic_day);
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
        if (clinicFilterPojo.getUiState().getDaysOfWeekSelected().contains(dayOfWeek))
            chip.setChecked(true);
    }

    private void onClickInChip(Chip chip) {
        chip.setOnCheckedChangeListener((buttonView, isChecked) -> {

            String dayOfWeek = chip.getText().toString();
            List<Clinica> clinicsFiltered = new ClinicsSelector(clinicFilterPojo.getClinicsList()).
                    byDayOfWeek(dayOfWeek, clinicFilterPojo.getDayOfWorkList());

            List<String> daysOfWeekSelected = clinicFilterPojo.getUiState().getDaysOfWeekSelected();
            if (isChecked){
                insertInClinicasSelecteds(clinicsFiltered);
                daysOfWeekSelected.add(dayOfWeek);
            }else {
                clinicasSelecteds.removeAll(clinicsFiltered);
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





    @Override
    public void reset() {
        super.reset();
        isFirstTime = true;
        clinicFilterPojo.getUiState().getDaysOfWeekSelected().clear();

    }
}
