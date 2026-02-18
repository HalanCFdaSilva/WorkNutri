package com.example.worknutri.ui.forms.patientForm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static com.example.worknutri.support.ViewUtil.assertEditTextCorrectFormatText;

import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.support.TextViewAssertions;
import com.example.worknutri.support.ViewUtil;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.formularios.formularioPaciente.PatientFormActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class PatientFormActivityGeneralTest {

    private ActivityScenario<PatientFormActivity> scenario;
    private AppDataBase db;


    @Before
    public void setUp() {

        scenario = ActivityScenario.launch(PatientFormActivity.class);
        db = AppDataBase.getInstance(ApplicationProvider.getApplicationContext());
    }

    @After
    public void tearDown() {

        if (scenario != null) {
            scenario.close();
        }
    }

    @Test
    public void phoneFieldFormatTextCorrectly() {

        scenario.onActivity(activity -> {
            EditText et = activity.findViewById(R.id.formulario_paciente_dados_pessoais_fone);
            assertEquals(InputType.TYPE_CLASS_PHONE, et.getInputType());

            // Expected 0 because the method press a key '0' to check the format after key pressed
            assertEditTextCorrectFormatText(et,"","0");
            assertEditTextCorrectFormatText(et, "1", "(10) ");
            assertEditTextCorrectFormatText(et, "12", "(12) 0");
            assertEditTextCorrectFormatText(et, "12345", "(12) 3450");
            assertEditTextCorrectFormatText(et, "123456", "(12) 3456-0");
            assertEditTextCorrectFormatText(et, "1234567890", "(12) 3456-7890");
            assertEditTextCorrectFormatText(et, "12934567890", "(12) 93456-7890");
            assertEditTextCorrectFormatText(et, "12345678900", "(12) 3456-7890");
        });
    }

    @Test
    public void birthFieldFormatTextCorrectly() {

        scenario.onActivity(activity -> {
            EditText et = activity.findViewById(R.id.formulario_paciente_dados_pessoais_nascimento);
            assertEquals(InputType.TYPE_CLASS_NUMBER, et.getInputType());

            // Expected 0 because the method press a key '0' to check the format after key pressed
            assertEditTextCorrectFormatText(et,"","0");
            assertEditTextCorrectFormatText(et, "1", "10/");
            assertEditTextCorrectFormatText(et, "12", "12/0");
            assertEditTextCorrectFormatText(et, "121", "12/10/");
            assertEditTextCorrectFormatText(et, "1212", "12/12/0");
            assertEditTextCorrectFormatText(et, "1212200", "12/12/2000");
            assertEditTextCorrectFormatText(et, "1212200000", "12/12/2000");

        });
    }

    @Test
    public void checkClickInSaveButtonWithEmptyFieldsShowACorrectlyMessageInLayoutAndNotSaveLayout() {
        checkVisibilityOfCampInError(View.INVISIBLE);

        onView(withId(R.id.formulario_paciente_activity_fab)).perform(click());
        
        String textExpected = ApplicationProvider.getApplicationContext().getString(R.string.error_blank);
        onView(withId(R.id.formulario_paciente_activity_error)).check(TextViewAssertions.matchesText(textExpected));
        checkVisibilityOfCampInError(View.VISIBLE);

        onView(withId(R.id.formulario_paciente_dados_pessoais_name))
                .check(TextViewAssertions.matchesHintColor(TestUtil.colorFromRes(R.color.obrigatorio)));
        onView(withId(R.id.formulario_paciente_dados_pessoais_nascimento))
                .check(TextViewAssertions.matchesHintColor(TestUtil.colorFromRes(R.color.obrigatorio)));
        onView(withId(R.id.formulario_paciente_antropometria_peso_atual))
                .check(TextViewAssertions.matchesHintColor(TestUtil.colorFromRes(R.color.obrigatorio)));
        onView(withId(R.id.formulario_paciente_antropometria_peso_atual))
                .check(TextViewAssertions.matchesHintColor(TestUtil.colorFromRes(R.color.obrigatorio)));
        onView(withId(R.id.formulario_paciente_antropometria_altura))
                .check(TextViewAssertions.matchesHintColor(TestUtil.colorFromRes(R.color.obrigatorio)));

    }

    private static void checkVisibilityOfCampInError(int visibilityExpected) {
        onView(withId(R.id.formulario_paciente_activity_error))
                .check(TextViewAssertions.matchesVisibility(visibilityExpected));
        onView(withId(R.id.formulario_paciente_dados_pessoais_name_obrigatorio))
                .check(TextViewAssertions.matchesVisibility(visibilityExpected));
        onView(withId(R.id.formulario_paciente_dados_pessoais_idade_obrigatorio))
                .check(TextViewAssertions.matchesVisibility(visibilityExpected));
        onView(withId(R.id.formulario_paciente_antropometria_peso_atual_obrigatorio))
                .check(TextViewAssertions.matchesVisibility(visibilityExpected));
        onView(withId(R.id.formulario_paciente_antropometria_peso_atual_obrigatorio))
                .check(TextViewAssertions.matchesVisibility(visibilityExpected));
        onView(withId(R.id.formulario_paciente_antropometria_altura_obrigatorio))
                .check(TextViewAssertions.matchesVisibility(visibilityExpected));
    }

    @Test
    public void notSaveInDataBankAndShowFieldsIfEmailFieldIsIncorrectlyFilled() throws Exception {
        ifFieldIncorrectlyShowErrorCorrectly(R.id.formulario_paciente_dados_pessoais_email,"aa",
                R.string.error_email);
        ifFieldIncorrectlyShowErrorCorrectly(R.id.formulario_paciente_dados_pessoais_email,"aa@",
                R.string.error_email);
        ifFieldIncorrectlyShowErrorCorrectly(R.id.formulario_paciente_dados_pessoais_email,"aa@aa",
                R.string.error_email);
        ifFieldIncorrectlyShowErrorCorrectly(R.id.formulario_paciente_dados_pessoais_email,"aa@aa.",
                R.string.error_email);
        ifFieldIncorrectlyShowErrorCorrectly(R.id.formulario_paciente_dados_pessoais_email,"aa@aa.c",
                R.string.error_email);
    }
    @Test
    public void notSaveInDataBankAndShowFieldsIfPhoneFieldIsIncorrectlyFilled() throws Exception {
        ifFieldIncorrectlyShowErrorCorrectly(R.id.formulario_paciente_dados_pessoais_fone, "12",
                R.string.error_fone);
        ifFieldIncorrectlyShowErrorCorrectly(R.id.formulario_paciente_dados_pessoais_fone, "(12) 1212-",
                R.string.error_fone);
        ifFieldIncorrectlyShowErrorCorrectly(R.id.formulario_paciente_dados_pessoais_fone, "(12) 1212-121",
                R.string.error_fone);
        ifFieldIncorrectlyShowErrorCorrectly(R.id.formulario_paciente_dados_pessoais_fone, "(12) 1212-121212",
                R.string.error_fone);

    }
    @Test
    public void notSaveInDataBankAndShowFieldsIfBirthdayFieldIsIncorrectlyFilled() throws Exception {
        ifFieldIncorrectlyShowErrorCorrectly(R.id.formulario_paciente_dados_pessoais_nascimento,
                "12", R.string.error_data);
        ifFieldIncorrectlyShowErrorCorrectly(R.id.formulario_paciente_dados_pessoais_nascimento,
                "12/04", R.string.error_data);
        ifFieldIncorrectlyShowErrorCorrectly(R.id.formulario_paciente_dados_pessoais_nascimento,
                "12/12/121", R.string.error_data);
        ifFieldIncorrectlyShowErrorCorrectly(R.id.formulario_paciente_dados_pessoais_nascimento,
                "29/02/2026", R.string.error_data);
        ifFieldIncorrectlyShowErrorCorrectly(R.id.formulario_paciente_dados_pessoais_nascimento,
                "29/13/1990", R.string.error_data);
        ifFieldIncorrectlyShowErrorCorrectly(R.id.formulario_paciente_dados_pessoais_nascimento,
                "41/07/1990", R.string.error_data);
        ifFieldIncorrectlyShowErrorCorrectly(R.id.formulario_paciente_dados_pessoais_nascimento,
                "29/12/0000", R.string.error_data);
        ifFieldIncorrectlyShowErrorCorrectly(R.id.formulario_paciente_dados_pessoais_nascimento,
                "29/10/0990", R.string.error_data);

    }
    private void ifFieldIncorrectlyShowErrorCorrectly(int idEdittextToCheck, String textToInsert, int idStringError) throws InterruptedException {
        List<Paciente> patientListBefore = db.pacienteDao().getAll();

        fillerNecessaryFields("Patient Teste");

        onView(withId(idEdittextToCheck)).perform(replaceText(textToInsert));

        onView(withId(R.id.formulario_paciente_activity_fab)).perform(click());

        Thread.sleep(500);

        onView(withId(idEdittextToCheck)).check(TextViewAssertions.matchesTextColor(TestUtil.colorFromRes(R.color.obrigatorio)));
        onView(withId(R.id.formulario_paciente_activity_error))
                .check(TextViewAssertions.matchesVisibility(View.VISIBLE))
                .check(TextViewAssertions.matchesText(TestUtil.getStringFromRes(idStringError)));

        List<Paciente> patientListAfter = db.pacienteDao().getAll();
        assertEquals("Esperava id 0 indicando que a clínica NÃO foi salva", patientListBefore.size(), patientListAfter.size());
    }

    @Test
    public void saveInDataBankAndCloseActivityWhenClickInSaveButtonWithOnlyMandatoryFieldsFilled() throws InterruptedException {
        List<Paciente> patientListBefore = db.pacienteDao().getAll();
        String patientName = "Patient Teste";
        fillerNecessaryFields(patientName);

        onView(withId(R.id.formulario_paciente_activity_fab)).perform(click());

        Thread.sleep(500);

        List<Paciente> patientListAfter = db.pacienteDao().getAll();
        assertEquals("Esperava id 1 indicando que a clínica foi salva", patientListBefore.size() + 1, patientListAfter.size());
        List<Paciente> byName = db.pacienteDao().findByName(patientName);
        assertEquals("Esperava id 1 indicando que a clínica foi salva", 1, byName.size());
    }

    private static void fillerNecessaryFields(String patientName) {
        onView(withId(R.id.formulario_paciente_dados_pessoais_name)).perform(replaceText(patientName));
        onView(withId(R.id.formulario_paciente_dados_pessoais_nascimento)).perform(replaceText("12/12/2000"));
        onView(withId(R.id.formulario_paciente_antropometria_peso_ideal)).perform(replaceText("67"));
        onView(withId(R.id.formulario_paciente_antropometria_peso_atual)).perform(replaceText("80"));
        onView(withId(R.id.formulario_paciente_antropometria_altura)).perform(replaceText("1.70"));
    }


    @Test
    public void checkOnClickToSaveSavePatientDataCorrectly() throws InterruptedException {
        Paciente patient = TestEntityFactory.generatePatient();
        patient.setNomePaciente("João Teste");
        fillerNecessaryFields(patient.getNomePaciente());
        onView(withId(R.id.formulario_paciente_dados_pessoais_nascimento))
                .perform(replaceText(patient.getNascimento()));
        onView(withId(R.id.formulario_paciente_dados_pessoais_fone))
                .perform(replaceText(patient.getTelefone()));
        onView(withId(R.id.formulario_paciente_dados_pessoais_email))
                .perform(replaceText(patient.getEmail()));
        ViewUtil.selectSpinnerItemByName(R.id.formulario_paciente_dados_pessoais_genero_spinner,
                String.valueOf(patient.getGenero()));

        scenario.onActivity(activity ->{
            ((TextView)activity.findViewById(R.id.formulario_paciente_observation)).setText(patient.getObservacoes());

            Spinner spinnerClinic = activity.findViewById(R.id.formulario_paciente_dados_pessoais_clinica_spinner);
            List<Clinica> allClinicsInOrder = db.clinicaDao().getAllInOrder();
            for (int i = 0; i < allClinicsInOrder.size(); i++) {
                if(allClinicsInOrder.get(i).getId() == patient.getClinicaId()){
                    spinnerClinic.setSelection(i);
                    break;
                }
            }
        });


        onView(withId(R.id.formulario_paciente_activity_fab)).perform(click());

        Thread.sleep(500);

        List<Paciente> byName = db.pacienteDao().findByName(patient.getNomePaciente());
        assertEquals("Esperava id 1 indicando que a clínica foi salva", 1, byName.size());
            Paciente patientSaved = byName.get(0);
            assertNotEquals(patient.getId(), patientSaved.getId());
            assertEquals(patient.getNomePaciente(), patientSaved.getNomePaciente());
            assertEquals(patient.getNascimento(), patientSaved.getNascimento());
            assertEquals(patient.getEmail(), patientSaved.getEmail());
            assertEquals(patient.getTelefone(), patientSaved.getTelefone());
            assertEquals(patient.getGenero(), patientSaved.getGenero());
            assertEquals(patient.getClinicaId(), patientSaved.getClinicaId());
            assertEquals(patient.getObservacoes(), patientSaved.getObservacoes());
    }


    @Test
    public void whenActivityLaunchedWithClinicaIntent_itPopulatesLayout() {
        Paciente paciente = TestEntityFactory.generatePatient();
        db.pacienteDao().insertAll(paciente);
        paciente = db.pacienteDao().findByName(paciente.getNomePaciente()).get(0);
        Antropometria antropometria = createAntropometriaForPatient((int) paciente.getId());
        db.antropometriaDao().insertAll(antropometria);
        Patologia patologia = createPathologyForPatient((int) paciente.getId());
        db.patologiaDao().insert(patologia);


        Intent intent = new Intent(
                ApplicationProvider.getApplicationContext(),
                PatientFormActivity.class
        );
        intent.putExtra(ExtrasActivities.PACIENTE_EXTRA.getKey(), paciente);

        try (ActivityScenario<PatientFormActivity> localScenario = ActivityScenario.launch(intent)) {

            checkIfEditTextIsInsertedPacienteDataCorrectly(localScenario, paciente);
            checkIfEditTextIsInsertedAnthropometryDataCorrectly(localScenario, antropometria);
            localScenario.onActivity(activity -> {
                ViewGroup viewGroup = activity.findViewById(R.id.formulario_paciente_patologia_layout_content);

                assertEquals(12, viewGroup.getChildCount());
                ViewGroup viewOfPathology = (ViewGroup) viewGroup.getChildAt(0);
                TextView etPatologyTitle = viewOfPathology.findViewById(R.id.pop_up_patologia_description_formulario_title);
                EditText etPatology = viewOfPathology.findViewById(R.id.pop_up_patologia_description_formulario_editText);

                assertEquals("PATOLOGIA ATUAL",etPatologyTitle.getText().toString());
                assertEquals(patologia.getPatologiaAtual(), etPatology.getText().toString());

            });
        }
    }

    private Antropometria createAntropometriaForPatient(int patientId) {
        Antropometria antropometria = TestEntityFactory.generateAntropometria("15/08/1990", 1.80, 81);
        antropometria.setIdPaciente(patientId);
        return antropometria;
    }

    private Patologia createPathologyForPatient(int id) {
        Patologia patologia = TestEntityFactory.generatePatologia();
        patologia.setIdPaciente(id);
        return patologia;

    }

    private static void checkIfEditTextIsInsertedPacienteDataCorrectly(ActivityScenario<PatientFormActivity> localScenario,
                                                                       Paciente paciente) {
        localScenario.onActivity(activity -> {
            EditText etName = activity.findViewById(R.id.formulario_paciente_dados_pessoais_name);
            EditText etNasc = activity.findViewById(R.id.formulario_paciente_dados_pessoais_nascimento);
            EditText etEmail = activity.findViewById(R.id.formulario_paciente_dados_pessoais_email);
            EditText etPhone = activity.findViewById(R.id.formulario_paciente_dados_pessoais_fone);
            android.widget.Spinner spinnerGender = activity.findViewById(R.id.formulario_paciente_dados_pessoais_genero_spinner);
            EditText etObservation = activity.findViewById(R.id.formulario_paciente_observation);

            assertEquals(paciente.getNomePaciente(), etName.getText().toString());
            assertEquals(paciente.getNascimento(), etNasc.getText().toString());
            assertEquals(paciente.getEmail(), etEmail.getText().toString());
            assertEquals(paciente.getTelefone(), etPhone.getText().toString());
            if (spinnerGender.getSelectedItem() != null) {
                assertEquals(String.valueOf(paciente.getGenero()), spinnerGender.getSelectedItem().toString());
            }
            assertEquals(paciente.getObservacoes(), etObservation.getText().toString());

        });
    }

    private static void checkIfEditTextIsInsertedAnthropometryDataCorrectly(ActivityScenario<PatientFormActivity> localScenario,
                                                                            Antropometria antropometria) {
        localScenario.onActivity(activity -> {
            EditText etHeight = activity.findViewById(R.id.formulario_paciente_antropometria_altura);
            EditText etWeight = activity.findViewById(R.id.formulario_paciente_antropometria_peso_atual);
            EditText etExpectedWeight = activity.findViewById(R.id.formulario_paciente_antropometria_peso_ideal);
            EditText etRightArm = activity.findViewById(R.id.formulario_paciente_antropometria_circum_braco);
            EditText etRightThigh = activity.findViewById(R.id.formulario_paciente_antropometria_circum_coxa);
            EditText etAbdomen = activity.findViewById(R.id.formulario_paciente_antropometria_circum_abdomen);
            EditText etWaist = activity.findViewById(R.id.formulario_paciente_antropometria_circum_cintura);
            EditText etHip = activity.findViewById(R.id.formulario_paciente_antropometria_circum_quadril);

            assertEquals(antropometria.getAltura(), etHeight.getText().toString());
            assertEquals(antropometria.getPeso(), etWeight.getText().toString());
            assertEquals(antropometria.getPesoIdeal(), etExpectedWeight.getText().toString());
            assertEquals(antropometria.getCircumferenciaBracoDir(), etRightArm.getText().toString());
            assertEquals(antropometria.getCircumferenciaCoxaDir(), etRightThigh.getText().toString());
            assertEquals(antropometria.getCircumferenciaAbdomen(), etAbdomen.getText().toString());
            assertEquals(antropometria.getCircumferenciaCintura(), etWaist.getText().toString());
            assertEquals(antropometria.getCircumferenciaQuadril(), etHip.getText().toString());

        });
    }
    


}
