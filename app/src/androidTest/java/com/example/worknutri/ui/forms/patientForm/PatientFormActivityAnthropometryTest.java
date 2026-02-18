package com.example.worknutri.ui.forms.patientForm;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.R;
import com.example.worknutri.calcular.AntropometricCalculator;
import com.example.worknutri.calcular.ClassificacaoImc;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.support.DrawableMatcher;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.support.TextViewAssertions;
import com.example.worknutri.support.ViewUtil;
import com.example.worknutri.ui.formularios.formularioPaciente.PatientFormActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RunWith(AndroidJUnit4.class)
public class PatientFormActivityAnthropometryTest {

    private ActivityScenario<PatientFormActivity> scenario;


    @Before
    public void setUp() {

        scenario = ActivityScenario.launch(PatientFormActivity.class);

    }

    @After
    public void tearDown() {

        if (scenario != null) {
            scenario.close();
        }
    }

    @Test
    public void whenFillerHeightFilledIdealWeightCorrectly(){
        double idealWeightExpected = 22*Math.pow(1.75,2);

        checkHeightKeyListener("mm", "1750", idealWeightExpected);
        checkHeightKeyListener("cm", "175", idealWeightExpected);
        checkHeightKeyListener("dm", "17.5", idealWeightExpected);
        checkHeightKeyListener("m", "1.75", idealWeightExpected);


    }



    private void checkHeightKeyListener(String measureType, String height, double idealWeightExpected) {

        ViewUtil.selectSpinnerItemByName(R.id.formulario_paciente_antropometria_spinner_altura, measureType);

        scenario.onActivity(activity -> {
            EditText etHeight = activity.findViewById(R.id.formulario_paciente_antropometria_altura);
            EditText etIdealWeight = activity.findViewById(R.id.formulario_paciente_antropometria_peso_ideal);
            etHeight.setText(height);
            etHeight.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_0));
            etHeight.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_0));

            assertEquals(measureType, ((Spinner)activity.findViewById(R.id.formulario_paciente_antropometria_spinner_altura)).getSelectedItem().toString());
            assertEquals("0".concat(height), etHeight.getText().toString());
            assertEquals(String.valueOf(idealWeightExpected).substring(0,5), etIdealWeight.getText().toString());
        });
    }

    @Test
    public void onClickInButtonToViewAAnthropometryDataWithBirthHeightAndWeightsFilledOpenASmallPopUpOfAntropometry() throws InterruptedException {
        onView(withId(R.id.popup_base_layout)).check(doesNotExist());

        openAntropometrypopUp("11/12/2000", "1.75", "70");

        onView(withId(R.id.popup_base_layout)).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.popup_form_antropometric_description)).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));

    }

    private void openAntropometrypopUp( String birth, String height, String weight) throws InterruptedException {
        insertAntropologicDatas(birth, height, weight);

        scenario.onActivity(activity -> activity.findViewById(
                R.id.formulario_paciente_antropometria_calculos_button).performClick());

        Thread.sleep(300);
    }

    private static void insertAntropologicDatas(String birth, String height, String weight) {
        onView(withId(R.id.formulario_paciente_dados_pessoais_nascimento)).perform(replaceText(birth));
        onView(withId(R.id.formulario_paciente_antropometria_altura)).perform(replaceText(height));
        onView(withId(R.id.formulario_paciente_antropometria_peso_atual)).perform(replaceText(weight));
        onView(withId(R.id.formulario_paciente_antropometria_peso_ideal)).perform(replaceText(TestUtil
                .formatDoubleToString(AntropometricCalculator.generatePesoIdeal(Double.parseDouble(height)))));
    }

    @Test
    public void checkAntropometryPopUpDataIsCorrect() throws InterruptedException {
        onView(withId(R.id.popup_base_layout)).check(doesNotExist());
        double weight = 70;
        double height = 1.75;
        AntropometricCalculator calculator = new AntropometricCalculator(weight,height);
        openAntropometrypopUp("11/12/2000", String.valueOf(height), String.valueOf(weight));

        String imc = calculator.generateImc();
        ClassificacaoImc imcType = ClassificacaoImc.tipoImc(Double.parseDouble(imc));
        onView(withId(R.id.popup_form_antropometric_description_imc)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions.matchesText(imc.substring(0,5)));
        onView(withId(R.id.classificacao_imc_textview)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions.matchesText(imcType.name()))
                .check(TextViewAssertions.matchesBackgroundDrawable(imcType.getColor()));

        String metabolicalTax = calculator.generateTMB('M', 25);
        onView(withId(R.id.popup_form_antropometric_description_metabolical_rate)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions.matchesText(metabolicalTax.concat(" Kcal")));

        String metabolicalValue = calculator.generateGET(Double.parseDouble(metabolicalTax), 0);
        onView(withId(R.id.popup_form_antropometric_description_energy_expend)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions.matchesText(metabolicalValue.concat(" Kcal")));

        String ventaExpected = calculator.generateVenta(Double.parseDouble(metabolicalValue), 0);
        ventaExpected = ventaExpected.substring(0, ventaExpected.indexOf(".")+3).concat(" Kcal/Dia");
        onView(withId(R.id.popup_form_antropometric_description_venta)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions.matchesText(ventaExpected));

        String bolsoExpected = calculator.generateBolso(22*Math.pow(height,2)).concat(" Kcal");
        onView(withId(R.id.popup_form_antropometric_description_thumb)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions.matchesText(bolsoExpected));

        String waterExpected = String.valueOf(calculator.generateAgua(25,weight)).concat(" ml");
        onView(withId(R.id.popup_form_antropometric_description_water)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions.matchesText(waterExpected));


    }

    @Test
    public void whenClickInImageButtonOpenAPopupWithInformationAboutActivityLevel() throws InterruptedException {
        onView(withId(R.id.popup_base_layout)).check(doesNotExist());

        scenario.onActivity(activity -> activity.findViewById(
                R.id.formulario_paciente_antropometria_calculos_atividade_info_imageview).performClick());
        Thread.sleep(300);

        onView(withId(R.id.popup_base_layout)).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));

        onView(withId(R.id.popup_activity_level_imageview)).inRoot(RootMatchers.isPlatformPopup())
                .check(matches(DrawableMatcher.withDrawable(R.drawable.tabela_desc_nivel_atividade_pic)));

    }

    @Test
    public void onClickInSaveButtonInsertDataInDbCorrectely() {
        String pacientName = "Test Name";
        onView(withId(R.id.formulario_paciente_dados_pessoais_name)).perform(replaceText(pacientName));
        LocalDate date = LocalDate.of(2000, 12, 12);

        Antropometria antropometricExpected = TestEntityFactory.generateAntropometria(
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), 1.75, 70);

        insertDataInactivity(date, antropometricExpected);

        scenario.onActivity(activity ->
                activity.findViewById(R.id.formulario_paciente_activity_fab).performClick());

        AppDataBase db = AppDataBase.getInstance(TestUtil.getThemedContext());
        long id = db.pacienteDao().findByName(pacientName).get(0).getId();
        Antropometria antropometricGenerate = db.antropometriaDao().getByPacienteId(id);
        compareData(antropometricGenerate, antropometricExpected);

    }

    private void insertDataInactivity(LocalDate date, Antropometria antropometricExpected) {
        insertAntropologicDatas(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                antropometricExpected.getAltura(), antropometricExpected.getPeso());

        onView(withId(R.id.formulario_paciente_antropometria_circum_cintura))
                .perform(replaceText(antropometricExpected.getCircumferenciaCintura()));
        onView(withId(R.id.formulario_paciente_antropometria_circum_coxa))
                .perform(replaceText(antropometricExpected.getCircumferenciaCoxaDir()));
        onView(withId(R.id.formulario_paciente_antropometria_circum_braco))
                .perform(replaceText(antropometricExpected.getCircumferenciaBracoDir()));
        onView(withId(R.id.formulario_paciente_antropometria_circum_abdomen))
                .perform(replaceText(antropometricExpected.getCircumferenciaAbdomen()));

        scenario.onActivity(activity -> {
            ((TextView)activity.findViewById(
                    R.id.formulario_paciente_antropometria_circum_quadril)).setText(antropometricExpected.getCircumferenciaQuadril());
            Spinner spinner = activity.findViewById(R.id.formulario_paciente_antropometria_calculos_atividade_spinner);
            spinner.setSelection(1);
            spinner = activity.findViewById(R.id.formulario_paciente_antropometria_calculos_peso_a_perder_spinner);
            spinner.setSelection(0);

        });

    }

    private static void compareData(Antropometria antropometricGenerate, Antropometria antropometricExpected) {
        assertNotNull(antropometricGenerate);
        assertEquals(antropometricExpected.getAltura(), antropometricGenerate.getAltura());
        assertEquals(antropometricExpected.getPeso(), antropometricGenerate.getPeso());
        assertEquals(antropometricExpected.getPesoIdeal(), antropometricGenerate.getPesoIdeal());
        assertEquals(antropometricExpected.getCircumferenciaAbdomen(), antropometricGenerate.getCircumferenciaAbdomen());
        assertEquals(antropometricExpected.getCircumferenciaCintura(), antropometricGenerate.getCircumferenciaCintura());
        assertEquals(antropometricExpected.getCircumferenciaBracoDir(), antropometricGenerate.getCircumferenciaBracoDir());
        assertEquals(antropometricExpected.getCircumferenciaCoxaDir(), antropometricGenerate.getCircumferenciaCoxaDir());
        assertEquals(antropometricExpected.getCircumferenciaQuadril(), antropometricGenerate.getCircumferenciaQuadril());
        assertEquals(antropometricExpected.getRegraBolso(), antropometricGenerate.getRegraBolso());
        assertEquals(antropometricExpected.getTaxaMetabolica(), antropometricGenerate.getTaxaMetabolica());
        assertEquals(antropometricExpected.getValorMetabolico(), antropometricGenerate.getValorMetabolico());
        assertEquals(antropometricExpected.getVenta(), antropometricGenerate.getVenta());
        assertEquals(antropometricExpected.getAgua(), antropometricGenerate.getAgua());
        assertEquals(antropometricExpected.getImc(), antropometricGenerate.getImc());
    }

}
