package com.example.worknutri.ui.forms.formInserters;

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.worknutri.R;
import com.example.worknutri.calcular.AntropometricCalculator;
import com.example.worknutri.calcular.MeasureConverter;
import com.example.worknutri.calcular.MeasureTypes;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.forms.InvalidViewGroupIdException;
import com.example.worknutri.util.ViewsUtil;

public class AnthropometryFormInserter extends FormInserter<Antropometria> {
    private AnthropometryFormInserter(ViewGroup viewGroup) {
        super(viewGroup);
        viewGroupIdExpected = R.id.patient_form_activity_anthropometry_layout;
    }

    public static AnthropometryFormInserter create (ViewGroup viewGroup) throws InvalidViewGroupIdException{
        if (checkViewGroupIsCorrectly(viewGroup)) {
            return new AnthropometryFormInserter(viewGroup);
        } else {
            throw new InvalidViewGroupIdException( viewGroup.getId());
        }
    }

    @Override
    public void insertViewGroupInEntity(Antropometria antropometria) {

    }
    public void insertViewGroupInEntity(Antropometria antropometria, Paciente paciente) {

        double pesoAtual = getValueConverTed(getMeasureTypesToWeightSpinner(R.id.patient_form_activity_anthropometry_weight_current_spinner),
                viewGroup.findViewById(R.id.patient_form_activity_anthropometry_weight_current), MeasureTypes.KILO);
        antropometria.setPeso(String.valueOf(pesoAtual));

        Spinner spinner = viewGroup.findViewById(R.id.patient_form_activity_anthropometry_height_spinner);
        double altura = getValueConverTed(MeasureTypes.fromValue(spinner.getSelectedItemPosition()),viewGroup.findViewById(R.id.patient_form_activity_anthropometry_height),
                MeasureTypes.GRAM_METER);
        antropometria.setAltura(String.valueOf(altura));

        double pesoIdeal = getValueConverTed(getMeasureTypesToWeightSpinner(R.id.patient_form_activity_anthropometry_weight_ideal_spinner),
                viewGroup.findViewById(R.id.patient_form_activity_anthropometry_weight_ideal), MeasureTypes.KILO);
        antropometria.setPesoIdeal(String.valueOf(pesoIdeal));


        int positionOfAtivity = ((Spinner) viewGroup.findViewById(
                R.id.patient_form_activity_anthropometry_calculations_activity_level_spinner)).getSelectedItemPosition();

        AntropometricCalculator calculador = new AntropometricCalculator(pesoAtual, altura);

        antropometria.setImc(calculador.generateImc());

        int yearFromDate = AntropometricCalculator.getYearFromDate(paciente.getNascimento());
        antropometria.setAgua(String.valueOf(calculador.generateAgua(yearFromDate, pesoAtual)));
        antropometria.setTaxaMetabolica(calculador.generateTMB(paciente.getGenero(), yearFromDate));

        antropometria.setValorMetabolico(calculador.generateGET(Double.parseDouble(antropometria.getTaxaMetabolica()),
                positionOfAtivity));

        antropometria.setRegraBolso(calculador.generateBolso(pesoIdeal));

        int valorAPerder = ((Spinner) viewGroup.findViewById(R.id.patient_form_activity_anthropometry_calculations_weight_lose_spinner)).getSelectedItemPosition();
        antropometria.setVenta(calculador.generateVenta(Double.parseDouble(antropometria.getValorMetabolico()), valorAPerder));


        antropometria.setCircumferenciaBracoDir(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.patient_form_activity_anthropometry_circum_arm)));
        antropometria.setCircumferenciaCoxaDir(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.patient_form_activity_anthropometry_circum_thigh)));
        antropometria.setCircumferenciaAbdomen(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.patient_form_activity_anthropometry_circum_abdomen)));
        antropometria.setCircumferenciaCintura(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.patient_form_activity_anthropometry_circum_waist)));
        antropometria.setCircumferenciaQuadril(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.patient_form_activity_anthropometry_circum_hip)));
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
    @Override
    public void insertEntityInViewGroup(Antropometria antropometria) {
        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.patient_form_activity_anthropometry_height)
                , antropometria.getAltura());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.patient_form_activity_anthropometry_weight_current)
                , antropometria.getPeso());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.patient_form_activity_anthropometry_weight_ideal)
                , antropometria.getPesoIdeal());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.patient_form_activity_anthropometry_circum_arm)
                , antropometria.getCircumferenciaBracoDir());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.patient_form_activity_anthropometry_circum_thigh)
                , antropometria.getCircumferenciaCoxaDir());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.patient_form_activity_anthropometry_circum_abdomen)
                , antropometria.getCircumferenciaAbdomen());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.patient_form_activity_anthropometry_circum_waist)
                , antropometria.getCircumferenciaCintura());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.patient_form_activity_anthropometry_circum_hip)
                , antropometria.getCircumferenciaQuadril());
    }

    public static boolean checkViewGroupIsCorrectly(ViewGroup viewGroup){
        return  viewGroup != null &&(viewGroup.getId() == R.id.patient_form_activity ||
                viewGroup.getId() == R.id.patient_form_activity_anthropometry_layout);
    }
}
