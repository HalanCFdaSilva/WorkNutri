package com.example.worknutri.ui.agendasFragment.agendaClinicas.Inflaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;

import java.util.ArrayList;
import java.util.List;


public class LetterClinicaFragment {

    private LayoutInflater layoutInflater;
    private final List<Clinica> clinicas;

    public LetterClinicaFragment(LayoutInflater inflater, List<Clinica> clinicasInOrder) {
        layoutInflater = inflater;
        this.clinicas = clinicasInOrder;
    }


    public void generateAgenda(ViewGroup viewGroup,Context context){

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
        for (char letter: alphabet) {
            List<Clinica> pacientestoAdd = getPacientesOfLetter(letter);
            pacientestoAdd.forEach(paciente -> clinicas.remove(paciente));
            if (!pacientestoAdd.isEmpty()){
                ViewGroup letterLayout =(ViewGroup) layoutInflater.inflate(R.layout.agenda_letter_fragment,null);

                TextView textView = letterLayout.findViewById(R.id.agenda_letter_fragment_agenda_textview);
                textView.setText(String.valueOf(letter));

                LinearLayout linearLayout = letterLayout.findViewById(R.id.agenda_letter_fragment_linear_layout);
                inflateLetterPacienteCard(linearLayout,pacientestoAdd,context);

                viewGroup.addView(letterLayout);
            }
        }
    }

    private List<Clinica> getPacientesOfLetter(char letter) {
        List<Clinica> newPacientes = new ArrayList<>();
        for (Clinica clinica : clinicas){
            if (!clinica.getNome().isEmpty()){
                if (letter  == clinica.getNome().toUpperCase().charAt(0)){
                    newPacientes.add(clinica);

                }
                if (letter  < clinica.getNome().toUpperCase().charAt(0)){
                    break;
                }
            }
        }
        return newPacientes;
    }



    private void inflateLetterPacienteCard(LinearLayout layout, List<Clinica>clinicasToAdd, Context context){
        ClinicaCardInflater cardInflater = new ClinicaCardInflater(clinicasToAdd,context);
        cardInflater.refreshLayout(layout );
    }
}
