package com.example.worknutri.ui.formularios.formularioPaciente.insertionsOfPacienteFormulario;

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.worknutri.R;
import com.example.worknutri.calcular.AntropometricCalculator;
import com.example.worknutri.calcular.MeasureConverter;
import com.example.worknutri.calcular.MeasureTypes;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.util.ViewsUtil;

public class AntropometryInsertionPacienteForm extends InsertionPacienteForm{
    public AntropometryInsertionPacienteForm(ViewGroup viewGroup) {
        super(viewGroup);
    }

    public void insertViewGroupInAntropometria( Antropometria antropometria, Paciente paciente) {



        double pesoAtual = getValueConverTed(getMeasureTypesToWeightSpinner(R.id.formulario_paciente_antropometria_peso_atual_spinner),
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_atual), MeasureTypes.KILO);
        antropometria.setPeso(String.valueOf(pesoAtual));

        Spinner spinner = viewGroup.findViewById(R.id.formulario_paciente_antropometria_spinner_altura);
        double altura = getValueConverTed(MeasureTypes.fromValue(spinner.getSelectedItemPosition()),viewGroup.findViewById(R.id.formulario_paciente_antropometria_altura),
                MeasureTypes.GRAM_METER);
        antropometria.setAltura(String.valueOf(altura));

        double pesoIdeal = getValueConverTed(getMeasureTypesToWeightSpinner(R.id.formulario_paciente_antropometria_peso_ideal_spinner),
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_ideal), MeasureTypes.KILO);
        antropometria.setPesoIdeal(String.valueOf(pesoIdeal));


        int positionOfAtivity = ((Spinner) viewGroup.findViewById(
                R.id.formulario_paciente_antropometria_calculos_atividade_spinner)).getSelectedItemPosition();

        AntropometricCalculator calculador = new AntropometricCalculator(pesoAtual, altura);

        antropometria.setImc(calculador.generateImc());

        int yearFromDate = AntropometricCalculator.getYearFromDate(paciente.getNascimento());
        antropometria.setAgua(String.valueOf(calculador.generateAgua(yearFromDate, pesoAtual)));
        antropometria.setTaxaMetabolica(calculador.generateTMB(paciente.getGenero(), yearFromDate));

        antropometria.setValorMetabolico(calculador.generateGET(Double.parseDouble(antropometria.getTaxaMetabolica()),
                positionOfAtivity));

        antropometria.setRegraBolso(calculador.generateBolso(pesoIdeal));

        int valorAPerder = ((Spinner) viewGroup.findViewById(R.id.formulario_paciente_antropometria_calculos_peso_a_perder_spinner)).getSelectedItemPosition();
        antropometria.setVenta(calculador.generateVenta(Double.parseDouble(antropometria.getValorMetabolico()), valorAPerder));


        antropometria.setCircumferenciaBracoDir(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_braco)));
        antropometria.setCircumferenciaCoxaDir(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_coxa)));
        antropometria.setCircumferenciaAbdomen(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_abdomen)));
        antropometria.setCircumferenciaCintura(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_cintura)));
        antropometria.setCircumferenciaQuadril(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_quadril)));


    }

    private MeasureTypes getMeasureTypesToWeightSpinner(int spinnerId) {
        Spinner spinner = viewGroup.findViewById(spinnerId);
        int selectedItemPosition = spinner.getSelectedItemPosition();
        return MeasureTypes.fromValue(selectedItemPosition-3);
    }

    private double getValueConverTed(MeasureTypes measureTypeActual, EditText editText, MeasureTypes measureTypeExpected) {
        double valueOfEditText = Double.parseDouble(ViewsUtil.getStringOfEditText(editText));
        return MeasureConverter.convertTo(measureTypeActual, valueOfEditText,measureTypeExpected);
    }

    /**
     * Método que preenche o viewGroup de Antropometria do FormularioPaciente com os dados que recebe da classe Antropometria
     * com a ajuda da classe InsertSelectViewSupport.
     *
     * @see Antropometria
     * @see ViewsUtil
     */
    public void insertAntropometria(Antropometria antropometria) {
        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_altura)
                , antropometria.getAltura());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_atual)
                , antropometria.getPeso());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_ideal)
                , antropometria.getPesoIdeal());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_braco)
                , antropometria.getCircumferenciaBracoDir());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_coxa)
                , antropometria.getCircumferenciaCoxaDir());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_abdomen)
                , antropometria.getCircumferenciaAbdomen());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_cintura)
                , antropometria.getCircumferenciaCintura());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.formulario_paciente_antropometria_circum_quadril)
                , antropometria.getCircumferenciaQuadril());
    }
}
