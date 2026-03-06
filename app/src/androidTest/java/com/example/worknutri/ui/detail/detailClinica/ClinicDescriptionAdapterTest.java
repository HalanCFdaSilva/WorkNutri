package com.example.worknutri.ui.detail.detailClinica;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;



import com.example.worknutri.R;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.support.NavViewInteractionTest;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.ActivityToTest;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.forms.clinicForm.ClinicFormActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ClinicDescriptionAdapterTest {

    private Context context;

    private Clinica clinic;
    private ClinicDescriptionAdapter adapter;

    @Before
    public void setUp() {
        context = TestUtil.getThemedContext();
        clinic = TestEntityFactory.generateClinic();
        clinic.setId(2);
        Intent intent = getIntentWithClinicaExtra(clinic);
        adapter = new ClinicDescriptionAdapter(intent, context);
    }

    @NonNull
    private Intent getIntentWithClinicaExtra(Clinica clinic) {
        Intent intent = new Intent(context, ActivityToTest.class);
        intent.putExtra(ExtrasActivities.CLINICA_EXTRA.getKey(), clinic);
        return intent;
    }


    @Test
    public void whenOnCreateAdapterTheActivityNotHasExtraCLINICA_EXTRATheyFinishActivity() {
        Intent intent = new Intent(context, ActivityToTest.class);
        checkIfAdapterCloseOrNotActivity(intent,Lifecycle.State.DESTROYED);
        intent.putExtra(ExtrasActivities.SCHEDULE_EXTRA.getKey(), true);
        checkIfAdapterCloseOrNotActivity(intent,Lifecycle.State.DESTROYED);
        intent.putExtra(ExtrasActivities.PACIENTE_EXTRA.getKey(), true);
        checkIfAdapterCloseOrNotActivity(intent,Lifecycle.State.DESTROYED);
        Clinica clinic = TestEntityFactory.generateClinic();
        clinic.setId(2);
        intent.putExtra(ExtrasActivities.CLINICA_EXTRA.getKey(), clinic);
        checkIfAdapterCloseOrNotActivity(intent,Lifecycle.State.RESUMED);

    }

    private static void checkIfAdapterCloseOrNotActivity(Intent intent, Lifecycle.State stateExpected) {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(intent);

        scenario.onActivity(activity ->
                new ClinicDescriptionAdapter(activity.getIntent(), activity));
        TestUtil.waitFor(500);

        assertEquals(stateExpected, scenario.getState());
    }

    @Test
    public void whenInsertClinicInLayoutTheDadosGeraisAndEnderecoAreFilled() {
        ViewGroup viewGroup = inflateViewGroup();
        adapter.insertClinicInLayout(viewGroup);

        assertTextViewTextEqualString(clinic.getNome(),
                viewGroup.findViewById(R.id.clinica_description_activity_dados_gerais_name));
        assertTextViewTextEqualString(clinic.getTelefone1(),
                viewGroup.findViewById(R.id.clinica_description_activity_dados_gerais_fone));
        assertTextViewTextEqualString(clinic.getEmail(),
                viewGroup.findViewById(R.id.clinica_description_activity_dados_gerais_email));
        assertTextViewTextEqualString(clinic.getRua(),
                viewGroup.findViewById(R.id.clinica_description_activity_endereco_rua));
        assertTextViewTextEqualString(clinic.getCodigoPostal(),
                viewGroup.findViewById(R.id.clinica_description_activity_endereco_cep));
        assertTextViewTextEqualString(String.valueOf(clinic.getNumero()),
                viewGroup.findViewById(R.id.clinica_description_activity_endereco_numero));
        assertTextViewTextEqualString(clinic.getComplemento(),
                viewGroup.findViewById(R.id.clinica_description_activity_endereco_complemento));
        assertTextViewTextEqualString(clinic.getCidade(),
                viewGroup.findViewById(R.id.clinica_description_activity_endereco_cidade));
        assertTextViewTextEqualString(clinic.getBairro(),
                viewGroup.findViewById(R.id.clinica_description_activity_endereco_bairro));
        assertTextViewTextEqualString(clinic.getEstado(),
                viewGroup.findViewById(R.id.clinica_description_activity_endereco_estado));



    }

    private ViewGroup inflateViewGroup() {
        return (ViewGroup) LayoutInflater.from(context).inflate(R.layout.clinica_description, new LinearLayout(context));
    }

    private void assertTextViewTextEqualString(String stringExpected,TextView textView) {
        assertEquals(stringExpected, textView.getText().toString());
    }

    @Test
    public void insertDaysOfWorkInLayout_insertsHourDateFragmentsFromDatabase() {
        DayOfWork dayOfWorkExpected = TestEntityFactory.generateRandomicDayOfWork(clinic.getId());
        insertAllInDB(dayOfWorkExpected);
        LinearLayout viewToInsertDayOfWork = new LinearLayout(context);
        adapter.insertDaysOfWorkInLayout(viewToInsertDayOfWork);
        assertIfDayOfWorkInsertedCorrectly(viewToInsertDayOfWork, dayOfWorkExpected);

    }
    private void insertAllInDB(DayOfWork dayOfWork) {
        AppDataBase memoryDb = AppDataBase.getInstance(context);
        memoryDb.clinicaDao().insertAll(clinic);
        memoryDb.dayOfWorkDao().insert(dayOfWork);
    }

    private static void assertIfDayOfWorkInsertedCorrectly(ViewGroup viewWereDayOfWorkHasInserted, DayOfWork dayOfWorkExpected) {
        assertEquals(1, viewWereDayOfWorkHasInserted.getChildCount());
        ViewGroup layoutInserted = (ViewGroup) viewWereDayOfWorkHasInserted.getChildAt(0);
        assertEquals(R.id.time_description_fragment, layoutInserted.getId());
        TextView dayOfWeek = layoutInserted.findViewById(R.id.time_descrition_fragment_textView_day_of_week);
        TextView beginHour = layoutInserted.findViewById(R.id.time_descrition_fragment_textView_hour_begin);
        TextView endHour = layoutInserted.findViewById(R.id.time_descrition_fragment_textView_hour_end);
        assertNotNull(dayOfWeek);
        assertNotNull(beginHour);
        assertNotNull(endHour);
        assertEquals(dayOfWorkExpected.getDayOfWeek(), dayOfWeek.getText().toString());
        assertEquals(dayOfWorkExpected.getHoraInicio(), beginHour.getText().toString());
        assertEquals(dayOfWorkExpected.getHoraFim(), endHour.getText().toString());
    }

    @Test
    public void configureNavButtonModifyCorrectlyBottomNavView() throws InterruptedException {

        ActivityScenario<ActivityToTest> scenario = createScenarioWithBottomNavViewConfigured();

        checkNavegationEditIsConfigured(scenario);

        checkNavegationDeleteHasConfigured(scenario);

    }

    @NonNull
    private ActivityScenario<ActivityToTest> createScenarioWithBottomNavViewConfigured() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(getIntentWithClinicaExtra(clinic));
        scenario.onActivity(activity -> {
            ClinicDescriptionAdapter adapterLocal = new ClinicDescriptionAdapter(activity.getIntent(), activity);
            BottomNavigationView bottomView = new BottomNavigationView(activity);
            bottomView.setId(12);
            bottomView.inflateMenu(R.menu.bottom_nav_menu_clinica_detail);
            adapterLocal.configureNavButton(bottomView,activity.findViewById(R.id.layout_test));
            ViewGroup layout = activity.findViewById(R.id.layout_test);
            layout.addView(bottomView);
        });
        return scenario;
    }

    private void checkNavegationEditIsConfigured(ActivityScenario<ActivityToTest> scenario) {
        NavViewInteractionTest<ActivityToTest> navViewTester = new NavViewInteractionTest<>(12, scenario);
        navViewTester.clickInNavItemOpenActivity(R.id.navigation_edit, ClinicFormActivity.class);

    }

    private void checkNavegationDeleteHasConfigured(ActivityScenario<ActivityToTest> scenario) throws InterruptedException {
        scenario.onActivity(activity -> ((BottomNavigationView)activity.findViewById(12))
                .getMenu().performIdentifierAction(R.id.navigation_delete, 0));
        Thread.sleep(500);
        onView(withId(R.id.popup_base_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.popup_delete_confirm_layout)).check(matches(isDisplayed()));
    }


}
