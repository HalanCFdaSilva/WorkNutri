package com.example.worknutri.ui.agendasFragment.agendaClinicas.Inflaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import java.util.List;

public abstract class ScheduleInflater<T> {

    protected final Context context;

    protected ScheduleInflater(Context context) {
        this.context = context;
    }

    public  void generateAgenda(ViewGroup viewGroup, List<T> elementsToAdd){

        viewGroup.removeAllViews();

        while (!elementsToAdd.isEmpty()){

            ViewGroup categoryViewGroup = generateCategory(viewGroup, elementsToAdd.get(0));
            List<T> collect = filterElements(elementsToAdd);
            elementsToAdd.removeAll(collect);
            inflateCard(categoryViewGroup.findViewById(R.id.agenda_letter_fragment_linear_layout), collect);
        }

    }

    protected abstract List<T> filterElements(List<T> elementsToAdd);

    ;

    protected ViewGroup generateCategory(ViewGroup viewToInsert, T firstElementOfCategory) {
        ViewGroup categoryViewGroup = (ViewGroup) LayoutInflater.from(context).inflate(
                com.example.worknutri.R.layout.agenda_letter_fragment, viewToInsert);

        TextView textView = categoryViewGroup.findViewById(R.id.agenda_letter_fragment_agenda_textview);
        textView.setText(getCategoryTitle(firstElementOfCategory));

        return categoryViewGroup;
    }

    protected abstract String getCategoryTitle(T firstElementOfCategory);

    protected void inflateCard(ViewGroup viewGroup, List<T> toAdd) {

        ViewGroup clinicaCard = null;

        for (T element : toAdd) {
            if (element instanceof Clinica) {
                clinicaCard = getClinicaCard(viewGroup, (Clinica) element);
            } else if (element instanceof Paciente) {
                clinicaCard = getPacienteCard(viewGroup, (Paciente) element);
            }
        }
        if (clinicaCard != null) {
            clinicaCard.findViewById(R.id.card_fragment_clinica_sortdivider).setVisibility(View.GONE);
        }
    }

    private ViewGroup getPacienteCard(ViewGroup viewGroup, Paciente element) {
        return null;
    }

    private ViewGroup getClinicaCard(ViewGroup viewGroup, Clinica element) {
        ClinicaCardInflater clinicaCardInflater = new ClinicaCardInflater(context);
        return clinicaCardInflater.generateClinicaCard(viewGroup, element);

    }
}
