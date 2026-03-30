package com.example.worknutri.ui.agendasFragment.fragments.clinicRegistry;


import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.CLINICA_FILTER_BUNDLE;
import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.CLINICA_FILTER_POJO;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.worknutri.R;
import com.example.worknutri.databinding.RegistryFragmentBinding;
import com.example.worknutri.ui.agendasFragment.filter.NavsDirection.NavDirectionClinicaFilter;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;


public class ClinicRegistryFragment extends Fragment {

    private RegistryFragmentBinding binding;
    private ClinicRegistryAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = RegistryFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        adapter = new ClinicRegistryAdapter(getContext());
        binding.registryFragmentLayoutSearch.setHint("Digite o nome da clinica");
        binding.registryFragmentEditTextSearch.addTextChangedListener(
                new TextWatcherClinicRegistry(adapter, binding.registryFragmentLinearLayout));

        return root;
    }



    @Override
    public void onStart() {
        super.onStart();
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
            NavDirectionClinicaFilter filter = new NavDirectionClinicaFilter(
                    R.id.action_navigation_clinicas_to_filter,
                    adapter.getClinicaFilterPojo());
            Navigation.findNavController(v).navigate(filter);
        });
    }

    private void updateAgendaAfterFilter() {

        getParentFragmentManager().setFragmentResultListener(CLINICA_FILTER_BUNDLE,this, (requestKey, result) -> {
            if (result.containsKey(CLINICA_FILTER_POJO)) {
                ClinicaFilterPojo clinicaFilterPojo = (ClinicaFilterPojo) result.getSerializable(CLINICA_FILTER_POJO);
                adapter.setClinicaFilterPojo(clinicaFilterPojo);
                adapter.inflateAgenda(binding.registryFragmentLinearLayout);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}