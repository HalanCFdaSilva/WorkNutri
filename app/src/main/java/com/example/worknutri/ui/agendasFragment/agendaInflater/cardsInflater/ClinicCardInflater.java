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
import com.example.worknutri.util.ViewsUtil;
import com.example.worknutri.ui.detail.detailClinica.ClinicDescriptionActivity;

public class ClinicCardInflater implements CardInflater<Clinica> {
    private final Context context;


    public ClinicCardInflater(Context context) {


        this.context = context;

    }

    @Override
    public ViewGroup generateCard(ViewGroup layoutWereAddCard, Clinica clinic) {

        ViewGroup viewGroup = inflateClinicCard(layoutWereAddCard, clinic, context);
        viewGroup.setOnClickListener(onClick -> {
            Intent intent = new Intent(context, ClinicDescriptionActivity.class);
            intent.putExtra(ExtrasActivities.CLINICA_EXTRA.getKey(), clinic);
            context.startActivities(new Intent[]{intent});
        });
        return viewGroup;
    }

    private ViewGroup inflateClinicCard(ViewGroup layout, Clinica clinic, Context context) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.card_fragment_clinica, layout);
        TextView textView = viewGroup.findViewById(R.id.card_fragment_clinica_name);
        ViewsUtil.insertInTextView(textView, clinic.getNome());
        textView = viewGroup.findViewById(R.id.card_fragment_clinica_rua);
        ViewsUtil.insertInTextViewOrTextViewGone(textView, clinic.getRua());
        textView = viewGroup.findViewById(R.id.card_fragment_clinica_bairro);
        ViewsUtil.insertInTextViewOrTextViewGone(textView, clinic.getBairro());
        textView = viewGroup.findViewById(R.id.card_fragment_clinica_cidade);
        ViewsUtil.insertInTextViewOrTextViewGone(textView, clinic.getCidade());
        if (clinic.getBairro().isBlank()) {
            viewGroup.findViewById(R.id.card_fragment_clinica_virgula).setVisibility(View.GONE);
        }
        return viewGroup;
    }

    public static ViewGroup generateClinicCardToSpinner(ViewGroup layout, Clinica clinic, Context context) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.card_fragment_clinica, layout, false);
        TextView textView = viewGroup.findViewById(R.id.card_fragment_clinica_name);
        ViewsUtil.insertInTextView(textView, clinic.getNome());
        textView = viewGroup.findViewById(R.id.card_fragment_clinica_rua);
        ViewsUtil.insertInTextView(textView, clinic.getRua());
        textView = viewGroup.findViewById(R.id.card_fragment_clinica_bairro);
        ViewsUtil.insertInTextView(textView, clinic.getBairro());
        textView = viewGroup.findViewById(R.id.card_fragment_clinica_cidade);
        ViewsUtil.insertInTextView(textView, clinic.getCidade());
        if (clinic.getBairro().isBlank() || clinic.getCidade().isBlank()) {
            viewGroup.findViewById(R.id.card_fragment_clinica_virgula).setVisibility(View.GONE);
        }
        return viewGroup;
    }


}
