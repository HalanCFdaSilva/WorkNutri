package com.example.worknutri.ui.agendasFragment.fragments.agendaClinicas;


import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.CLINICA_FILTER_BUNDLE;
import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.CLINICA_FILTER_POJO;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.worknutri.MainActivity;
import com.example.worknutri.R;
import com.example.worknutri.databinding.FragmentAgendaBinding;
import com.example.worknutri.ui.agendasFragment.filter.NavsDirection.NavDirectionClinicaFilter;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;


public class AgendaClinicasFragment extends Fragment {

    private FragmentAgendaBinding binding;
    private AgendaClinicasAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAgendaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        MainActivity.isPaciente = false;
        adapter = new AgendaClinicasAdapter(getContext());


        binding.agendaFragmentLayoutSearch.setHint("Digite o nome da clinica");
        binding.agendaFragmentEditTextSearch.addTextChangedListener(new TextWatcherAgendaClinica(adapter, binding.agendaFragmentLinearLayout));



        return root;
    }



    @Override
    public void onStart() {
        super.onStart();
        adapter.inflateAgenda(binding.agendaFragmentLinearLayout);
        configureFilter();
    }
    private void configureFilter() {
        callFilter();

        updateAgendaAfterFilter();
    }
    private void callFilter() {
        binding.agendaFragmentButonFilter.setOnClickListener(v->{

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
                adapter.inflateAgenda(binding.agendaFragmentLinearLayout);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}