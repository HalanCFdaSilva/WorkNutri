package com.example.worknutri.ui.agendasFragment.agendaInflater.cardsInflater;

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
import com.example.worknutri.util.ViewsUtil;
import com.example.worknutri.ui.detail.detailClinica.ClinicDescriptionActivity;

public class ClinicCardInflater implements CardInflater<Clinica> {
    private final Context context;


    public ClinicCardInflater(Context context) {


        this.context = context;

    }

    @Override
    public void configureOnClickInCard(ViewGroup card, Clinica clinic) {


        card.setOnClickListener(onClick -> {
            Intent intent = new Intent(context, ClinicDescriptionActivity.class);
            intent.putExtra(ExtrasActivities.CLINICA_EXTRA.getKey(), clinic);
            context.startActivities(new Intent[]{intent});
        });

    }

    public ViewGroup inflateCard(Clinica clinic) {

        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.registry_card_fragment_clinic, new LinearLayout(context),false);
        TextView textView = viewGroup.findViewById(R.id.registry_card_fragment_clinic_name);
        ViewsUtil.insertInTextView(textView, clinic.getNome());
        textView = viewGroup.findViewById(R.id.registry_card_fragment_clinic_street);
        ViewsUtil.insertInTextViewOrTextViewGone(textView, clinic.getRua());
        textView = viewGroup.findViewById(R.id.registry_card_fragment_clinic_neighborhood);
        ViewsUtil.insertInTextViewOrTextViewGone(textView, clinic.getBairro());
        textView = viewGroup.findViewById(R.id.registry_card_fragment_clinic_city);
        ViewsUtil.insertInTextViewOrTextViewGone(textView, clinic.getCidade());
        if (clinic.getBairro().isBlank() || clinic.getCidade().isBlank()) {
            viewGroup.findViewById(R.id.registry_card_fragment_clinic_comma).setVisibility(View.GONE);
        }
        return viewGroup;
    }



}
