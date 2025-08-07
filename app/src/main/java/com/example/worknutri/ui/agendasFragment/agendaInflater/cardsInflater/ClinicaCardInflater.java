package com.example.worknutri.ui.agendasFragment.agendaInflater.cardsInflater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.InsertSelectViewSupport;
import com.example.worknutri.ui.detail.detailClinica.ClinicaDescriptionActivity;

public class ClinicaCardInflater implements CardInflater<Clinica> {
    private final Context context;


    public ClinicaCardInflater( Context context) {


        this.context = context;

    }

    @Override
    public ViewGroup generateCard(ViewGroup layoutWereAddCard, Clinica clinica) {

        ViewGroup viewGroup = inflateClinicaCard(layoutWereAddCard, clinica, context);
        viewGroup.setOnClickListener(onClick -> {
            Intent intent = new Intent(context, ClinicaDescriptionActivity.class);
            intent.putExtra(ExtrasActivities.CLINICA, clinica);
            context.startActivities(new Intent[]{intent});
        });
        return viewGroup;
    }

    private ViewGroup inflateClinicaCard(ViewGroup layout, Clinica clinica, Context context) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.card_fragment_clinica, layout);
        TextView textView = viewGroup.findViewById(R.id.card_fragment_clinica_name);
        InsertSelectViewSupport.insertInTextView(textView, clinica.getNome());
        textView = viewGroup.findViewById(R.id.card_fragment_clinica_rua);
        InsertSelectViewSupport.insertInTextViewOrGone(textView, clinica.getRua());
        textView = viewGroup.findViewById(R.id.card_fragment_clinica_bairro);
        InsertSelectViewSupport.insertInTextViewOrGone(textView, clinica.getBairro());
        textView = viewGroup.findViewById(R.id.card_fragment_clinica_cidade);
        InsertSelectViewSupport.insertInTextViewOrGone(textView, clinica.getCidade());
        if (clinica.getBairro().isBlank()) {
            viewGroup.findViewById(R.id.card_fragment_clinica_virgula).setVisibility(View.GONE);
        }
        return viewGroup;
    }

    public static ViewGroup generateClinicaCardForSpinner(ViewGroup layout, Clinica clinica, Context context) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.card_fragment_clinica, layout, false);
        TextView textView = viewGroup.findViewById(R.id.card_fragment_clinica_name);
        InsertSelectViewSupport.insertInTextView(textView, clinica.getNome());
        textView = viewGroup.findViewById(R.id.card_fragment_clinica_rua);
        InsertSelectViewSupport.insertInTextView(textView, clinica.getRua());
        textView = viewGroup.findViewById(R.id.card_fragment_clinica_bairro);
        InsertSelectViewSupport.insertInTextView(textView, clinica.getBairro());
        textView = viewGroup.findViewById(R.id.card_fragment_clinica_cidade);
        InsertSelectViewSupport.insertInTextView(textView, clinica.getCidade());
        if (clinica.getBairro().isBlank() || clinica.getCidade().isBlank()) {
            viewGroup.findViewById(R.id.card_fragment_clinica_virgula).setVisibility(View.GONE);
        }
        return viewGroup;
    }


}
