package com.example.worknutri.ui.forms.patientForm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.support.TestUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class PatientFormAdapterTest {

    private PatientFormAdapter adapter;
    private android.content.Context context;
    private ViewGroup root;

    @Before
    public void setup() {
        context = TestUtil.getThemedContext();
        adapter = new PatientFormAdapter(context);

        root = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.patient_form_activity,
                new LinearLayout(context), false);
    }

    @Test
    public void setClinicsPopulatesSpinnerAdapter() throws Exception {
        final Spinner spinner = new Spinner(context);

        // replace internal clinicsInOrder list contents via reflection
        Field clinicsField = PatientFormAdapter.class.getDeclaredField("clinicsInOrder");
        clinicsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Clinica> clinics = (List<Clinica>) clinicsField.get(adapter);
        if (clinics == null) {
            clinics = new ArrayList<>();
            clinicsField.set(adapter, clinics);
        }
        clinics.clear();

        Clinica c1 = new Clinica();
        c1.setId(1L);
        c1.setNome("A");
        Clinica c2 = new Clinica();
        c2.setId(2L);
        c2.setNome("B");
        clinics.add(c1);
        clinics.add(c2);

        // call setClinicas on UI thread
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> adapter.setClinics(spinner));

        List<Clinica> finalClinics = clinics;
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            assertNotNull("Spinner adapter should not be null", spinner.getAdapter());
            assertEquals("Spinner adapter count should match clinics list size", finalClinics.size(), spinner.getAdapter().getCount());
        });
    }


    @Test
    public void validateFormReturnsFalseWhenPhoneFieldIsFilledIncorrectly() {
        TextView error = root.findViewById(R.id.patient_form_activity_anthropometry_calculations_error);
        error.setText("");
        insertStringMandatoryfields();
        EditText phoneEditText = root.findViewById(R.id.patient_form_activity_personal_data_phone);
        phoneEditText.setText("invalid phone");
        assertFalse("validaFormulario should return false when phone field is filled incorrectly",
                adapter.validateForm(root, error));
        assertEquals("validaFormulario should set correct error message when phone field is filled incorrectly",
                context.getString(R.string.error_fone), error.getText());
    }

    @Test
    public void whenMandatoryFieldsIsEmptyAndPhoneFieldIsIncorrectlyFilledAMandatoryFieldsErrorHasPriority() {
        TextView error = root.findViewById(R.id.patient_form_activity_anthropometry_calculations_error);
        error.setText("");
        insertStringMandatoryfields();
        getMandatoryEditTexts()[0].setText("");
        EditText phoneEditText = root.findViewById(R.id.patient_form_activity_personal_data_phone);
        phoneEditText.setText("invalid phone");

        assertFalse("validaFormulario should return false when phone field is filled incorrectly or a mandatory field is empty",
                adapter.validateForm(root, error));
        assertEquals("when phone field is filled incorrectly and a mandatory field is empty a mandatory field error messsage has priority over phone field error message",
                context.getString(R.string.error_blank), error.getText());
    }

    @Test
    public void validateFormReturnsTrueWhenPhoneFieldIsFilledCorrectly() {
        TextView error = root.findViewById(R.id.patient_form_activity_anthropometry_calculations_error);
        error.setText("");
        insertStringMandatoryfields();
        EditText phoneEditText = root.findViewById(R.id.patient_form_activity_personal_data_phone);
        phoneEditText.setText("21999999999");
        assertTrue("validaFormulario should return true when phone field is filled correctly and all mandatory fields are filled",
                adapter.validateForm(root, error));
        assertTrue(error.getText().toString().isBlank());

    }

    @Test
    public void validateFormReturnsFalseWhenEmailFieldIsFilledIncorrectly() {
        TextView error = root.findViewById(R.id.patient_form_activity_anthropometry_calculations_error);
        error.setText("");
        insertStringMandatoryfields();
        EditText emailEditText = root.findViewById(R.id.patient_form_activity_personal_data_email);
        emailEditText.setText("invalid@email");
        assertFalse("validaFormulario should return false when email field is filled incorrectly",
                adapter.validateForm(root, error));
        assertEquals("validaFormulario should set correct error message when email field is filled incorrectly",
                context.getString(R.string.error_email), error.getText());
    }

    @Test
    public void whenMandatoryFieldsIsEmptyAndEmailFieldIsIncorrectlyFilledAMandatoryFieldsErrorHasPriority() {
        TextView error = root.findViewById(R.id.patient_form_activity_anthropometry_calculations_error);
        error.setText("");
        insertStringMandatoryfields();
        getMandatoryEditTexts()[0].setText("");
        EditText emailEditText = root.findViewById(R.id.patient_form_activity_personal_data_email);
        emailEditText.setText("invalid@email");

        assertFalse("validaFormulario should return false when email field is filled incorrectly or a mandatory field is empty",
                adapter.validateForm(root, error));
        assertEquals("when email field is filled incorrectly and a mandatory field is empty a mandatory field error messsage has priority over email field error message",
                context.getString(R.string.error_blank), error.getText());
    }

    @Test
    public void whenEmailFieldAndPhoneFieldIsIncorrectlyFilledAEmailFieldErrorHasPriority() {
        TextView error = root.findViewById(R.id.patient_form_activity_anthropometry_calculations_error);
        error.setText("");
        insertStringMandatoryfields();
        EditText phoneEditText = root.findViewById(R.id.patient_form_activity_personal_data_phone);
        phoneEditText.setText("invalid phone");
        EditText emailEditText = root.findViewById(R.id.patient_form_activity_personal_data_email);
        emailEditText.setText("invalid@email");

        assertFalse("validaFormulario should return false when email field is filled incorrectly or a mandatory field is empty",
                adapter.validateForm(root, error));
        assertEquals("when email field and phone field is filled incorrectly a email field error messsage has priority over phone field error message",
                context.getString(R.string.error_email), error.getText());
    }

    @Test
    public void validateFormReturnsTrueWhenEmailFieldIsFilledCorrectly() {
        TextView error = root.findViewById(R.id.patient_form_activity_anthropometry_calculations_error);
        error.setText("");
        insertStringMandatoryfields();
        EditText emailEditText = root.findViewById(R.id.patient_form_activity_personal_data_email);
        emailEditText.setText("email@correctly.filled");
        assertTrue("validaFormulario should return true when email field is filled correctly and all mandatory fields are filled",
                adapter.validateForm(root, error));
        assertTrue(error.getText().toString().isBlank());

    }

    @Test
    public void validateFormReturnsFalseWhenDateFieldIsFilledIncorrectly() {
        TextView error = root.findViewById(R.id.patient_form_activity_anthropometry_calculations_error);
        error.setText("");
        insertStringMandatoryfields();
        EditText dateEditText = root.findViewById(R.id.patient_form_activity_personal_data_birthday);
        dateEditText.setText("22/22/2222");
        assertFalse("validaFormulario should return false when date field is filled incorrectly",
                adapter.validateForm(root, error));
        assertEquals("validaFormulario should set correct error message when date field is filled incorrectly",
                context.getString(R.string.error_data), error.getText());
    }

    @Test
    public void whenMandatoryFieldsIsEmptyAndDateFieldIsIncorrectlyFilledAMandatoryFieldsErrorHasPriority() {
        TextView error = root.findViewById(R.id.patient_form_activity_anthropometry_calculations_error);
        error.setText("");
        insertStringMandatoryfields();
        getMandatoryEditTexts()[0].setText("");
        EditText dateEditText = root.findViewById(R.id.patient_form_activity_personal_data_birthday);
        dateEditText.setText("22/22/2222");

        assertFalse("validaFormulario should return false when date field is filled incorrectly or a mandatory field is empty",
                adapter.validateForm(root, error));
        assertEquals("when date field is filled incorrectly and a mandatory field is empty a mandatory field error messsage has priority over date field error message",
                context.getString(R.string.error_blank), error.getText());
    }

    @Test
    public void whenDateFieldAndEmailFieldIsIncorrectlyFilledADateFieldErrorHasPriority() {
        TextView error = root.findViewById(R.id.patient_form_activity_anthropometry_calculations_error);
        error.setText("");
        insertStringMandatoryfields();
        EditText emailEditText = root.findViewById(R.id.patient_form_activity_personal_data_email);
        emailEditText.setText("invalid@email");
        EditText dateEditText = root.findViewById(R.id.patient_form_activity_personal_data_birthday);
        dateEditText.setText("22/22/2222");

        assertFalse("validaFormulario should return false when email field or a  date field is filled incorrectly ",
                adapter.validateForm(root, error));
        assertEquals("when email field and date field is filled incorrectly a date field error messsage has priority over email field error message",
                context.getString(R.string.error_data), error.getText());
    }

    @Test
    public void validateFormReturnsTrueWhenDateFieldIsFilledCorrectly() {
        TextView error = root.findViewById(R.id.patient_form_activity_anthropometry_calculations_error);
        error.setText("");
        insertStringMandatoryfields();
        EditText dateEditText = root.findViewById(R.id.patient_form_activity_personal_data_birthday);
        dateEditText.setText("22/10/1990");
        assertTrue("validaFormulario should return true when email field is filled correctly and all mandatory fields are filled",
                adapter.validateForm(root, error));
        assertTrue(error.getText().toString().isBlank());

    }

    private void insertStringMandatoryfields() {
        EditText[] editTexts = getMandatoryEditTexts();
        String[] texts = getStringsToMandatoryEditTexts();

        editTexts[0].setText(texts[0]);
        editTexts[1].setText(texts[1]);
        editTexts[2].setText(texts[2]);
        editTexts[3].setText(texts[3]);
        editTexts[4].setText(texts[4]);
    }

    @Test
    public void validateFormReturnsTrueWhenAllMandatoryFieldsIsFilled() {
        TextView error = root.findViewById(R.id.patient_form_activity_anthropometry_calculations_error);
        error.setText("");
        insertStringMandatoryfields();


        assertTrue("validaObrigatorios should return true when all required fields are filled",
                adapter.validateForm(root, error));
        assertTrue(error.getText().toString().isBlank());
    }


    @Test
    public void validateFormReturnsFalseWhenAnyMandatoryFieldIsEmpty() {
        final TextView error = root.findViewById(R.id.patient_form_activity_anthropometry_calculations_error);

        EditText[] editTexts = getMandatoryEditTexts();
        String[] texts = getStringsToMandatoryEditTexts();
        editTexts[1].setText(texts[1]);
        editTexts[2].setText(texts[2]);
        editTexts[3].setText(texts[3]);
        editTexts[4].setText(texts[4]);
        for (int i = 0; i < editTexts.length; i++) {
            editTexts[i].setText("");
            boolean result = adapter.validateForm(root, error);
            assertFalse("validaObrigatorios should return false when a required field (name) is empty", result);
            assertEquals("validaObrigatorios should correct string in textView explained error", context.getText(R.string.error_blank), error.getText());
            error.setText("");
            editTexts[i].setText(texts[i]);
        }

    }

    private EditText[] getMandatoryEditTexts() {
        return new EditText[]{
                root.findViewById(R.id.patient_form_activity_personal_data_name),
                root.findViewById(R.id.patient_form_activity_personal_data_birthday),
                root.findViewById(R.id.patient_form_activity_anthropometry_height),
                root.findViewById(R.id.patient_form_activity_anthropometry_weight_current),
                root.findViewById(R.id.patient_form_activity_anthropometry_weight_ideal)
        };
    }

    private static String[] getStringsToMandatoryEditTexts() {
        return new String[]{
                "name test",
                "01/01/2000",
                "170",
                "Peso 70",
                "68"
        };
    }
}
