package com.example.nutricoop.ui.formularios.formularioPaciente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nutricoop.R;
import com.example.nutricoop.sqlLite.clinica.domain.Clinica;
import com.example.nutricoop.ui.InsertSelectViewSupport;

import java.util.List;

public class ClinicaArrayAdapter extends BaseAdapter {
    private final Context context;
    private final List<Clinica> clinicas;


    public ClinicaArrayAdapter(Context context, List<Clinica> clinicas) {
        this.context = context;
        this.clinicas = clinicas;

    }

    @Override
    public int getCount() {
        return clinicas != null ? clinicas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.card_fragment_clinica, parent, false);

        Clinica clinica = clinicas.get(position);

        TextView textView = viewGroup.findViewById(R.id.card_fragment_clinica_name);
        InsertSelectViewSupport.insertInTextView(textView,clinica.getNome());
        textView = viewGroup.findViewById(R.id.card_fragment_clinica_rua);
        InsertSelectViewSupport.insertInTextView(textView,clinica.getRua());
        textView = viewGroup.findViewById(R.id.card_fragment_clinica_bairro);
        InsertSelectViewSupport.insertInTextView(textView,clinica.getBairro());
        textView = viewGroup.findViewById(R.id.card_fragment_clinica_cidade);
        InsertSelectViewSupport.insertInTextView(textView,clinica.getCidade());




        return viewGroup;
    }
}
