package com.example.worknutri.ui.agendasFragment.agendaPacientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.worknutri.MainActivity;
import com.example.worknutri.databinding.FragmentAgendaBinding;


public class AgendaPacienteFragment extends Fragment {


    private FragmentAgendaBinding binding;
    private AgendaPacienteAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAgendaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        MainActivity.isPaciente = true;
        binding.agendaFragmentLayoutSearch.setHint("Digite o nome do paciente");
        adapter = new AgendaPacienteAdapter(getContext());
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.agendaFragmentLinearLayout.removeAllViews();
        adapter.inflateAgenda(getLayoutInflater(), binding.agendaFragmentLinearLayout);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}