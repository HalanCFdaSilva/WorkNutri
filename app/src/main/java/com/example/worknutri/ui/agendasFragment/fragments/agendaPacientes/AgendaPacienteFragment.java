package com.example.worknutri.ui.agendasFragment.fragments.agendaPacientes;

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
import com.example.worknutri.databinding.FragmentAgendaBinding;
import com.example.worknutri.ui.agendasFragment.filter.NavsDirection.NavDirectionPacienteFilter;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;


public class AgendaPacienteFragment extends Fragment {


    private FragmentAgendaBinding binding;
    private AgendaPacienteAdapter adapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAgendaBinding.inflate(inflater, container, false);
        adapter = new AgendaPacienteAdapter(getContext());


        binding.agendaFragmentLayoutSearch.setHint("Digite o nome do paciente");
        binding.agendaFragmentEditTextSearch.addTextChangedListener(new TextWatcherAgendaPaciente(adapter, binding.agendaFragmentLinearLayout));

        return binding.getRoot();
    }





    @Override
    public void onStart() {
        super.onStart();
        binding.agendaFragmentLinearLayout.removeAllViews();
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
                adapter.inflateAgenda( binding.agendaFragmentLinearLayout);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}