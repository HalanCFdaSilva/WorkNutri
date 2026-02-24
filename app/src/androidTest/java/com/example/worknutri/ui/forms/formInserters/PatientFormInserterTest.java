package com.example.worknutri.ui.forms.formInserters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class PatientFormInserterTest {

    private Context context;
    private ViewGroup viewGroup;
    private PatientFormInserter inserter;

    @Before
    public void setUp() {
        context = TestUtil.getThemedContext();
        viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.formulario_paciente_activity,
                new LinearLayout(context), false);
        inserter = PatientFormInserter.create(viewGroup);
    }

    @Test
    public void createThrowsWhenViewGroupIdInvalid_andReturnsInstanceWhenValid() {
        ViewGroup root = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.formulario_paciente_activity,
                new LinearLayout(context), false);

        checkNotThrowExceptionAndGenerateObject(R.id.form_patient_activity, root);
        checkNotThrowExceptionAndGenerateObject(R.id.formulario_paciente_dados_pessoais_layout, root);

        root.setId(R.id.formulario_paciente_patologia_layout);
        assertThrows(IllegalArgumentException.class, () -> PatientFormInserter.create(root));
    }

    private void checkNotThrowExceptionAndGenerateObject(int id, ViewGroup root) {
        root.setId(id);
        try {
            PatientFormInserter created = PatientFormInserter.create(root);
            assertNotNull("create should return one instance when ViewGroup id is valid (id=" + id + ")", created);
        } catch (IllegalArgumentException e) {
            throw new AssertionError("create() throws IllegalArgumentException to valid id: " + id, e);
        }
    }

    @Test
    public void insertViewGroupInEntityPopulatesPacienteFromUiForValidIds() {
        int[] validIds = new int[] {
                R.id.form_patient_activity,
                R.id.formulario_paciente_dados_pessoais_layout
        };

        // expected values
        Paciente expected = TestEntityFactory.generatePatient();

        for (int id : validIds) {
            ViewGroup inflated = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.formulario_paciente_activity,
                    new LinearLayout(context), false);
            ViewGroup root = inflated.findViewById(id);
            assertNotNull("root must exist for id=" + id, root);

            // populate UI
            EditText name = root.findViewById(R.id.formulario_paciente_dados_pessoais_name);
            EditText nascimento = root.findViewById(R.id.formulario_paciente_dados_pessoais_nascimento);
            EditText email = root.findViewById(R.id.formulario_paciente_dados_pessoais_email);
            EditText fone = root.findViewById(R.id.formulario_paciente_dados_pessoais_fone);
            Spinner genero = root.findViewById(R.id.formulario_paciente_dados_pessoais_genero_spinner);

            name.setText(expected.getNomePaciente());
            nascimento.setText(expected.getNascimento());
            email.setText(expected.getEmail());
            fone.setText(expected.getTelefone());
            // set spinner selection to match expected gender
            genero.setSelection(expected.getGenero() == 'M' ? 0 : 1);

            Paciente target = new Paciente();
            PatientFormInserter localInserter = PatientFormInserter.create(root);
            localInserter.insertViewGroupInEntity(target);

            assertEquals(expected.getNomePaciente(), target.getNomePaciente());
            assertEquals(expected.getNascimento(), target.getNascimento());
            assertEquals(expected.getEmail(), target.getEmail());
            assertEquals(expected.getTelefone(), target.getTelefone());
            assertEquals(expected.getGenero(), target.getGenero());
        }
    }

    @Test
    public void insertEntityInViewGroupPopulatesViewsFromPaciente() {
        Paciente expected = TestEntityFactory.generatePatient();
        inserter.insertEntityInViewGroup(expected);

        EditText name = viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_name);
        EditText nascimento = viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_nascimento);
        EditText email = viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_email);
        EditText fone = viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_fone);
        Spinner genero = viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_genero_spinner);

        assertEquals(expected.getNomePaciente(), name.getText().toString());
        assertEquals(expected.getNascimento(), nascimento.getText().toString());
        assertEquals(expected.getEmail(), email.getText().toString());
        assertEquals(expected.getTelefone(), fone.getText().toString());
        int expectedSelection = expected.getGenero() == 'M' ? 0 : 1;
        assertEquals(expectedSelection, genero.getSelectedItemPosition());
    }

    @Test
    public void checkViewGroupIsCorrectlyReturnsTrueAndFalse() {
        // viewGroup id was set in setUp to form_patient_activity
        assertTrue(PatientFormInserter.checkViewGroupIsCorrectly(viewGroup));
        assertTrue(PatientFormInserter.checkViewGroupIsCorrectly(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_layout)));

        viewGroup.setId(R.id.formulario_paciente_patologia_layout);
        assertFalse(PatientFormInserter.checkViewGroupIsCorrectly(viewGroup));
    }

    @Test
    public void insertObservationsInPaciente_setsPacienteObservations() {
        Paciente p = new Paciente();
        EditText et = new EditText(context);
        String obs = "alguma observacao";
        et.setText(obs);
        PatientFormInserter.insertObservationsInPaciente(p, et);
        assertEquals(obs, p.getObservacoes());
    }

    @Test
    public void insertClinicaInPaciente_setsPacienteClinicaIdBasedOnSpinnerSelection() {
        // prepare spinner and clinicas
        Spinner spinner = new Spinner(context);
        List<Clinica> clinicas = new ArrayList<>();
        // create a few Clinica instances (use TestEntityFactory if available)
        Clinica c1 = new Clinica();
        c1.setId(10L);
        c1.setNome("Clinica A");
        Clinica c2 = new Clinica();
        c2.setId(20L);
        c2.setNome("Clinica B");
        Clinica c3 = new Clinica();
        c3.setId(30L);
        c3.setNome("Clinica C");
        clinicas.add(c1);
        clinicas.add(c2);
        clinicas.add(c3);

        // simulate selecting the second clinic (index 1 -> c2)
        spinner.setSelection(1);

        Paciente p = new Paciente();
        PatientFormInserter.insertClinicaInPaciente(spinner, p, clinicas);

        assertEquals("Paciente clinicaId should be set to the selected Clinica id",
                Long.valueOf(20L), Long.valueOf(p.getClinicaId()));

        // change selection to first
        spinner.setSelection(0);
        PatientFormInserter.insertClinicaInPaciente(spinner, p, clinicas);
        assertEquals(Long.valueOf(10L), Long.valueOf(p.getClinicaId()));

        // change to last
        spinner.setSelection(2);
        PatientFormInserter.insertClinicaInPaciente(spinner, p, clinicas);
        assertEquals(Long.valueOf(30L), Long.valueOf(p.getClinicaId()));
    }

}
