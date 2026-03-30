package com.example.worknutri.ui.agendasFragment.fragments.patientRegistry;

import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.PACIENTE_FILTER_BUNDLE;
import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.PACIENTE_FILTER_POJO;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.worknutri.R;
import com.example.worknutri.databinding.RegistryFragmentBinding;
import com.example.worknutri.ui.agendasFragment.filter.NavsDirection.NavDirectionPacienteFilter;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;


public class PatientRegistryFragment extends Fragment {


    private RegistryFragmentBinding binding;
    private PatientRegistryAdapter adapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = RegistryFragmentBinding.inflate(inflater, container, false);
        adapter = new PatientRegistryAdapter(getContext());


        binding.registryFragmentLayoutSearch.setHint("Digite o nome do paciente");
        binding.registryFragmentEditTextSearch.addTextChangedListener(new TextWatcherPatientRegistry(adapter, binding.registryFragmentLinearLayout));

        return binding.getRoot();
    }





    @Override
    public void onStart() {
        super.onStart();
        binding.registryFragmentLinearLayout.removeAllViews();
        adapter.inflateAgenda(binding.registryFragmentLinearLayout);
        configureFilter();


    }

    private void configureFilter() {
        callFilter();

        updateAgendaAfterFilter();
    }

    private void callFilter() {
        binding.registryFragmentButtonFilter.setOnClickListener(v->{

            adapter.updatePojo();
            NavDirectionPacienteFilter filter = new NavDirectionPacienteFilter(
                    R.id.action_navigation_pacientes_to_filter,
                    adapter.getPacienteFilterPojo());
            Navigation.findNavController(v).navigate(filter);
        });
    }

    private void updateAgendaAfterFilter() {

        getParentFragmentManager().setFragmentResultListener(PACIENTE_FILTER_BUNDLE,this, (requestKey, result) -> {
            if (result.containsKey(PACIENTE_FILTER_POJO)) {
                PacienteFilterPojo pacienteFilterPojo = (PacienteFilterPojo) result.getSerializable(PACIENTE_FILTER_POJO);
                adapter.setPacienteFilterPojo(pacienteFilterPojo);
                adapter.inflateAgenda( binding.registryFragmentLinearLayout);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}