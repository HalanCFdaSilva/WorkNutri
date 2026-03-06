package com.example.worknutri.ui.detail.detailClinica;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.support.NavViewInteractionTest;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.forms.clinicForm.ClinicFormActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ClinicDescriptionActivityTest {

    private Clinica clinic;
    private ActivityScenario<ClinicDescriptionActivity> scenario;
    private AppDataBase db;

    @Before
    public void setUp() {
        db = AppDataBase.getInstance(TestUtil.getThemedContext());
        populateInDb();
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ClinicDescriptionActivity.class);
        intent.putExtra(ExtrasActivities.CLINICA_EXTRA.getKey(), clinic);
        scenario = ActivityScenario.launch(intent);
    }

    private void populateInDb() {
        clinic = TestEntityFactory.generateClinic();
        clinic.setId(1234);
        db.clinicaDao().insertAll(clinic);
        for (int i = 0; i < 2; i++) {
            DayOfWork dayOfWork = TestEntityFactory.generateRandomicDayOfWork(clinic.getId());
            db.dayOfWorkDao().insert(dayOfWork);
        }
    }

    @After
    public void tearDown() {
        db.clinicaDao().delete(clinic);
        scenario.moveToState(Lifecycle.State.DESTROYED);
    }


    @Test
    public void whenLaunchedWithClinicIntentItPopulatesLayout() {
        scenario.onActivity(activity -> {
            // verify a few representative fields populated by the adapter
            assertEquals(clinic.getNome(), ((android.widget.TextView) activity.findViewById(R.id.clinica_description_activity_dados_gerais_name)).getText().toString());
            assertEquals(clinic.getTelefone1(), ((android.widget.TextView) activity.findViewById(R.id.clinica_description_activity_dados_gerais_fone)).getText().toString());
            assertEquals(clinic.getEmail(), ((android.widget.TextView) activity.findViewById(R.id.clinica_description_activity_dados_gerais_email)).getText().toString());
            assertEquals(clinic.getRua(), ((android.widget.TextView) activity.findViewById(R.id.clinica_description_activity_endereco_rua)).getText().toString());
            assertEquals(String.valueOf(clinic.getNumero()), ((android.widget.TextView) activity.findViewById(R.id.clinica_description_activity_endereco_numero)).getText().toString());
            assertEquals(clinic.getComplemento(), ((android.widget.TextView) activity.findViewById(R.id.clinica_description_activity_endereco_complemento)).getText().toString());
            assertEquals(clinic.getBairro(), ((android.widget.TextView) activity.findViewById(R.id.clinica_description_activity_endereco_bairro)).getText().toString());
            assertEquals(clinic.getCidade(), ((android.widget.TextView) activity.findViewById(R.id.clinica_description_activity_endereco_cidade)).getText().toString());
            assertEquals(clinic.getEstado(), ((android.widget.TextView) activity.findViewById(R.id.clinica_description_activity_endereco_estado)).getText().toString());

            ViewGroup viewContainedDaysOfWork = activity.findViewById(R.id.clinica_description_activity_horario_atendimento_layout);
            assertEquals(2, viewContainedDaysOfWork.getChildCount());
            List<DayOfWork> daysOfWork = db.dayOfWorkDao().getDaysforClinicaId(clinic.getId());
            for (int i = 0; i < daysOfWork.size(); i++) {
                DayOfWork dayOfWork = daysOfWork.get(i);
                View dayOfWorkView = viewContainedDaysOfWork.getChildAt(i);
                assertEquals(dayOfWork.getDayOfWeek(), ((android.widget.TextView) dayOfWorkView.
                        findViewById(R.id.time_descrition_fragment_textView_day_of_week)).getText().toString());
                assertEquals(dayOfWork.getHoraInicio(), ((android.widget.TextView) dayOfWorkView.
                        findViewById(R.id.time_descrition_fragment_textView_hour_begin)).getText().toString());
                assertEquals(dayOfWork.getHoraFim(), ((android.widget.TextView) dayOfWorkView.
                        findViewById(R.id.time_descrition_fragment_textView_hour_end)).getText().toString());
            }

        });
    }

    @Test
    public void onClickInNavEditItemHisOpenAClinicFormActivity() {
        NavViewInteractionTest<ClinicDescriptionActivity> navViewInteractionTest =
                new NavViewInteractionTest<>(R.id.clinica_description_activity_nav_view,scenario);
        navViewInteractionTest.clickInNavItemOpenActivity(R.id.navigation_edit, ClinicFormActivity.class);
    }

    @Test
    public void onClickInNavDeleteButtonShowPopUpToConfirmDeleteAndInConfirmIsRemovedClinicFromLayout() {

        List<Clinica> byName = db.clinicaDao().findByName(clinic.getNome());
        assertEquals(1, byName.size());
        List<DayOfWork> daysforClinicaId = db.dayOfWorkDao().getDaysforClinicaId(clinic.getId());
        assertEquals(2, daysforClinicaId.size());

        // trigger delete and assert popup inflated
        scenario.onActivity(activity -> {
            android.view.View nav = activity.findViewById(R.id.clinica_description_activity_nav_view);
            if (nav instanceof BottomNavigationView) {
                ((BottomNavigationView) nav).getMenu().performIdentifierAction(R.id.navigation_delete, 0);
            }
        });

        // wait until popup view is present or fail after timeout
        TestUtil.waitFor(2000);
        onView(withId(R.id.popup_delete_confirm_layout)).check(matches(isDisplayed()));

        onView(withId(R.id.popup_delete_confirm_layout_confirm_button)).perform(click());

        byName = db.clinicaDao().findByName(clinic.getNome());
        assertEquals(0, byName.size());
        daysforClinicaId = db.dayOfWorkDao().getDaysforClinicaId(clinic.getId());
        assertEquals(0, daysforClinicaId.size());
    }


    @Test
    public void onClickInFabButtonHisFinishActivity(){

        onView(withId(R.id.clinica_description_activity_fab_back)).perform(click());
        TestUtil.waitFor(1000);
        assertEquals(Lifecycle.State.DESTROYED, scenario.getState());
    }



}
