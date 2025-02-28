package com.example.worknutri.ui.agendasFragment.agendaClinicas.Inflaters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.InsertSelectViewSupport;
import com.example.worknutri.ui.detail.detailClinica.ClinicaDescriptionActivity;

import java.util.List;

public class ClinicaCardInflater {
    private final List<Clinica> clinicas;
    private final Context context;


    public ClinicaCardInflater(List<Clinica> clinicas, Context context) {

        this.clinicas = clinicas;
        this.context = context;

    }

    public void refreshLayout(LinearLayout layout) {
        layout.removeAllViews();
        ViewGroup viewGroup = null;
        for (Clinica clinica : clinicas) {
            viewGroup = generateClinicaCard(layout, clinica, context);
            viewGroup.findViewById(R.id.card_fragment_clinica_sortdivider).setVisibility(View.VISIBLE);

            viewGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ClinicaDescriptionActivity.class);
                    intent.putExtra(ExtrasActivities.CLINICA, clinica);
                    context.startActivities(new Intent[]{intent});
                }
            });
            layout.addView(viewGroup);
        }
        if (viewGroup != null) {
            viewGroup.findViewById(R.id.card_fragment_clinica_sortdivider).setVisibility(View.GONE);
        }
    }

    public static ViewGroup generateClinicaCard(ViewGroup layout, Clinica clinica, Context context) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.card_fragment_clinica, layout, false);
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
