package com.example.worknutri.ui.forms.formInserters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.formularios.formInserters.ClinicFormInserter;
import com.example.worknutri.util.ViewsUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ClinicFormInserterTest {

    private ViewGroup clinicFormLayout;
    private Context context;
    private ClinicFormInserter clinicFormInserter;

    @Before
    public void setUp() {
        context = TestUtil.getThemedContext();
        clinicFormLayout = (ViewGroup) LayoutInflater.from(context)
                .inflate(R.layout.clinic_form_activity, new LinearLayout(context), false);
        clinicFormInserter = ClinicFormInserter.create(clinicFormLayout);
    }

    @Test
    public void insertEntityInViewGroup_shouldPopulateViewsFromClinica() {
        // prepara um Clinica com valores de teste
        Clinica clinica = new Clinica();
        clinica.setNome("Nome Teste");
        clinica.setEmail("teste@example.com");
        clinica.setTelefone1("123456789");
        clinica.setCodigoPostal("12345-678");
        clinica.setRua("Rua A");
        clinica.setNumero(42);
        clinica.setComplemento("Apt 1");
        clinica.setBairro("Bairro B");
        clinica.setCidade("Cidade C");

        String[] estados = context.getResources().getStringArray(R.array.estado_brasil);
        String estadoEscolhido = estados.length > 1 ? estados[1] : estados[0];
        clinica.setEstado(estadoEscolhido);

        // executa
        clinicFormInserter.insertEntityInViewGroup( clinica);

        // valida campos preenchidos nas views
        assertEquals("Nome deve ser populado",
                clinica.getNome(),
                ViewsUtil.getStringOfEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_name)));

        assertEquals("Email deve ser populado",
                clinica.getEmail(),
                ViewsUtil.getStringOfEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_email)));

        assertEquals("Telefone deve ser populado",
                clinica.getTelefone1(),
                ViewsUtil.getStringOfEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_phone)));

        assertEquals("CEP deve ser populado",
                clinica.getCodigoPostal(),
                ViewsUtil.getStringOfEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_zip_postal)));

        assertEquals("Rua deve ser populado",
                clinica.getRua(),
                ViewsUtil.getStringOfEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_street)));

        assertEquals("Numero deve ser populado",
                String.valueOf(clinica.getNumero()),
                ViewsUtil.getStringOfEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_number)));

        assertEquals("Complemento deve ser populado",
                clinica.getComplemento(),
                ViewsUtil.getStringOfEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_complement)));

        assertEquals("Bairro deve ser populado",
                clinica.getBairro(),
                ViewsUtil.getStringOfEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_neighborhood)));

        assertEquals("Cidade deve ser populado",
                clinica.getCidade(),
                ViewsUtil.getStringOfEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_city)));

        Spinner spinner = clinicFormLayout.findViewById(R.id.clinic_form_address_state_spinner);
        assertEquals("Estado selecionado deve corresponder",
                estadoEscolhido,
                spinner.getSelectedItem().toString());
    }

    @Test
    public void insertInClinica_shouldReadValuesFromViewsIntoClinica() {
        // popula as views com valores de teste
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_name), "Nome View");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_email), "view@example.com");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_general_data_phone), "987654321");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_zip_postal), "98765-432");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_street), "Rua View");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_number), "77");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_complement), "Casa");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_neighborhood), "Bairro V");
        ViewsUtil.insertInEditText(clinicFormLayout.findViewById(R.id.clinic_form_address_city), "Cidade V");

        String[] estados = context.getResources().getStringArray(R.array.estado_brasil);
        String estadoEscolhido = estados.length > 1 ? estados[1] : estados[0];
        Spinner spinner = clinicFormLayout.findViewById(R.id.clinic_form_address_state_spinner);
        // encontra índice do estado escolhido e seleciona
        int index = 0;
        for (int i = 0; i < estados.length; i++) {
            if (estados[i].equals(estadoEscolhido)) {
                index = i;
                break;
            }
        }
        spinner.setSelection(index);

        // lê para um objeto Clinica
        Clinica clinica = new Clinica();
        clinicFormInserter.insertViewGroupInEntity(clinica);

        // valida valores lidos
        assertEquals("Nome lido corretamente", "Nome View", clinica.getNome());
        assertEquals("Email lido corretamente", "view@example.com", clinica.getEmail());
        assertEquals("Telefone lido corretamente", "987654321", clinica.getTelefone1());
        assertEquals("Rua lida corretamente", "Rua View", clinica.getRua());
        assertEquals("Numero lido corretamente", 77, clinica.getNumero());
        assertEquals("Complemento lido corretamente", "Casa", clinica.getComplemento());
        assertEquals("Bairro lido corretamente", "Bairro V", clinica.getBairro());
        assertEquals("Cidade lida corretamente", "Cidade V", clinica.getCidade());
        assertEquals("Estado lido corretamente", estadoEscolhido, clinica.getEstado());
    }
}

