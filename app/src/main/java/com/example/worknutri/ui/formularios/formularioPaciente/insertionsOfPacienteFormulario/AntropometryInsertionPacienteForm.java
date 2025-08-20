package com.example.worknutri.ui.formularios.formularioPaciente.insertionsOfPacienteFormulario;

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.worknutri.R;
import com.example.worknutri.calcular.CalculadorAntropometrico;
import com.example.worknutri.calcular.MeasureConverter;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.InsertSelectViewSupport;

public class AntropometryInsertionPacienteForm extends InsertionPacienteForm{
    public AntropometryInsertionPacienteForm(ViewGroup viewGroup) {
        super(viewGroup);
    }

    public void insertViewGroupInAntropometria( Antropometria antropometria, Paciente paciente) {


        double pesoAtual = getValueConverTed(viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_atual),
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_atual_spinner));
        antropometria.setPeso(String.valueOf(pesoAtual));

        double altura = getValueConverTed(viewGroup.findViewById(R.id.formulario_paciente_antropometria_altura),
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_spinner_altura));
        antropometria.setAltura(String.valueOf(altura));

        double pesoIdeal = getValueConverTed(viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_ideal),
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_ideal_spinner));
        antropometria.setPesoIdeal(String.valueOf(pesoIdeal));


        int positionOfAtivity = ((Spinner) viewGroup.findViewById(
                R.id.formulario_paciente_antropometria_calculos_atividade_spinner)).getSelectedItemPosition();

        CalculadorAntropometrico calculador = new CalculadorAntropometrico(pesoAtual, altura);

        antropometria.setImc(calculador.generateImc());

        antropometria.setTaxaMetabolica(calculador.generateTMB(paciente.getGenero(),
                CalculadorAntropometrico.getYearFromDate(paciente.getNascimento())));

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


    }

    private double getValueConverTed(EditText editText, Spinner spinner) {
        double pesoAtual = Double.parseDouble(InsertSelectViewSupport.getStringOfEditText(editText));
        int position = getPositionOfSpinner(spinner);
        return MeasureConverter.convertToGramOrMeters(position, pesoAtual);
    }

    /**
     * Método que preenche o viewGroup de Antropometria do FormularioPaciente com os dados que recebe da classe Antropometria
     * com a ajuda da classe InsertSelectViewSupport.
     *
     * @see Antropometria
     * @see InsertSelectViewSupport
     */
    public void insertAntropometria(Antropometria antropometria) {
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
}
