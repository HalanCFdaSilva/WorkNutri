package com.example.worknutri.ui.agendasFragment.agendaInflater.cardsInflater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.detail.detailPaciente.PacienteDescriptionActivity;


public class PatientCardInflater implements CardInflater<Paciente> {

    private final Context context;


    public PatientCardInflater(Context context) {


        this.context = context;

    }

    @Override
    public void configureOnClickInCard(ViewGroup card, Paciente paciente) {

            card.setOnClickListener( onClick ->{
                Intent intent = new Intent(context, PacienteDescriptionActivity.class);
                intent.putExtra(ExtrasActivities.PACIENTE_EXTRA.getKey(), paciente);
                context.startActivities(new Intent[]{intent});
            });

    }

    @Override
    public ViewGroup inflateCard(Paciente patient) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.registry_card_fragment_patient,
                new LinearLayout(context),false);
        TextView textView = view.findViewById(R.id.registry_card_fragment_patient_textview);
        textView.setText(patient.getNomePaciente());
        return view;
    }


}
