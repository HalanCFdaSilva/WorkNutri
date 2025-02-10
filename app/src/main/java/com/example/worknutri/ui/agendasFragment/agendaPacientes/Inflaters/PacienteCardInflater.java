package com.example.worknutri.ui.agendasFragment.agendaPacientes.Inflaters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.detail.detailPaciente.PacienteDescriptionActivity;

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
                    Intent intent = new Intent(context, PacienteDescriptionActivity.class);
                    intent.putExtra(ExtrasActivities.PACIENTE, paciente);
                    context.startActivities(new Intent[]{intent});
                }
            });
        });

    }



}
