package com.example.nutricoop.ui.agendaPacientes.Inflaters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nutricoop.R;
import com.example.nutricoop.sqlLite.paciente.domain.Paciente;
import com.example.nutricoop.ui.ExtrasActivities;
import com.example.nutricoop.ui.detail.detailPaciente.DetailPacienteActivie;

import java.io.Serializable;
import java.util.List;

public class PacienteCardInflater {
    private List<Paciente> pacientes;
    private Context context;


    public PacienteCardInflater(List<Paciente> pacienteDao, Context context) {

        pacientes  = pacienteDao;
        this.context = context;

    }

    public void refreshLayout(LinearLayout layout, LayoutInflater inflater){
        layout.removeAllViews();
        pacientes.forEach(paciente -> {
            ViewGroup view = (ViewGroup) inflater.inflate(R.layout.card_fragment_paciente,null);
            TextView textView = view.findViewById(R.id.paciente_card_fragment_textview);
            textView.setText(paciente.getNomePaciente());
            layout.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailPacienteActivie.class);
                    intent.putExtra(ExtrasActivities.PACIENTE, (Serializable) paciente);
                    context.startActivities(new Intent[]{intent});
                }
            });
        });

    }



}
