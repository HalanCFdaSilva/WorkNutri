package com.example.worknutri.ui.agendasFragment.filter.pacientesFilter;


import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.PACIENTE_FILTER_BUNDLE;
import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.PACIENTE_FILTER_POJO;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.FilterFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;
import java.util.stream.Collectors;


public class PacienteFilterFragment extends FilterFragment {

    private PacienteFilterPojo pojo;




    @Override
    protected void generateFilter() {
        getPacientes();
        setFilterTitle("PACIENTES");
        generateGenderCategory();


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

    private void generateGenderCategory() {
        ViewGroup viewGroup = agendaFilter.GenerateChipsCategory(getLayoutInflater(), getLayoutToCategories());
        TextView text = viewGroup.findViewById(R.id.filter_category_title);
        text.setText("Gênero");
        ChipGroup group = viewGroup.findViewById(R.id.filter_category_chipgroup);
        group.setSingleSelection(true);

        Chip chip = getChipToGender("Masculino");
        group.addView(chip);
        chip = getChipToGender("Feminino");
        group.addView(chip);

    }

    private Chip getChipToGender(String genero) {
        Chip chip = agendaFilter.generateChip(genero);
        chip.setChecked(pojo.getState().getGeneroSelected().equals(genero.substring(0,1)));
        onClickSave(chip, genero.charAt(0));
        return chip;

    }
    private void onClickSave(Chip chip,char genero){

        chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pojo.getState().setInOrder(false);

            if (isChecked) {

                pojo.getState().setGeneroSelected(String.valueOf(genero));

                List<Paciente> pacientesFiltred = pojo.getPacienteSelected().stream()
                        .filter(paciente -> paciente.getGenero() == genero).collect(Collectors.toList());
                pojo.setPacienteSelected(pacientesFiltred);

            }else{
                pojo.getState().setGeneroSelected("NONE");
                List<Paciente> pacientesFiltred = pojo.getPacientes().stream()
                        .filter(paciente -> paciente.getGenero() != genero).collect(Collectors.toList());
                pojo.getPacienteSelected().addAll(pacientesFiltred);
            }

        });
    }

    @Override
    protected Bundle generateBundle() {
        Bundle bundle = new Bundle();
        filterPacientesSelected();
        bundle.putSerializable(PACIENTE_FILTER_POJO, pojo);
        return bundle;
    }

    private void filterPacientesSelected(){
        pojo.setPacienteSelected( pojo.getPacienteSelected().stream().sorted((o1, o2) -> o1.getNomePaciente().compareTo(o2.getNomePaciente())).collect(Collectors.toList()));

    }

    @Override
    protected String getRequestKey() {
        return PACIENTE_FILTER_BUNDLE;
    }


}
