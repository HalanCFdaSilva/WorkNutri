package com.example.nutricoop.ui.agendaPacientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.nutricoop.MainActivity;
import com.example.nutricoop.databinding.FragmentAgendaPacientesBinding;
import com.example.nutricoop.sqlLite.dao.PacienteDao;
import com.example.nutricoop.sqlLite.database.AppDataBase;
import com.example.nutricoop.ui.agendaPacientes.Inflaters.LetterPacienteFragment;


public class AgendaPacienteFragment extends Fragment {


    private FragmentAgendaPacientesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        binding = FragmentAgendaPacientesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        MainActivity.isPaciente = true;
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.agendaPacienteFragmentLinearLayout.removeAllViews();
        PacienteDao pacienteDao = AppDataBase.getInstance(getContext()).pacienteDao();
        LetterPacienteFragment letterPacienteFragment = new LetterPacienteFragment(getLayoutInflater(),pacienteDao.getAllInOrder());
        letterPacienteFragment.generateAgenda(binding.agendaPacienteFragmentLinearLayout);



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}