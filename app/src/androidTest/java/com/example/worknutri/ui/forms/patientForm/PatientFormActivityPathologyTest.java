package com.example.worknutri.ui.forms.patientForm;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.R;
import com.example.worknutri.ui.formularios.formularioPaciente.PatientFormActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PatientFormActivityPathologyTest {

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
    public void clickInAddPatologyOpenPathologyAddPopUp() throws InterruptedException {
        onView(withId(R.id.popup_base_layout)).check(doesNotExist());

        scenario.onActivity(activity -> activity.findViewById(
                R.id.formulario_paciente_patologia_button_add).performClick());

        Thread.sleep(300);

        onView(withId(R.id.popup_base_layout)).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.popup_patologia_add)).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));

    }

    @Test
    public void inPatologyAddPopUpWhenClickInAddGenerateOneViewWithCorrectlyData() throws InterruptedException {

        String pathologyTitleExpected = "MEDICAÇÃO";
        String pathologyTextExpected = "Medicacao teste";

        insertNewPathology(pathologyTitleExpected, pathologyTextExpected);

        Thread.sleep(300);
        onView(withId(R.id.popup_base_layout)).check(doesNotExist());
        scenario.onActivity(activity -> {
            ViewGroup viewGroup = activity.findViewById(R.id.formulario_paciente_patologia_layout_content);

            assertEquals(1, viewGroup.getChildCount());
            ViewGroup viewOfPathology = (ViewGroup) viewGroup.getChildAt(0);
            TextView etPatologyTitle = viewOfPathology.findViewById(R.id.pop_up_patologia_description_formulario_title);
            EditText etPatology = viewOfPathology.findViewById(R.id.pop_up_patologia_description_formulario_editText);

            assertEquals(pathologyTitleExpected,etPatologyTitle.getText().toString());
            assertEquals(pathologyTextExpected, etPatology.getText().toString());

        });
    }

    @Test
    public void verifyIfOnePathologyTypeInsertedInLayoutOnlyAppearsToAddAgainIfHeGoesRemovedFromLayout() throws InterruptedException {

        String pathologyTypeExpected = "ALERGIA";


        insertNewPathology(pathologyTypeExpected, "");

        Thread.sleep(300);
        onView(withId(R.id.popup_base_layout)).check(doesNotExist());
        scenario.onActivity(activity -> {
            ViewGroup viewGroup = activity.findViewById(R.id.formulario_paciente_patologia_layout_content);

            assertEquals(1, viewGroup.getChildCount());
            ViewGroup viewOfPathology = (ViewGroup) viewGroup.getChildAt(0);
            TextView etPatologyTitle = viewOfPathology.findViewById(R.id.pop_up_patologia_description_formulario_title);
            assertEquals(pathologyTypeExpected,etPatologyTitle.getText().toString());

        });

        // check if pathology type not appears in spinner
        scenario.onActivity(activity -> activity.findViewById(
                R.id.formulario_paciente_patologia_button_add).performClick());
        Thread.sleep(300);
        onView(withId(R.id.popup_patologia_add_spinner)).perform(click());
        onView(withText(pathologyTypeExpected))
                .inRoot(RootMatchers.isPlatformPopup())
                .check(doesNotExist());
        Thread.sleep(300);

        //close popup
        Espresso.pressBack();
        Espresso.pressBack();
        Thread.sleep(300);

        // check if pathology type appears in spinner after remove pathology from layout
        scenario.onActivity(activity -> {
            activity.findViewById(R.id.pop_up_patologia_description_formulario_button_delete).performClick();
            activity.findViewById(
                    R.id.formulario_paciente_patologia_button_add).performClick();
        });
        Thread.sleep(300);
        onView(withId(R.id.popup_patologia_add)).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.popup_patologia_add_spinner)).perform(click());
        onData(is(pathologyTypeExpected))
                .inRoot(RootMatchers.isPlatformPopup())
                .check(matches(isDisplayed()));

    }

    private void insertNewPathology(String pathologyType, String pathologyText) throws InterruptedException {
        scenario.onActivity(activity -> activity.findViewById(
                R.id.formulario_paciente_patologia_button_add).performClick());

        Thread.sleep(300);
        onView(withId(R.id.popup_patologia_add)).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));

        onView(withId(R.id.popup_patologia_add_spinner)).perform(click());
        onData(is(pathologyType))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
        Thread.sleep(300);
        onView(withId(R.id.popup_patologia_add_multiAutoComplete)).inRoot(RootMatchers.isPlatformPopup())
                .perform(replaceText(pathologyText));
        onView(withId(R.id.popup_patologia_add_button)).inRoot(RootMatchers.isPlatformPopup()).perform(click());
    }


}
