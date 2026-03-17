package com.example.worknutri.ui.detail.detailPaciente;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.matcher.RootMatchers;

import com.example.worknutri.R;
import com.example.worknutri.calcular.AntropometricCalculator;
import com.example.worknutri.calcular.ClassificacaoImc;
import com.example.worknutri.calcular.StringWithUnitsFormatter;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.support.NavViewInteractionTest;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.support.TextViewAssertions;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.detail.detailClinica.ClinicDescriptionActivity;
import com.example.worknutri.ui.forms.patientForm.PatientFormActivity;
import com.example.worknutri.ui.popUp.pathology.PathologyField;
import com.example.worknutri.ui.popUp.pathology.PathologyFieldMapper;
import com.example.worknutri.util.StringsUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PacienteDescriptionActivityTest {

    private Paciente patient;
    private ActivityScenario<PacienteDescriptionActivity> scenario;
    private AppDataBase db;

    @Before
    public void setUp() {
        db = AppDataBase.getInstance(TestUtil.getThemedContext());
        populateDb();
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), PacienteDescriptionActivity.class);
        intent.putExtra(ExtrasActivities.PACIENTE_EXTRA.getKey(), patient);
        scenario = ActivityScenario.launch(intent);
    }

    private void populateDb() {
        Clinica clinic = TestEntityFactory.generateClinic();
        db.clinicaDao().insertAll(clinic);

        patient = TestEntityFactory.generatePatient();
        patient.setClinicaId(db.clinicaDao().findIdByName(clinic.getNome()));
        db.pacienteDao().insertAll(patient);
        patient = db.pacienteDao().findByName(patient.getNomePaciente()).get(0);

        Antropometria anthropometry = TestEntityFactory.generateAnthropometry("15/08/1990", 1.70, 70);
        anthropometry.setIdPaciente((int) patient.getId());
        db.antropometriaDao().insertAll(anthropometry);

        Patologia pathology = TestEntityFactory.generatePathology();
        pathology.setIdPaciente((int) patient.getId());
        db.patologiaDao().insert(pathology);


    }

    @After
    public void tearDown() {
        // remove paciente and related data
        db.pacienteDao().delete(patient);
        Clinica clinic = db.clinicaDao().getById(patient.getClinicaId());
        db.clinicaDao().delete(clinic);
        if (scenario != null) scenario.moveToState(Lifecycle.State.DESTROYED);
    }

    @Test
    public void whenLaunchedWithPacienteIntentItPopulatesLayout() {

        onView(withId(R.id.paciente_description_activity_name_paciente_descrition))
                .check(TextViewAssertions.matchesText(patient.getNomePaciente()));
        onView(withId(R.id.paciente_description_activity_fone_paciente_descrition))
                .check(TextViewAssertions.matchesText(patient.getTelefone()));
        onView(withId(R.id.paciente_description_activity_email_paciente_descrition))
                .check(TextViewAssertions.matchesText(patient.getEmail()));
        onView(withId(R.id.paciente_description_activity_observation_paciente_descrition))
                .check(TextViewAssertions.matchesText(patient.getObservacoes()));

        int age = AntropometricCalculator.getYearFromDate(patient.getNascimento());
        onView(withId(R.id.paciente_description_activity_idade_paciente_descrition))
                .check(TextViewAssertions.matchesText(String.valueOf(age).concat(" anos")));

        Antropometria anthropometry = db.antropometriaDao().getByPacienteId((int) patient.getId());
        onView(withId(R.id.paciente_description_activity_height_paciente_descrition))
                .check(TextViewAssertions.matchesText(anthropometry.getAltura()));
        onView(withId(R.id.paciente_description_activity_peso_paciente_descrition))
                .check(TextViewAssertions.matchesText(anthropometry.getPeso()));
        onView(withId(R.id.paciente_description_activity_peso_ideal_paciente_descrition))
                .check(TextViewAssertions.matchesText(anthropometry.getPesoIdeal()));
    }

    @Test
    public void onClickInNavEditItemOpensPatientFormActivity() {
        NavViewInteractionTest<PacienteDescriptionActivity> navTest =
                new NavViewInteractionTest<>(R.id.paciente_description_activity_nav_view, scenario);

        navTest.clickInNavItemOpenActivity(R.id.navigation_edit, PatientFormActivity.class);
    }

    @Test
    public void onClickInNavClinicItemOpensClinicDescriptionActivity() {
        NavViewInteractionTest<PacienteDescriptionActivity> navTest =
                new NavViewInteractionTest<>(R.id.paciente_description_activity_nav_view, scenario);

        navTest.clickInNavItemOpenActivity(R.id.navigation_clinic_patient, ClinicDescriptionActivity.class);
    }

    @Test
    public void onClickInNavDeleteButtonShowPopUpToConfirmDeleteAndInConfirmIsRemovedPacienteFromLayout() {

        assertEquals(1, db.pacienteDao().findByName(patient.getNomePaciente()).size());

        scenario.onActivity(activity -> {
            View nav = activity.findViewById(R.id.paciente_description_activity_nav_view);
            if (nav instanceof com.google.android.material.bottomnavigation.BottomNavigationView) {
                ((com.google.android.material.bottomnavigation.BottomNavigationView) nav).getMenu().performIdentifierAction(R.id.navigation_delete, 0);
            }
        });

        TestUtil.waitFor(2000);
        onView(withId(R.id.popup_delete_confirm_layout)).check(matches(isDisplayed()));

        onView(withId(R.id.popup_delete_confirm_layout_confirm_button)).perform(click());

        assertEquals(0, db.pacienteDao().findByName(patient.getNomePaciente()).size());
        assertEquals(0, db.antropometriaDao().getAll().stream().filter(a -> a.getIdPaciente() == patient.getId()).count());
        assertEquals(0, db.patologiaDao().loadAllByIdPaciente((int) patient.getId()).size());

    }

    @Test
    public void onClickInFabButtonHisFinishActivity(){

        onView(withId(R.id.paciente_description_activity_fab_back)).perform(click());
        TestUtil.waitFor(1000);
        assertEquals(Lifecycle.State.DESTROYED, scenario.getState());
    }

    @Test
    public void clickAntropometriaButton_inflatesCompletePopupAndShowsCorrectData() {
        // ensure no popup initially
        onView(withId(R.id.popup_base_layout)).check(doesNotExist());

        // click the antropometria button
        onView(withId(R.id.paciente_description_activity_button_antropometric)).perform(click());

        // wait for popup animation/inflation
        TestUtil.waitFor(500);

        // popup should be displayed as a platform popup
        onView(withId(R.id.popup_base_layout)).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.popup_antropometria_description)).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));

        Antropometria antropometria = db.antropometriaDao().getByPacienteId((int) patient.getId());
        assertNotNull(antropometria);
        verifyCircumferenceIsCorrect(antropometria);

        verifyWeightHeightIsCorrect(antropometria);

        verifyAnthropometryCalcIsCorrect(antropometria);

    }
    private static void verifyCircumferenceIsCorrect(Antropometria antropometria) {
        String stringExpected = StringWithUnitsFormatter.withCm(antropometria.getCircumferenciaBracoDir());
        onView(withId(R.id.paciente_descrition_antropometria_circum_braco)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions.matchesText(stringExpected));

        stringExpected = StringWithUnitsFormatter.withCm(antropometria.getCircumferenciaCoxaDir());
        onView(withId(R.id.paciente_descrition_antropometria_circum_coxa)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions.matchesText(stringExpected));

        stringExpected = StringWithUnitsFormatter.withCm(antropometria.getCircumferenciaQuadril());
        onView(withId(R.id.paciente_descrition_antropometria_circum_quadril)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions.matchesText(stringExpected));

        stringExpected = StringWithUnitsFormatter.withCm(antropometria.getCircumferenciaAbdomen());
        onView(withId(R.id.paciente_descrition_antropometria_circum_abdomen)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions.matchesText(stringExpected));

        stringExpected = StringWithUnitsFormatter.withCm(antropometria.getCircumferenciaCintura());
        onView(withId(R.id.paciente_descrition_antropometria_circum_cintura)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions.matchesText(stringExpected));
    }

    private static void verifyWeightHeightIsCorrect(Antropometria antropometria) {
        onView(withId(R.id.paciente_descrition_antropometria_altura)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions.matchesText(StringWithUnitsFormatter.withMeters(antropometria.getAltura())));
        onView(withId(R.id.paciente_descrition_antropometria_peso_atual)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions.matchesText(StringWithUnitsFormatter.withKg(antropometria.getPeso())));
        onView(withId(R.id.paciente_descrition_antropometria_peso_ideal)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions.matchesText(StringWithUnitsFormatter.withKg(antropometria.getPesoIdeal())));
    }

    private static void verifyAnthropometryCalcIsCorrect(Antropometria antropometria) {
        String expectedImc = StringsUtil.formatDouble(antropometria.getImc());
        onView(withId(R.id.paciente_descrition_antropometria_imc)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions.matchesText(expectedImc));

        ClassificacaoImc classificacaoImcExpected = ClassificacaoImc.tipoImc(Double.parseDouble(expectedImc));
        onView(withId(R.id.classificacao_imc_textview)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions.matchesText(classificacaoImcExpected.name()))
                .check(TextViewAssertions.matchesBackgroundDrawable(classificacaoImcExpected.getColor()));

        onView(withId(R.id.paciente_descrition_antropometria_taxa_metabolica)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions
                        .matchesText(StringWithUnitsFormatter.withKcal(antropometria.getTaxaMetabolica())));

        onView(withId(R.id.paciente_descrition_antropometria_valor_metabolico)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions
                        .matchesText(StringWithUnitsFormatter.withKcal(antropometria.getValorMetabolico())));

        onView(withId(R.id.paciente_descrition_antropometria_bolso)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions
                        .matchesText(StringWithUnitsFormatter.withKcal(antropometria.getRegraBolso())));

        onView(withId(R.id.paciente_descrition_antropometria_venta)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions
                        .matchesText(StringWithUnitsFormatter.withKcalDia(antropometria.getVenta())));

        onView(withId(R.id.paciente_descrition_antropometria_agua)).inRoot(RootMatchers.isPlatformPopup())
                .check(TextViewAssertions
                        .matchesText(StringWithUnitsFormatter.withMl(antropometria.getAgua())));
    }

    @Test
    public void clickPatologiaButtonInflatesPopupAndShowsCorrectData() {
        // ensure no popup initially
        onView(withId(R.id.popup_base_layout)).check(doesNotExist());

        // click the patologia button
        onView(withId(R.id.paciente_description_activity_button_patologic)).perform(click());

        // wait for popup animation/inflation
        TestUtil.waitFor(500);

        // popup should be displayed as a platform popup
        onView(withId(R.id.popup_base_layout)).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.popup_base_layout_layout_intern)).inRoot(RootMatchers.isPlatformPopup())
                        .check(((view, noViewFoundException) -> {
                            ViewGroup viewGroup = (ViewGroup) view;
                            assertEquals(12, viewGroup.getChildCount()); // 12 TextViews for pathologies
                            Patologia pathology = db.patologiaDao().loadAllByIdPaciente((int) patient.getId()).get(0);
                            assertNotNull(pathology);

                            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                                ViewGroup pathologyView = (ViewGroup) viewGroup.getChildAt(i);
                                TextView pathologyType = pathologyView.findViewById(R.id.popup_paciente_descrition_patologia_textview_title);
                                String stringExpected = getPahologyExpectedString(pathologyType.getText().toString(),pathology);
                                TextView pathologyDescription = pathologyView.findViewById(R.id.popup_paciente_descrition_patologia_textview_description);
                                assertEquals(stringExpected, pathologyDescription.getText().toString());
                            }
                        }));
    }

    private String getPahologyExpectedString(String pathologyType, Patologia pathology) {
        PathologyField field = PathologyField.from(pathologyType);
        PathologyFieldMapper mapper = new PathologyFieldMapper(field);
        String stringExpected = mapper.getValue(pathology);
        if (stringExpected == null || stringExpected.isBlank()) stringExpected = "Não Informado";
        return stringExpected;
    }

}
