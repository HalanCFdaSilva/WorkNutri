package com.example.worknutri.ui.forms.clinicForm;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.worknutri.support.ViewUtil.assertEditTextCorrectFormatText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.support.TextViewAssertions;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class ClinicFormActivityTest {

    private ActivityScenario<ClinicFormActivity> scenario;
    private AppDataBase db;


    @Before
    public void setUp() {

        scenario = ActivityScenario.launch(ClinicFormActivity.class);
        db = AppDataBase.getInstance(ApplicationProvider.getApplicationContext());
    }

    @After
    public void tearDown() {

        if (scenario != null) {
            scenario.close();
        }
    }

    @Test
    public void whenActivityLaunchedWithClinicaIntent_itPopulatesLayout() {
        Clinica clinica = createClinic();

        Intent intent = new Intent(
                ApplicationProvider.getApplicationContext(),
                ClinicFormActivity.class
        );
        intent.putExtra(com.example.worknutri.ui.ExtrasActivities.CLINICA_EXTRA.getKey(), clinica);

        try (ActivityScenario<ClinicFormActivity> localScenario = ActivityScenario.launch(intent)) {
            checkIfEditTextIsInsertedCorrectly(localScenario, clinica);
        }
    }

    @Test
    public void whenActivityLaunchedWithClinicaIntentAndContainDayOfWorkInDb_itPopulatesLayout(){
        Clinica clinica = createClinic();
        DayOfWork dayOfWork = new DayOfWork();
        dayOfWork.setIdClinica(clinica.getId());
        dayOfWork.setDayOfWeek("SEGUNDA");
        dayOfWork.setHoraInicio("09:00");
        dayOfWork.setHoraFim("18:00");
        db.dayOfWorkDao().insert(dayOfWork);

        Intent intent = new Intent(
                ApplicationProvider.getApplicationContext(),
                ClinicFormActivity.class
        );
        intent.putExtra(com.example.worknutri.ui.ExtrasActivities.CLINICA_EXTRA.getKey(), clinica);

        try (ActivityScenario<ClinicFormActivity> localScenario = ActivityScenario.launch(intent)) {
            checkIfEditTextIsInsertedCorrectly(localScenario, clinica);
            localScenario.onActivity(activity -> {
                ViewGroup layout = activity.findViewById(R.id.clinic_form_horario_atendimento_layout);
                Assert.assertEquals(1, layout.getChildCount());
                ViewGroup viewOfDayOfWork = (ViewGroup) layout.getChildAt(0);
                assertEquals(R.id.time_description_fragment, viewOfDayOfWork.getId());
                TextView tvDayOfWeek = viewOfDayOfWork.findViewById(R.id.time_descrition_fragment_textView_day_of_week);
                assertEquals(dayOfWork.getDayOfWeek(), tvDayOfWeek.getText().toString());
                TextView tvHourBegin = viewOfDayOfWork.findViewById(R.id.time_descrition_fragment_textView_hour_begin);
                assertEquals(dayOfWork.getHoraInicio(), tvHourBegin.getText().toString());
                TextView tvHourEnd = viewOfDayOfWork.findViewById(R.id.time_descrition_fragment_textView_hour_end);
                assertEquals(dayOfWork.getHoraFim(), tvHourEnd.getText().toString());

            });
        }
    }

    private static void checkIfEditTextIsInsertedCorrectly(ActivityScenario<ClinicFormActivity> localScenario, Clinica clinica) {
        localScenario.onActivity(activity -> {
            EditText etName = activity.findViewById(R.id.clinic_form_general_data_name);
            EditText etEmail = activity.findViewById(R.id.clinic_form_general_data_email);
            EditText etPhone = activity.findViewById(R.id.clinic_form_general_data_phone);
            EditText etRua = activity.findViewById(R.id.clinic_form_address_street);
            EditText etNumero = activity.findViewById(R.id.clinic_form_address_number);
            EditText etComplemento = activity.findViewById(R.id.clinic_form_address_complement);
            EditText etCep = activity.findViewById(R.id.clinic_form_address_zip_postal);
            EditText etBairro = activity.findViewById(R.id.clinic_form_address_neighborhood);
            EditText etCidade = activity.findViewById(R.id.clinic_form_address_city);
            android.widget.Spinner spinnerEstado = activity.findViewById(R.id.clinic_form_address_state_spinner);

            assertEquals(clinica.getNome(), etName.getText().toString());
            assertEquals(clinica.getEmail(), etEmail.getText().toString());
            assertEquals(clinica.getTelefone1(), etPhone.getText().toString());
            assertEquals(clinica.getRua(), etRua.getText().toString());
            assertEquals(String.valueOf(clinica.getNumero()), etNumero.getText().toString());
            assertEquals(clinica.getComplemento(), etComplemento.getText().toString());
            assertEquals(clinica.getCodigoPostal(), etCep.getText().toString());
            assertEquals(clinica.getBairro(), etBairro.getText().toString());
            assertEquals(clinica.getCidade(), etCidade.getText().toString());
            // Verifica seleção do Spinner pelo texto do item selecionado
            if (spinnerEstado.getSelectedItem() != null) {
                assertEquals(clinica.getEstado(), spinnerEstado.getSelectedItem().toString());
            }
        });
    }

    private Clinica createClinic() {
        Clinica clinica = new Clinica();
        clinica.setId(1000);
        clinica.setNome("Clinica Intent Test");
        clinica.setEmail("intent@exemplo.com");
        clinica.setTelefone1("11999999999");
        clinica.setRua("Rua Intent");
        clinica.setNumero(45);
        clinica.setComplemento("Apto 1");
        clinica.setCodigoPostal("12345000");
        clinica.setBairro("Bairro Intent");
        clinica.setCidade("Cidade Intent");
        clinica.setEstado("SP");
        return clinica;
    }


    @Test
    public void SavaDataInDataBankIfFormValidate() {
        // Preenche campos obrigatórios (nomes de ids conforme layout)
        insertMinimunValues();
        onView(withId(R.id.clinic_form_general_data_phone)).perform(replaceText("11999999999"));
        onView(withId(R.id.clinic_form_general_data_email)).perform(replaceText("teste@exemplo.com"));

        onView(withId(R.id.clinic_form_fab)).perform(click());

        long id = db.clinicaDao().findIdByName("Clinica Teste");

        assertTrue("Esperava id maior que 0 indicando que a clinica foi salva", id > 0);
    }

    @Test
    public void fabSavesAndFinishesActivity() throws Exception {

        String clinicNameExpected = "Clinica Teste Finish";
        String streetExpected = "Rua Teste";
        String numberExpected = "123";
        String neighborhoodExpected = "Bairro Teste";
        String cityExpected = "Cidade Teste";
        String phoneExpected = "11999999999";
        String emailExpected = "teste@exemplo.com";

        onView(withId(R.id.clinic_form_general_data_name)).perform(replaceText(clinicNameExpected));
        onView(withId(R.id.clinic_form_address_street)).perform(replaceText(streetExpected));
        onView(withId(R.id.clinic_form_address_number)).perform(replaceText(numberExpected));
        onView(withId(R.id.clinic_form_address_neighborhood)).perform(replaceText(neighborhoodExpected));
        onView(withId(R.id.clinic_form_address_city)).perform(replaceText(cityExpected));
        onView(withId(R.id.clinic_form_general_data_phone)).perform(replaceText(phoneExpected));
        onView(withId(R.id.clinic_form_general_data_email)).perform(replaceText(emailExpected));

        onView(withId(R.id.clinic_form_fab)).perform(click());
        Thread.sleep(300);

        List<Clinica> clinicas = db.clinicaDao().findByName(clinicNameExpected);
        assertEquals("Esperava encontrar a clinica salva no banco de dados", 1, clinicas.size());
        Clinica clinica = clinicas.get(0);
        assertTrue("Esperava id maior que 0 indicando que a clinica foi salva", clinica.getId() > 0);
        assertEquals(clinicNameExpected, clinica.getNome());
        assertEquals(streetExpected, clinica.getRua());
        assertEquals(Integer.parseInt(numberExpected), clinica.getNumero());
        assertEquals(neighborhoodExpected, clinica.getBairro());
        assertEquals(cityExpected, clinica.getCidade());
        assertEquals(phoneExpected, clinica.getTelefone1());
        assertEquals(emailExpected, clinica.getEmail());


        try {
            scenario.onActivity(activity -> org.junit.Assert.assertTrue(activity.isFinishing()));
        } catch (IllegalStateException | NullPointerException ignored) {
            // Activity já finalizada - comportamento aceitável para este teste
        }
    }

    @Test
    public void SaveDataInDataBankIfOnlyMandatoryFieldIsFilled() {
        // Preenche campos obrigatórios (nomes de ids conforme layout)
        insertMinimunValues();

        onView(withId(R.id.clinic_form_fab)).perform(click());

        long id = db.clinicaDao().findIdByName("Clinica Teste");

        assertTrue("Esperava id maior que 0 indicando que a clinica foi salva", id > 0);
    }

    @Test
    public void SaveDataInDataBankIfOnlyPhoneFieldIsEmpty() {
        // Preenche campos obrigatórios (nomes de ids conforme layout)
        insertMinimunValues();
        onView(withId(R.id.clinic_form_general_data_email)).perform(replaceText("teste@exemplo.com"));

        // Clica no FAB para salvar
        onView(withId(R.id.clinic_form_fab)).perform(click());


        long id = db.clinicaDao().findIdByName("Clinica Teste");

        assertTrue("Esperava id maior que 0 indicando que a clinica foi salva", id > 0);
    }

    @Test
    public void SaveDataInDataBankIfOnlyEmailFieldIsEmpty() {
        // Preenche campos obrigatórios (nomes de ids conforme layout)
        insertMinimunValues();
        onView(withId(R.id.clinic_form_general_data_phone)).perform(replaceText("11999999999"));

        // Clica no FAB para salvar
        onView(withId(R.id.clinic_form_fab)).perform(click());

        long id = db.clinicaDao().findIdByName("Clinica Teste");

        assertTrue("Esperava id maior que 0 indicando que a clinica foi salva", id > 0);
    }

    @Test
    public void dontSaveInDataBankAndShowFieldsIfMandatoryFieldIsEmpty() throws Exception {

        List<Clinica> clinicaListBefore = db.clinicaDao().getAll();

        onView(withId(R.id.clinic_form_general_data_name_mandatory)).check(TextViewAssertions.matchesVisibility(View.INVISIBLE));
        onView(withId(R.id.clinic_form_address_street_mandatory)).check(TextViewAssertions.matchesVisibility(View.INVISIBLE));
        onView(withId(R.id.clinic_form_address_number_mandatory)).check(TextViewAssertions.matchesVisibility(View.INVISIBLE));
        onView(withId(R.id.clinic_form_address_neighborhood_mandatory)).check(TextViewAssertions.matchesVisibility(View.INVISIBLE));
        onView(withId(R.id.clinic_form_address_city_mandatory)).check(TextViewAssertions.matchesVisibility(View.INVISIBLE));
        onView(withId(R.id.clinic_form_error)).check(TextViewAssertions.matchesVisibility(View.INVISIBLE));

        onView(withId(R.id.clinic_form_fab)).perform(click());
        // aguarda pequena margem para qualquer operação assíncrona (ajuste se necessário)
        Thread.sleep(500);

        verifyRequiredFieldErrorDisplayed(R.id.clinic_form_general_data_name_mandatory);
        onView(withId(R.id.clinic_form_general_data_name))
                .check(TextViewAssertions.matchesHintColor(TestUtil.colorFromRes(R.color.obrigatorio)));
        verifyRequiredFieldErrorDisplayed(R.id.clinic_form_address_street_mandatory);
        onView(withId(R.id.clinic_form_address_street))
                .check(TextViewAssertions.matchesHintColor(TestUtil.colorFromRes(R.color.obrigatorio)));
        verifyRequiredFieldErrorDisplayed(R.id.clinic_form_address_number_mandatory);
        onView(withId(R.id.clinic_form_address_number))
                .check(TextViewAssertions.matchesHintColor(TestUtil.colorFromRes(R.color.obrigatorio)));
        verifyRequiredFieldErrorDisplayed(R.id.clinic_form_address_neighborhood_mandatory);
        onView(withId(R.id.clinic_form_address_neighborhood))
                .check(TextViewAssertions.matchesHintColor(TestUtil.colorFromRes(R.color.obrigatorio)));
        verifyRequiredFieldErrorDisplayed(R.id.clinic_form_address_city_mandatory);
        onView(withId(R.id.clinic_form_address_city))
                .check(TextViewAssertions.matchesHintColor(TestUtil.colorFromRes(R.color.obrigatorio)));

        onView(withId(R.id.clinic_form_error)).check(TextViewAssertions.matchesVisibility(View.VISIBLE));
        onView(withId(R.id.clinic_form_error)).check(TextViewAssertions.matchesText(ApplicationProvider.getApplicationContext().getString(R.string.error_blank)));

        List<Clinica> clinicaListAfter = db.clinicaDao().getAll();
        assertEquals("Esperava id 0 indicando que a clínica NÃO foi salva", clinicaListBefore.size(), clinicaListAfter.size());

    }
    private static void verifyRequiredFieldErrorDisplayed(int fieldId) {
        int expectedColor = TestUtil.colorFromRes(R.color.obrigatorio);
        String expectedText = ApplicationProvider.getApplicationContext().getString(R.string.obrigatorio);
        onView(withId(fieldId)).check(TextViewAssertions.matchesTextColor(expectedColor));
        onView(withId(fieldId)).check(TextViewAssertions.matchesText(expectedText));
        onView(withId(fieldId)).check(TextViewAssertions.matchesVisibility(View.VISIBLE));
    }

    @Test
    public void dontSaveInDataBankAndShowFieldsIfIncorrectedFilledPhoneField() throws Exception {
        checkFieldCorrectlyShowError(R.id.clinic_form_general_data_phone,R.string.error_fone);
    }

    @Test
    public void dontSaveInDataBankAndShowFieldsIfIncorrectedFilledEmailField() throws Exception {
        checkFieldCorrectlyShowError(R.id.clinic_form_general_data_email,R.string.error_email);
    }
    private void checkFieldCorrectlyShowError(int idEdittextToCheck,int idStringError) throws InterruptedException {

        List<Clinica> clinicaListBefore = db.clinicaDao().getAll();

        insertMinimunValues();

        onView(withId(idEdittextToCheck)).perform(replaceText("22"));

        onView(withId(R.id.clinic_form_fab)).perform(click());
        // aguarda pequena margem para qualquer operação assíncrona (ajuste se necessário)
        Thread.sleep(500);

        onView(withId(idEdittextToCheck))
                .check(TextViewAssertions.matchesTextColor(TestUtil.colorFromRes(R.color.obrigatorio)));
        onView(withId(R.id.clinic_form_error))
                .check(TextViewAssertions.matchesVisibility(View.VISIBLE))
                .check(TextViewAssertions.matchesText(TestUtil.getStringFromRes(idStringError)));

        List<Clinica> clinicaListAfter = db.clinicaDao().getAll();
        assertEquals("Esperava id 0 indicando que a clínica NÃO foi salva", clinicaListBefore.size(), clinicaListAfter.size());


    }

    private void insertMinimunValues() {
        onView(withId(R.id.clinic_form_general_data_name)).perform(replaceText("Clinica Teste"));
        onView(withId(R.id.clinic_form_address_street)).perform(replaceText("Rua Teste"));
        onView(withId(R.id.clinic_form_address_number)).perform(replaceText("123"));
        onView(withId(R.id.clinic_form_address_neighborhood)).perform(replaceText("Bairro Teste"));
        onView(withId(R.id.clinic_form_address_city)).perform(replaceText("Cidade Teste"));
    }




    @Test
    public void phoneFieldFormatTextCorrectely() {

        scenario.onActivity(activity -> {
            EditText et = activity.findViewById(R.id.clinic_form_general_data_phone);
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
    public void cepFieldFormatTextCorrectely() {

        scenario.onActivity(activity -> {
            EditText et = activity.findViewById(R.id.clinic_form_address_zip_postal);
            assertEquals(InputType.TYPE_CLASS_NUMBER,et.getInputType());
            assertEditTextCorrectFormatText(et, "1", "10");
            assertEditTextCorrectFormatText(et, "123", "1230");
            assertEditTextCorrectFormatText(et, "12345", "12345-0");
            assertEditTextCorrectFormatText(et, "123456", "12345-60");
        });
    }




    @Test
    public void addHourButtonShowsPopup() throws Exception {

        onView(withId(R.id.clinic_form_horario_atendimento_button_add)).perform(click());

        Thread.sleep(300);

        onView(withId(R.id.pop_up_date_picker)).check(matches(isDisplayed()));

    }

    @Test
    public void popupNotShownBeforeClickInButtonAddHour() {
        onView(withId(R.id.pop_up_date_picker))
                .check(doesNotExist());
        onView(withId(R.id.clinic_form_horario_atendimento_button_add)).perform(click());
        onView(withId(R.id.pop_up_date_picker))
                .inRoot(RootMatchers.isPlatformPopup())
                .check(matches(isDisplayed()));
    }

    @Test
    public void onSaveDatePickerPopupInsertOneViewIBusinessHourLayout() throws Exception {

        List<String> weekDisplayedList = setDayOfWork(2, 9, 18);
        Thread.sleep(500);
        onView(withId(R.id.date_picker_pop_up_button_confirm)).check(doesNotExist());
        scenario.onActivity(activity -> {
            LinearLayout linearLayout = activity.findViewById(R.id.clinic_form_horario_atendimento_layout);
            int count = linearLayout.getChildCount();
            assertEquals(1, count);

            TextView weekView = linearLayout.getChildAt(0).findViewById(R.id.time_descrition_fragment_textView_day_of_week);
            TextView beginHourView = linearLayout.getChildAt(0).findViewById(R.id.time_descrition_fragment_textView_hour_begin);
            TextView endHourView = linearLayout.getChildAt(0).findViewById(R.id.time_descrition_fragment_textView_hour_end);

            Assert.assertEquals(weekDisplayedList.get(0), weekView.getText().toString());
            Assert.assertEquals(weekDisplayedList.get(1), beginHourView.getText().toString());
            Assert.assertEquals(weekDisplayedList.get(2), endHourView.getText().toString());
        });
    }

    @Test
    public void SaveCorrectlyDayOfWorkInDataBank() {

        onView(withId(R.id.clinic_form_general_data_name)).perform(replaceText("Clinica Teste"));
        onView(withId(R.id.clinic_form_address_street)).perform(replaceText("Rua Teste"));
        onView(withId(R.id.clinic_form_address_number)).perform(replaceText("123"));
        onView(withId(R.id.clinic_form_address_neighborhood)).perform(replaceText("Bairro Teste"));
        onView(withId(R.id.clinic_form_address_city)).perform(replaceText("Cidade Teste"));
        onView(withId(R.id.clinic_form_general_data_phone)).perform(replaceText("11999999999"));
        onView(withId(R.id.clinic_form_general_data_email)).perform(replaceText("teste@exemplo.com"));

        List<String> weekDisplayedList = setDayOfWork(2, 9, 18);
        onView(withId(R.id.clinic_form_fab)).perform(click());

        int clinicaId = db.clinicaDao().findIdByName("Clinica Teste");
        List<DayOfWork> diasDeTrabalho = db.dayOfWorkDao().getDaysforClinicaId(clinicaId);
        assertEquals(1, diasDeTrabalho.size());
        DayOfWork dayOfWork = diasDeTrabalho.get(0);
        assertEquals(clinicaId, dayOfWork.getIdClinica());
        assertEquals(weekDisplayedList.get(0), dayOfWork.getDayOfWeek());
        assertEquals(weekDisplayedList.get(1), dayOfWork.getHoraInicio());
        assertEquals(weekDisplayedList.get(2), dayOfWork.getHoraFim());


    }

    @Test
    public void SaveMultipleDayOfWorkInDataBank() {

        onView(withId(R.id.clinic_form_general_data_name)).perform(replaceText("Clinica Teste2"));
        onView(withId(R.id.clinic_form_address_street)).perform(replaceText("Rua Teste"));
        onView(withId(R.id.clinic_form_address_number)).perform(replaceText("123"));
        onView(withId(R.id.clinic_form_address_neighborhood)).perform(replaceText("Bairro Teste"));
        onView(withId(R.id.clinic_form_address_city)).perform(replaceText("Cidade Teste"));
        onView(withId(R.id.clinic_form_general_data_phone)).perform(replaceText("11999999999"));
        onView(withId(R.id.clinic_form_general_data_email)).perform(replaceText("teste@exemplo.com"));

        List<String> weekDisplayedList1 = setDayOfWork(2, 9, 18);
        List<String> weekDisplayedList2 = setDayOfWork(3, 10, 15);
        onView(withId(R.id.clinic_form_fab)).perform(click());

        int clinicaId = db.clinicaDao().findIdByName("Clinica Teste2");
        List<DayOfWork> diasDeTrabalho = db.dayOfWorkDao().getDaysforClinicaId(clinicaId);
        assertEquals(2, diasDeTrabalho.size());
        boolean foundFirst = false;
        boolean foundSecond = false;
       for (DayOfWork dayOfWork : diasDeTrabalho) {
            if (dayOfWork.getDayOfWeek().equals(weekDisplayedList1.get(0)) && !foundFirst) {
                foundFirst = true;
                assertEquals(clinicaId, dayOfWork.getIdClinica());
                assertEquals(weekDisplayedList1.get(1), dayOfWork.getHoraInicio());
                assertEquals(weekDisplayedList1.get(2), dayOfWork.getHoraFim());
            } else if (dayOfWork.getDayOfWeek().equals(weekDisplayedList2.get(0)) && !foundSecond) {
                foundSecond = true;
                assertEquals(clinicaId, dayOfWork.getIdClinica());
                assertEquals(weekDisplayedList2.get(1), dayOfWork.getHoraInicio());
                assertEquals(weekDisplayedList2.get(2), dayOfWork.getHoraFim());
            } else {
                Assert.fail("Dia da semana inesperado: " + dayOfWork.getDayOfWeek());
            }
        }


    }

    private List<String> setDayOfWork(int weekNumber, int hourBeginNumber, int endHourNumber) {
        onView(withId(R.id.clinic_form_horario_atendimento_button_add)).perform(click());


        final String[] result = new String[3];


        onView(withId(R.id.pop_up_date_picker))
                .inRoot(RootMatchers.isPlatformPopup())
                .check(matches(isDisplayed()));


        onView(withId(R.id.date_picker_pop_up_number_picker_week_day))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(setNumberPickerValue(weekNumber, result, 0));

        onView(withId(R.id.date_picker_pop_up_number_picker_hour_start))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(setNumberPickerValue(hourBeginNumber, result, 1));

        onView(withId(R.id.date_picker_pop_up_number_picker_hour_end))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(setNumberPickerValue(endHourNumber, result, 2));

        onView(withId(R.id.date_picker_pop_up_button_confirm))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        return Arrays.asList(result[0], result[1], result[2]);
    }

    private static ViewAction setNumberPickerValue(final int value, final String[] outDisplayedValue, final int outIndex) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(NumberPicker.class);
            }

            @Override
            public String getDescription() {
                return "Set NumberPicker value to " + value;
            }

            @Override
            public void perform(UiController uiController, View view) {
                NumberPicker picker = (NumberPicker) view;
                picker.setValue(value);
                String displayed;
                String[] vals = picker.getDisplayedValues();
                int idx = picker.getValue() - picker.getMinValue();
                if (vals != null && idx >= 0 && idx < vals.length) {
                    displayed = vals[idx];
                } else {
                    displayed = String.valueOf(picker.getValue());
                }
                if (outDisplayedValue != null) outDisplayedValue[outIndex] = displayed;
                uiController.loopMainThreadUntilIdle();
            }
        };
    }




}
