package com.example.nutricoop.ui.agendaClinicas;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.nutricoop.MainActivity;

import com.example.nutricoop.R;
import com.example.nutricoop.databinding.FragmentAgendaBinding;
import com.example.nutricoop.sqlLite.dao.ClinicaDao;
import com.example.nutricoop.sqlLite.database.AppDataBase;
import com.example.nutricoop.sqlLite.domain.clinica.Clinica;
import com.example.nutricoop.ui.InsertSelectViewSupport;

import java.util.List;

public class AgendaClinicasFragment extends Fragment {

    private FragmentAgendaBinding binding;
    ClinicaDao dao;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAgendaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        MainActivity.isPaciente = false;

        dao = AppDataBase.getInstance(getContext()).clinicaDao();
        binding.agendaFragmentLayoutSearch.setHint("Digite o nome da clinica");
        binding.agendaFragmentFabCalendario.setVisibility(View.VISIBLE);

        inflateAgenda();



        return root;
    }
    private void inflateAgenda(){
        List<Clinica> clinicaList = dao.getAllInOrder();

        for (Clinica clinica : clinicaList){
            ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.card_fragment_clinica, null);
            TextView textView = viewGroup.findViewById(R.id.card_fragment_clinica_name);
            InsertSelectViewSupport.insertInTextView(textView,clinica.getNome());
            textView = viewGroup.findViewById(R.id.card_fragment_clinica_rua);
            InsertSelectViewSupport.insertInTextView(textView,clinica.getRua());
            textView = viewGroup.findViewById(R.id.card_fragment_clinica_bairro);
            InsertSelectViewSupport.insertInTextView(textView,clinica.getBairro());
            textView = viewGroup.findViewById(R.id.card_fragment_clinica_cidade);
            InsertSelectViewSupport.insertInTextView(textView,clinica.getCidade());

            binding.agendaFragmentLinearLayout.addView(viewGroup);

        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}