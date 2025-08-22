package com.example.worknutri.ui.formularios.formularioPaciente.insertionsOfPacienteFormulario;

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.worknutri.R;
import com.example.worknutri.calcular.CalculadorAntropometrico;
import com.example.worknutri.calcular.MeasureConverter;
import com.example.worknutri.calcular.MeasureTypes;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.util.EditAndTextViewsUtil;

public class AntropometryInsertionPacienteForm extends InsertionPacienteForm{
    public AntropometryInsertionPacienteForm(ViewGroup viewGroup) {
        super(viewGroup);
    }

    public void insertViewGroupInAntropometria( Antropometria antropometria, Paciente paciente) {


        double pesoAtual = getValueConverTed(viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_atual),
                getMeasureTypesFromGramSpinner(viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_atual_spinner)));
        antropometria.setPeso(String.valueOf(pesoAtual));

        Spinner spinner = viewGroup.findViewById(R.id.formulario_paciente_antropometria_spinner_altura);
        double altura = getValueConverTed(viewGroup.findViewById(R.id.formulario_paciente_antropometria_altura),
                MeasureTypes.fromValue(spinner.getSelectedItemPosition()));
        antropometria.setAltura(String.valueOf(altura));

        double pesoIdeal = getValueConverTed(viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_ideal),
                getMeasureTypesFromGramSpinner(viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_ideal_spinner)));
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

        antropometria.setCircumferenciaBracoDir(EditAndTextViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_braco)));
        antropometria.setCircumferenciaCoxaDir(EditAndTextViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_coxa)));
        antropometria.setCircumferenciaAbdomen(EditAndTextViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_abdomen)));
        antropometria.setCircumferenciaCintura(EditAndTextViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_cintura)));
        antropometria.setCircumferenciaQuadril(EditAndTextViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_quadril)));


    }

    private MeasureTypes getMeasureTypesFromGramSpinner(Spinner spinner) {
        int selectedItemPosition = spinner.getSelectedItemPosition();
        return MeasureTypes.fromValue(selectedItemPosition-3);
    }

    private double getValueConverTed(EditText editText, MeasureTypes measureTypes) {
        double valueOfEditText = Double.parseDouble(EditAndTextViewsUtil.getStringOfEditText(editText));
        return MeasureConverter.convertToGramOrMeters(measureTypes, valueOfEditText);
    }

    /**
     * Método que preenche o viewGroup de Antropometria do FormularioPaciente com os dados que recebe da classe Antropometria
     * com a ajuda da classe InsertSelectViewSupport.
     *
     * @see Antropometria
     * @see EditAndTextViewsUtil
     */
    public void insertAntropometria(Antropometria antropometria) {
        EditAndTextViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_altura)
                , antropometria.getAltura());

        EditAndTextViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_atual)
                , antropometria.getPeso());

        EditAndTextViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_ideal)
                , antropometria.getPesoIdeal());

        EditAndTextViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_braco)
                , antropometria.getCircumferenciaBracoDir());

        EditAndTextViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_coxa)
                , antropometria.getCircumferenciaCoxaDir());

        EditAndTextViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_abdomen)
                , antropometria.getCircumferenciaAbdomen());

        EditAndTextViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_cintura)
                , antropometria.getCircumferenciaCintura());

        EditAndTextViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_quadril)
                , antropometria.getCircumferenciaQuadril());
    }
}
