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
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.formularios.InvalidViewGroupIdException;
import com.example.worknutri.ui.formularios.formInserters.AnthropometryFormInserter;
import com.example.worknutri.ui.formularios.formInserters.PathologyFormInserter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AnthropometryFormInserterTest {
    private ViewGroup viewGroup;
    private Antropometria anthropometryExpected;
    private AnthropometryFormInserter inserter;
    private final String birthday = "01/01/1990";
    private Context context;

    @Before
    public void setUp() {
        context = TestUtil.getThemedContext();
        viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.formulario_paciente_activity,
                new LinearLayout(context), false);
        inserter = AnthropometryFormInserter.create(viewGroup);
        anthropometryExpected = TestEntityFactory.generateAntropometria(birthday, 1.75, 65);


    }

    @Test
    public void getViewGroupExpectedIdIsCorrect() {
        assertEquals(R.id.formulario_paciente_antropometria_layout, AnthropometryFormInserter.getViewGroupIdExpected());
    }

    @Test
    public void insertViewGroupInEntityPopulatesAntropometriaFromUi() {

        assertNotNull(viewGroup);
        assertEquals(R.id.form_patient_activity, viewGroup.getId());
        insertDataInLayout();

        Antropometria anthropometryResult = new Antropometria();

        Paciente patient = new Paciente();
        patient.setNascimento(birthday);
        patient.setGenero('M');
        inserter.insertViewGroupInEntity(anthropometryResult, patient);


        checkAnthropometryIsCorrectInserted(anthropometryResult);

    }

    private void insertDataInLayout() {
        insertInEditText(R.id.formulario_paciente_antropometria_circum_braco, anthropometryExpected.getCircumferenciaBracoDir());
        insertInEditText(R.id.formulario_paciente_antropometria_circum_coxa, anthropometryExpected.getCircumferenciaCoxaDir());
        insertInEditText(R.id.formulario_paciente_antropometria_circum_abdomen, anthropometryExpected.getCircumferenciaAbdomen());
        insertInEditText(R.id.formulario_paciente_antropometria_circum_cintura, anthropometryExpected.getCircumferenciaCintura());
        insertInEditText(R.id.formulario_paciente_antropometria_circum_quadril, anthropometryExpected.getCircumferenciaQuadril());

        insertInEditText(R.id.formulario_paciente_antropometria_altura, anthropometryExpected.getAltura());
        insertInEditText(R.id.formulario_paciente_antropometria_peso_atual, anthropometryExpected.getPeso());
        insertInEditText(R.id.formulario_paciente_antropometria_peso_ideal, anthropometryExpected.getPesoIdeal());
        insertInEditText(R.id.formulario_paciente_dados_pessoais_nascimento, birthday);
        Spinner genderSpinner = viewGroup.findViewById(R.id.formulario_paciente_antropometria_calculos_atividade_spinner);
        genderSpinner.setSelection(1);
    }

    private void insertInEditText(int idEditText, String value) {
        EditText editText = viewGroup.findViewById(idEditText);
        editText.setText(value);
    }
    private void checkAnthropometryIsCorrectInserted(Antropometria anthropometryResult) {
        assertEquals(anthropometryExpected.getCircumferenciaBracoDir(),
                anthropometryResult.getCircumferenciaBracoDir());
        assertEquals(anthropometryExpected.getCircumferenciaCoxaDir(),
                anthropometryResult.getCircumferenciaCoxaDir());
        assertEquals(anthropometryExpected.getCircumferenciaAbdomen(),
                anthropometryResult.getCircumferenciaAbdomen());
        assertEquals(anthropometryExpected.getCircumferenciaCintura(),
                anthropometryResult.getCircumferenciaCintura());
        assertEquals(anthropometryExpected.getCircumferenciaQuadril(),
                anthropometryResult.getCircumferenciaQuadril());

        assertEquals(anthropometryExpected.getAltura(), anthropometryResult.getAltura());
        assertEquals(anthropometryExpected.getPeso(), anthropometryResult.getPeso());
        assertEquals(anthropometryExpected.getPesoIdeal(), anthropometryResult.getPesoIdeal());

        assertEquals(anthropometryExpected.getImc(), anthropometryResult.getImc());
        assertEquals(anthropometryExpected.getTaxaMetabolica(), anthropometryResult.getTaxaMetabolica());
        assertEquals(anthropometryExpected.getValorMetabolico(), anthropometryResult.getValorMetabolico());
        assertEquals(anthropometryExpected.getRegraBolso(), anthropometryResult.getRegraBolso());
        assertEquals(anthropometryExpected.getVenta(), anthropometryResult.getVenta());
        assertEquals(anthropometryExpected.getAgua(), anthropometryResult.getAgua());
    }


    @Test
    public void insertAnthropometryInViewGroupPopulatesUiFromEntity(){
        assertEquals(viewGroup.getId(), R.id.form_patient_activity);
        inserter.insertEntityInViewGroup(anthropometryExpected);
        checkIfViewGroupIsPopulatedWithAnthropometry();

    }

    private void checkIfViewGroupIsPopulatedWithAnthropometry() {
        checkIfEditTextIsPopulatedWithValue(anthropometryExpected.getCircumferenciaAbdomen(),
                R.id.formulario_paciente_antropometria_circum_abdomen);
        checkIfEditTextIsPopulatedWithValue(anthropometryExpected.getCircumferenciaCoxaDir(),
                R.id.formulario_paciente_antropometria_circum_coxa);
        checkIfEditTextIsPopulatedWithValue(anthropometryExpected.getCircumferenciaBracoDir(),
                R.id.formulario_paciente_antropometria_circum_braco);
        checkIfEditTextIsPopulatedWithValue(anthropometryExpected.getCircumferenciaCintura(),
                R.id.formulario_paciente_antropometria_circum_cintura);
        checkIfEditTextIsPopulatedWithValue(anthropometryExpected.getCircumferenciaQuadril(),
                R.id.formulario_paciente_antropometria_circum_quadril);

        checkIfEditTextIsPopulatedWithValue(anthropometryExpected.getAltura(),
                R.id.formulario_paciente_antropometria_altura);
        checkIfEditTextIsPopulatedWithValue(anthropometryExpected.getPeso(),
                R.id.formulario_paciente_antropometria_peso_atual);
        checkIfEditTextIsPopulatedWithValue(anthropometryExpected.getPesoIdeal(),
                R.id.formulario_paciente_antropometria_peso_ideal);
    }
    private void checkIfEditTextIsPopulatedWithValue( String expectedValue, int idEditText) {
        EditText editText = viewGroup.findViewById(idEditText);
        assertEquals(expectedValue, editText.getText().toString());
    }

    @Test
    public void checkViewGroupIsCorrectlyReturnsTrueIfViewGroupIsCorrect() {
        assertEquals(viewGroup.getId(), R.id.form_patient_activity);
        assertTrue(AnthropometryFormInserter.checkViewGroupIsCorrectly(viewGroup));
        assertTrue(AnthropometryFormInserter.checkViewGroupIsCorrectly(viewGroup.findViewById(R.id.formulario_paciente_antropometria_layout)));
        assertFalse(AnthropometryFormInserter.checkViewGroupIsCorrectly(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_layout)));
        assertFalse(AnthropometryFormInserter.checkViewGroupIsCorrectly(viewGroup.findViewById(R.id.formulario_paciente_patologia_layout)));

    }

    @Test
    public void checkIfInCallCreateToViewGroupWithIdIncorrectThrowInvalidViewGroupIdException(){
        ViewGroup incorrectViewGroup = viewGroup.findViewById(R.id.formulario_paciente_antropometria_layout);
        assertTrue(AnthropometryFormInserter.checkViewGroupIsCorrectly(incorrectViewGroup));
        inserter = AnthropometryFormInserter.create(incorrectViewGroup);
        inserter.insertEntityInViewGroup(anthropometryExpected);
        checkIfViewGroupIsPopulatedWithAnthropometry();

        incorrectViewGroup.setId(R.id.formulario_paciente_dados_pessoais_layout);
        assertFalse(AnthropometryFormInserter.checkViewGroupIsCorrectly(incorrectViewGroup));
        assertThrows(InvalidViewGroupIdException.class,() -> AnthropometryFormInserter.create(incorrectViewGroup));


    }

    @Test
    public void createThrowsWhenViewGroupIdInvalid_andReturnsInstanceWhenValid() {
        ViewGroup root = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.formulario_paciente_activity,
                new LinearLayout(context), false);
        checkNotThrowExceptionAndGenerateObject(R.id.formulario_paciente_antropometria_layout, root);
        checkNotThrowExceptionAndGenerateObject(R.id.form_patient_activity, root);
        root.setId(R.id.formulario_paciente_dados_pessoais_layout);
        assertThrows("create() not throws InvalidViewGroupIdException to viewGroup with id invalid",
                InvalidViewGroupIdException.class, () -> PathologyFormInserter.create(root,null));

    }

    private static void checkNotThrowExceptionAndGenerateObject(int id, ViewGroup root) {
        root.setId(id);
        try {
            AnthropometryFormInserter created = AnthropometryFormInserter.create(root);
            assertNotNull("create should return one instance when ViewGroup id is válid (id=" + id + ")", created);
        } catch (InvalidViewGroupIdException e) {
            throw new AssertionError("create() throws InvalidViewGroupIdException to valid id: " + id, e);
        }
    }
}
