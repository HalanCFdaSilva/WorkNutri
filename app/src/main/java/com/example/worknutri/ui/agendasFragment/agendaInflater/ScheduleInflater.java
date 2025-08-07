package com.example.worknutri.ui.agendasFragment.agendaInflater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.agendaInflater.cardsInflater.ClinicaCardInflater;
import com.example.worknutri.ui.agendasFragment.agendaInflater.cardsInflater.PacienteCardInflater;
import java.util.ArrayList;
import java.util.List;

public abstract class ScheduleInflater<T> {

    protected final Context context;

    protected ScheduleInflater(Context context) {
        this.context = context;
    }

    public  void generateAgenda(ViewGroup viewGroup, List<T> elementsToAdd){

        List<T> elementsToAddCopy = new ArrayList<>(elementsToAdd);

        while (!elementsToAddCopy.isEmpty()){

            ViewGroup categoryViewGroup = generateCategory(elementsToAddCopy.get(0));
            viewGroup.addView(categoryViewGroup);
            List<T> collect = filterElements(elementsToAddCopy);
            inflateCard(categoryViewGroup.findViewById(R.id.agenda_letter_fragment_linear_layout), collect);
            elementsToAddCopy.removeAll(collect);
        }

    }

    protected abstract List<T> filterElements(List<T> elementsToAdd);

    protected ViewGroup generateCategory(T firstElementOfCategory) {
        ViewGroup categoryViewGroup = (ViewGroup) LayoutInflater.from(context).inflate(
                com.example.worknutri.R.layout.agenda_letter_fragment, null);

        TextView textView = categoryViewGroup.findViewById(R.id.agenda_letter_fragment_agenda_textview);
        textView.setText(getCategoryTitle(firstElementOfCategory).toUpperCase());

        return categoryViewGroup;
    }

    protected abstract String getCategoryTitle(T firstElementOfCategory);

    protected void inflateCard(ViewGroup viewGroup, List<T> toAdd) {

        for (T element : toAdd) {
            CardView cardView = new CardView(context);
            if (element instanceof Clinica) {
                generateClinicaCard(cardView, (Clinica) element);
            } else if (element instanceof Paciente) {
                generatePacienteCard(cardView, (Paciente) element);
            }
            if (cardView.getChildCount() != 0){
                viewGroup.addView(cardView);
            }
        }

    }

    private void generatePacienteCard(ViewGroup viewGroup, Paciente element) {
        PacienteCardInflater pacienteCardInflater = new PacienteCardInflater(context);
        pacienteCardInflater.generateCard(viewGroup, element);

    }

    private void generateClinicaCard(ViewGroup viewGroup, Clinica element) {
        ClinicaCardInflater clinicaCardInflater = new ClinicaCardInflater(context);
        clinicaCardInflater.generateCard(viewGroup, element);

    }
}
