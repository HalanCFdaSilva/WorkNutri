package com.example.nutricoop.ui.agendaPacientes.Inflaters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nutricoop.R;
import com.example.nutricoop.sqlLite.dao.PacienteDao;
import com.example.nutricoop.sqlLite.domain.paciente.Paciente;

import java.util.List;

public class PacienteCardInflater {
    private List<Paciente> pacientes;


    public PacienteCardInflater(List<Paciente> pacienteDao) {

        pacientes  = pacienteDao;

    }

    public void refreshLayout(LinearLayout layout, LayoutInflater inflater){
        layout.removeAllViews();
        pacientes.forEach(paciente -> {
            ViewGroup view = (ViewGroup) inflater.inflate(R.layout.paciente_card_fragment,null);
            TextView textView = view.findViewById(R.id.paciente_card_fragment_textview);
            textView.setText(paciente.getNomePaciente());
            layout.addView(view);
        });

    }



}
