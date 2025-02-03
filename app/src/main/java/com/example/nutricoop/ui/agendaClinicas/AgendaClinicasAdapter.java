package com.example.nutricoop.ui.agendaClinicas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nutricoop.R;
import com.example.nutricoop.sqlLite.clinica.dao.ClinicaDao;
import com.example.nutricoop.sqlLite.clinica.domain.Clinica;
import com.example.nutricoop.sqlLite.database.AppDataBase;
import com.example.nutricoop.ui.ExtrasActivities;
import com.example.nutricoop.ui.InsertSelectViewSupport;
import com.example.nutricoop.ui.detail.detailClinica.ClinicaDescriptionActivity;

import java.util.List;

public class AgendaClinicasAdapter {

    private ClinicaDao dao;
    private Context context;

    public AgendaClinicasAdapter(Context context) {
        dao = AppDataBase.getInstance(context).clinicaDao();
        this.context = context;
    }

    public void inflateAgenda(LayoutInflater inflater, LinearLayout linearLayout){
        List<Clinica> clinicaList = dao.getAllInOrder();
        linearLayout.removeAllViews();
        for (Clinica clinica : clinicaList){
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.card_fragment_clinica, null);
            viewGroup.setForeground(context.getDrawable(R.drawable.border_small));

            TextView textView = viewGroup.findViewById(R.id.card_fragment_clinica_name);
            InsertSelectViewSupport.insertInTextView(textView,clinica.getNome());
            textView = viewGroup.findViewById(R.id.card_fragment_clinica_rua);
            InsertSelectViewSupport.insertInTextView(textView,clinica.getRua());
            textView = viewGroup.findViewById(R.id.card_fragment_clinica_bairro);
            InsertSelectViewSupport.insertInTextView(textView,clinica.getBairro());
            textView = viewGroup.findViewById(R.id.card_fragment_clinica_cidade);
            InsertSelectViewSupport.insertInTextView(textView,clinica.getCidade());

            viewGroup.setOnClickListener(v -> {
                Intent intent = new Intent(context, ClinicaDescriptionActivity.class);
                intent.putExtra(ExtrasActivities.CLINICA,clinica);
                context.startActivities(new Intent[]{intent});
            });

            linearLayout.addView(viewGroup);

        }

    }
}
