package com.example.worknutri.ui.agendasFragment.agendaInflater.cardsInflater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.detail.detailPaciente.PacienteDescriptionActivity;


public class PacienteCardInflater implements CardInflater<Paciente> {

    private final Context context;


    public PacienteCardInflater( Context context) {


        this.context = context;

    }

    @Override
    public ViewGroup generateCard(ViewGroup layout, Paciente paciente) {

            ViewGroup view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.card_fragment_paciente, layout);
            TextView textView = view.findViewById(R.id.paciente_card_fragment_textview);
            textView.setText(paciente.getNomePaciente());

            view.setOnClickListener( onClick ->{
                Intent intent = new Intent(context, PacienteDescriptionActivity.class);
                intent.putExtra(ExtrasActivities.PACIENTE_EXTRA.getKey(), paciente);
                context.startActivities(new Intent[]{intent});
            });
            return view;

    }


}
