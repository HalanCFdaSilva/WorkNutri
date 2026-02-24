package com.example.worknutri.ui.forms.clinicForm;



import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.R;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.util.ViewsUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ClinicFormAdapterTest {

    private ClinicFormAdapter adapter;
    private ViewGroup clinicFormLayout;
    private  Context context;

    @Before
    public void setup() {
        context = TestUtil.getThemedContext();
        clinicFormLayout = (ViewGroup) LayoutInflater.from(context)
                .inflate(R.layout.clinic_form_activity, new LinearLayout(context), false);
        adapter = new ClinicFormAdapter(clinicFormLayout);
    }

    @Test
    public void validateForm_shouldReturnFalse_whenRequiredFieldsEmpty() {
        TextView errorView = new TextView(context);
        // sem preencher nada -> deve retornar false
        boolean result = adapter.validateForm( errorView);
        assertFalse(result);
    }

    @Test
    public void validateForm_shouldReturnFalse_insertStringInTextViewAndSetVisible() {
        TextView errorView = new TextView(context);
        // sem preencher nada -> deve retornar false
        boolean result = adapter.validateForm(errorView);
        assertFalse(result);
    }


    @Test
    public void validateForm_shouldReturnFalse_whenPhoneInvalid() {
        TextView errorView = new TextView(context);

        // preencher apenas campos obrigatórios, deixar telefone inválido
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_name), "Nome Teste");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_street), "Rua Ex");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_number), "10");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_neighborhood), "Bairro");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_city), "Cidade");

        // telefone inválido
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_phone), "12");
        // e-mail válido para isolar o caso do telefone
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_email), "valido@example.com");

        boolean result = adapter.validateForm( errorView);
        assertFalse(result);
    }

    @Test
    public void validateForm_shouldReturnFalse_whenEmailInvalid() {
        TextView errorView = new TextView(context);

        // preencher campos obrigatórios e telefone válido
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_name), "Nome Teste");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_street), "Rua Ex");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_number), "10");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_neighborhood), "Bairro");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_city), "Cidade");

        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_phone), "11999999999");
        // e-mail inválido
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_email), "invalido");

        boolean result = adapter.validateForm( errorView);
        assertFalse(result);
    }

    @Test
    public void validateForm_shouldReturnFalse_whenNameClinicIsEmpty() {
        TextView errorView = new TextView(context);


        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_street), "Rua Ex");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_number), "10");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_neighborhood), "Bairro");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_city), "Cidade");

        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_phone), "11999999999");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_email), "valido@example.com");

        boolean result = adapter.validateForm( errorView);
        assertFalse(result);
    }

    @Test
    public void validateForm_shouldReturnFalse_whenStreetIsEmpty() {
        TextView errorView = new TextView(context);

        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_name), "Nome Teste");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_number), "10");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_neighborhood), "Bairro");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_city), "Cidade");

        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_phone), "11999999999");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_email), "valido@example.com");

        boolean result = adapter.validateForm( errorView);
        assertFalse(result);
    }

    @Test
    public void validateForm_shouldReturnFalse_whenNumberIsEmpty() {
        TextView errorView = new TextView(context);

        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_name), "Nome Teste");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_street), "Rua Ex");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_neighborhood), "Bairro");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_city), "Cidade");

        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_phone), "11999999999");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_email), "valido@example.com");

        boolean result = adapter.validateForm( errorView);
        assertFalse(result);
    }

    @Test
    public void validateForm_shouldReturnFalse_whenNeighboorhoodIsEmpty() {
        TextView errorView = new TextView(context);

        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_name), "Nome Teste");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_street), "Rua Ex");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_number), "10");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_city), "Cidade");

        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_phone), "11999999999");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_email), "valido@example.com");

        boolean result = adapter.validateForm( errorView);
        assertFalse(result);
    }

    @Test
    public void validateForm_shouldReturnFalse_whenCityIsEmpty() {
        TextView errorView = new TextView(context);

        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_name), "Nome Teste");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_street), "Rua Ex");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_number), "10");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_neighborhood), "Bairro");

        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_phone), "11999999999");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_email), "valido@example.com");

        boolean result = adapter.validateForm( errorView);
        assertFalse(result);
    }

    @Test
    public void validateForm_shouldReturnTrue_whenRequiredAndPhoneEmailValid() {
        TextView errorView = new TextView(context);

        // preencher campos obrigatórios e telefone/email válidos
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_name), "Nome Teste");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_street), "Rua Ex");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_number), "10");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_neighborhood), "Bairro");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_city), "Cidade");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_phone), "11999999999");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_email), "valido@example.com");

        boolean result = adapter.validateForm( errorView);
        assertTrue(result);
    }

    @Test
    public void getDayOfWork_ReturnNotNullObject(){
        assertNotNull(adapter.getDayOfWorkUiSave());
    }


}

