package com.example.worknutri.ui.formularios.formularioPaciente.insertionsOfPacienteFormulario;

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.util.ViewsUtil;

import java.util.List;

public class PacienteInsertionPacienteForm extends InsertionPacienteForm{
    public PacienteInsertionPacienteForm(ViewGroup viewGroup) {
        super(viewGroup);
    }

    public void insertViewGroupInPaciente( Paciente paciente) {


        paciente.setNomePaciente(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_name)));

        int i = ((Spinner)viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_genero_spinner)).getSelectedItemPosition();
        paciente.setGenero(i == 0 ? 'M' : 'F');

        paciente.setNascimento(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_nascimento)));

        paciente.setEmail(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_email)));

        paciente.setTelefone(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_fone)));

    }
    public static void insertObservationsInPaciente(Paciente paciente, EditText editText) {
        paciente.setObservacoes(ViewsUtil.getStringOfEditText(
               editText));
    }

    public static void insertClinicaInPaciente(Spinner spinner, Paciente paciente, List<Clinica> clinicasInOrder) {
        int i = spinner.getSelectedItemPosition();
        Clinica clinica = clinicasInOrder.get(i);
        paciente.setClinicaId(clinica.getId());
    }

    /**
     * Método que preenche o viewGroup de Paciente do FormularioPaciente com os dados que recebe da classe Paciente
     * com a ajuda da classe InsertSelectViewSupport.
     *
     * @see Paciente
     * @see ViewsUtil
     */
    public void insertPacienteInViewGroup( Paciente paciente) {
        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_name)
                , paciente.getNomePaciente());
        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_nascimento)
                , paciente.getNascimento());
        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_email)
                , paciente.getEmail());
        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_fone)
                , paciente.getTelefone());

        if (paciente.getGenero() == 'M') {
            ((Spinner) viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_genero_spinner)).setSelection(0);
        } else {
            ((Spinner) viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_genero_spinner)).setSelection(1);
        }
    }






    public static void SelectClinica(Spinner spinnerClinica, long clinicaId, List<Clinica> clinicas) {
        int i = 0;
        for (Clinica clinica : clinicas) {
            if (clinicaId == clinica.getId()) {
                break;
            }
            i++;
        }
        if (i < clinicas.size()) {
            spinnerClinica.setSelection(i);
        }
    }
}
