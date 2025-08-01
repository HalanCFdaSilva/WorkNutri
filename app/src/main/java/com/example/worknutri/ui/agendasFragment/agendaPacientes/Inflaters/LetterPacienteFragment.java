package com.example.worknutri.ui.agendasFragment.agendaPacientes.Inflaters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import java.util.ArrayList;
import java.util.List;


public class LetterPacienteFragment {

    private final LayoutInflater layoutInflater;
    private final List<Paciente> pacientes;

    public LetterPacienteFragment(LayoutInflater inflater, List<Paciente> pacientesInOrder) {
        layoutInflater = inflater;
        this.pacientes = new ArrayList<>(pacientesInOrder);
    }


    public void generateAgenda(ViewGroup viewGroup) {

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
        for (char letter : alphabet) {
            List<Paciente> pacientestoAdd = getPacientesOfLetter(letter);
            pacientestoAdd.forEach(pacientes::remove);
            if (!pacientestoAdd.isEmpty()) {
                ViewGroup layoutInflate = (ViewGroup) layoutInflater.inflate(R.layout.agenda_letter_fragment, null);

                TextView textView = layoutInflate.findViewById(R.id.agenda_letter_fragment_agenda_textview);
                textView.setText(String.valueOf(letter));

                LinearLayout linearLayout = layoutInflate.findViewById(R.id.agenda_letter_fragment_linear_layout);
                inflateLetterPacienteCard(linearLayout, pacientestoAdd, viewGroup.getContext());

                viewGroup.addView(layoutInflate);
            }
        }
    }

    private List<Paciente> getPacientesOfLetter(char letter) {
        List<Paciente> newPacientes = new ArrayList<>();
        for (Paciente paciente : pacientes) {
            if (!paciente.getNomePaciente().isEmpty()) {
                if (letter == paciente.getNomePaciente().toUpperCase().charAt(0)) {
                    newPacientes.add(paciente);

                }
                if (letter < paciente.getNomePaciente().toUpperCase().charAt(0)) {
                    break;
                }
            }
        }
        return newPacientes;
    }


    private void inflateLetterPacienteCard(LinearLayout layout, List<Paciente> pacientesToAdd, Context context) {
        PacienteCardInflater cardInflater = new PacienteCardInflater(pacientesToAdd, context);
        cardInflater.refreshLayout(layout, layoutInflater);
    }
}
