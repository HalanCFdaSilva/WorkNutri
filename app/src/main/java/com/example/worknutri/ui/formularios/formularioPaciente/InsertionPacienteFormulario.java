package com.example.worknutri.ui.formularios.formularioPaciente;

import android.view.ViewGroup;
import android.widget.Spinner;
import com.example.worknutri.R;
import com.example.worknutri.calcular.CalculadorAntropometrico;
import com.example.worknutri.calcular.Conversor;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.InsertSelectViewSupport;

import java.util.List;

public class InsertionPacienteFormulario {


    public Patologia insertViewGroupInPatologia(ViewGroup viewGroup, Patologia patologia) {
        return patologia;
    }

    public Antropometria insertViewGroupInAntropometria(ViewGroup viewGroup, Antropometria antropometria, Paciente paciente) {


        double pesoAtual = Double.parseDouble(InsertSelectViewSupport.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_atual)));
        int position = getPositionOfSpinner(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_atual_spinner));
        antropometria.setPeso(Conversor.convertToGramOrMeters(position, pesoAtual));
        pesoAtual = Double.parseDouble(antropometria.getPeso());

        double altura = Double.parseDouble(InsertSelectViewSupport.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_altura)));
        position = getPositionOfSpinner(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_spinner_altura));
        antropometria.setAltura(Conversor.convertToGramOrMeters(position, altura));
        altura = Double.parseDouble(antropometria.getAltura());

        double pesoIdeal = Double.parseDouble(InsertSelectViewSupport.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_ideal)));
        position = getPositionOfSpinner(viewGroup.findViewById(
                R.id.formulario_paciente_antropometria_peso_ideal_spinner));

        antropometria.setPesoIdeal(Conversor.convertToGramOrMeters(position, pesoIdeal));
        pesoIdeal = Double.parseDouble(antropometria.getPesoIdeal());

        int positionOfAtivity = ((Spinner) viewGroup.findViewById(
                R.id.formulario_paciente_antropometria_calculos_atividade_spinner)).getSelectedItemPosition();

        CalculadorAntropometrico calculador = new CalculadorAntropometrico(pesoAtual, altura);
        antropometria.setImc(calculador.generateImc());
        antropometria.setTaxaMetabolica(calculador.generateTMB(paciente.getGenero(), paciente.getIdade()));
        antropometria.setValorMetabolico(calculador.generateGET(Double.parseDouble(antropometria.getTaxaMetabolica()),
                positionOfAtivity, paciente.getGenero()));
        antropometria.setRegraBolso(calculador.generateBolso(pesoIdeal));
        int valorAPerder = ((Spinner) viewGroup.findViewById(R.id.formulario_paciente_antropometria_calculos_peso_a_perder_spinner)).getSelectedItemPosition();
        antropometria.setVenta(calculador.generateVenta(Double.parseDouble(antropometria.getValorMetabolico()), valorAPerder));
        antropometria.setAgua(String.valueOf(calculador.generateAgua(paciente.getIdade(), pesoAtual)));

        antropometria.setCircumferenciaBracoDir(InsertSelectViewSupport.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_braco)));
        antropometria.setCircumferenciaCoxaDir(InsertSelectViewSupport.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_coxa)));
        antropometria.setCircumferenciaAbdomen(InsertSelectViewSupport.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_abdomen)));
        antropometria.setCircumferenciaCintura(InsertSelectViewSupport.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_cintura)));
        antropometria.setCircumferenciaQuadril(InsertSelectViewSupport.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_quadril)));

        return antropometria;
    }

    public Paciente insertViewGroupInPaciente(ViewGroup viewGroup, Paciente paciente) {


        paciente.setNomePaciente(InsertSelectViewSupport.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_name)));

        int i = getPositionOfSpinner(
                viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_genero_spinner));
        paciente.setGenero(i == 0 ? 'M' : 'F');

        paciente.setNascimento(InsertSelectViewSupport.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_nascimento)));

        paciente.setEmail(InsertSelectViewSupport.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_email)));

        paciente.setTelefone(InsertSelectViewSupport.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_fone)));
        paciente.setObservacoes(InsertSelectViewSupport.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_observation)));

        return paciente;
    }

    public void insertClinicaInPaciente(Spinner spinner, Paciente paciente, List<Clinica> clinicasInOrder) {
        int i = getPositionOfSpinner(spinner);
        Clinica clinica = clinicasInOrder.get(i);
        paciente.setClinicaId(clinica.getId());
    }

    /**
     * Método que preenche o viewGroup de Paciente do FormularioPaciente com os dados que recebe da classe Paciente
     * com a ajuda da classe InsertSelectViewSupport.
     *
     * @see Paciente
     * @see InsertSelectViewSupport
     */
    public void insertPacienteInViewGroup(ViewGroup viewGroup, Paciente paciente) {
        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_name)
                , paciente.getNomePaciente());
        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_nascimento)
                , paciente.getNascimento());
        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_email)
                , paciente.getEmail());
        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_fone)
                , paciente.getTelefone());

        if (paciente.getGenero() == 'M') {
            ((Spinner) viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_genero_spinner)).setSelection(0);
        } else {
            ((Spinner) viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_genero_spinner)).setSelection(1);
        }
    }

    /**
     * Método que preenche o viewGroup de Antropometria do FormularioPaciente com os dados que recebe da classe Antropometria
     * com a ajuda da classe InsertSelectViewSupport.
     *
     * @see Antropometria
     * @see InsertSelectViewSupport
     */
    public void insertAntropometria(ViewGroup viewGroup, Antropometria antropometria) {
        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_altura)
                , antropometria.getAltura());
        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_atual)
                , antropometria.getPeso());
        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_ideal)
                , antropometria.getPesoIdeal());
        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_braco)
                , antropometria.getCircumferenciaBracoDir());
        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_coxa)
                , antropometria.getCircumferenciaCoxaDir());
        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_abdomen)
                , antropometria.getCircumferenciaAbdomen());
        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_cintura)
                , antropometria.getCircumferenciaCintura());
        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_quadril)
                , antropometria.getCircumferenciaQuadril());
    }


    /**
     * Método que preenche o viewGroup de patologia do FormularioPaciente com os dados que recebe da classe Patologia com
     * a ajuda da classe InsertSelectViewSupport.
     *
     * @see Patologia
     * @see InsertSelectViewSupport
     */
    public void InsertPatologia(ViewGroup viewGroup, Patologia patologia) {

    }

    public void SelectClinica(Spinner spinnerClinica, long clinicaId, List<Clinica> clinicas) {
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

    private int getPositionOfSpinner(Spinner spinner) {
        return spinner.getSelectedItemPosition();
    }
}
