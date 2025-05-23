package com.example.worknutri.ui.agendasFragment.agendaClinicas;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.worknutri.MainActivity;
import com.example.worknutri.databinding.FragmentAgendaBinding;


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

//        binding.agendaFragmentFabCalendario.setVisibility(View.VISIBLE);


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.inflateAgenda(getLayoutInflater(), binding.agendaFragmentLinearLayout);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}