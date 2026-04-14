package com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter;

import com.example.worknutri.calcular.ClassificacaoImc;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PatientFilterUiStateTest {

    private PatientFilterUiState patientFilterUiState;
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 65;

    @Before
    public void setUp() {
        patientFilterUiState = new PatientFilterUiState(MIN_AGE, MAX_AGE);
    }

    @Test
    public void testConstructorInitializesGeneroSelectedAsN() {
        assertEquals('N', patientFilterUiState.getGeneroSelected());
    }

    @Test
    public void testConstructorInitializesTupleOfYearSlider() {
        float[] tupleOfYearSlider = patientFilterUiState.getTupleOfYearSlider();
        assertNotNull(tupleOfYearSlider);
        assertEquals(2, tupleOfYearSlider.length);
        assertEquals(MIN_AGE, tupleOfYearSlider[0], 0.0f);
        assertEquals(MAX_AGE, tupleOfYearSlider[1], 0.0f);
    }

    @Test
    public void testConstructorInitializesTupleOfWeightSliderWithZeros() {
        float[] tupleOfWeightSlider = patientFilterUiState.getTupleOfWeightSlider();
        assertNotNull(tupleOfWeightSlider);
        assertEquals(2, tupleOfWeightSlider.length);
        assertEquals(0.0f, tupleOfWeightSlider[0], 0.0f);
        assertEquals(0.0f, tupleOfWeightSlider[1], 0.0f);
    }

    @Test
    public void testConstructorInitializesTupleOfHeightSliderWithZeros() {
        float[] tupleOfHeightSlider = patientFilterUiState.getTupleOfHeightSlider();
        assertNotNull(tupleOfHeightSlider);
        assertEquals(2, tupleOfHeightSlider.length);
        assertEquals(0.0f, tupleOfHeightSlider[0], 0.0f);
        assertEquals(0.0f, tupleOfHeightSlider[1], 0.0f);
    }

    @Test
    public void testConstructorInitializesClinicaIdSelectedList() {
        assertNotNull(patientFilterUiState.getClinicsIdSelected());
        assertTrue(patientFilterUiState.getClinicsIdSelected().isEmpty());
    }

    @Test
    public void testConstructorInitializesClassificacaoImcsList() {
        assertNotNull(patientFilterUiState.getBmiClassificationsSelected());
        assertTrue(patientFilterUiState.getBmiClassificationsSelected().isEmpty());
    }

    @Test
    public void testSetGeneroSelected() {
        patientFilterUiState.setGeneroSelected('M');
        assertEquals('M', patientFilterUiState.getGeneroSelected());
    }

    @Test
    public void testSetGeneroSelectedFemale() {
        patientFilterUiState.setGeneroSelected('F');
        assertEquals('F', patientFilterUiState.getGeneroSelected());
    }

    @Test
    public void testSetGeneroSelectedMultipleTimes() {
        patientFilterUiState.setGeneroSelected('M');
        assertEquals('M', patientFilterUiState.getGeneroSelected());

        patientFilterUiState.setGeneroSelected('F');
        assertEquals('F', patientFilterUiState.getGeneroSelected());

        patientFilterUiState.setGeneroSelected('N');
        assertEquals('N', patientFilterUiState.getGeneroSelected());
    }

    @Test
    public void testAddClinicaIdToSelected() {
        patientFilterUiState.getClinicsIdSelected().add(1L);
        assertEquals(1, patientFilterUiState.getClinicsIdSelected().size());
        assertEquals(Long.valueOf(1L), patientFilterUiState.getClinicsIdSelected().get(0));
    }

    @Test
    public void testAddMultipleClinicaIdsToSelected() {
        patientFilterUiState.getClinicsIdSelected().add(1L);
        patientFilterUiState.getClinicsIdSelected().add(2L);
        patientFilterUiState.getClinicsIdSelected().add(3L);

        assertEquals(3, patientFilterUiState.getClinicsIdSelected().size());
        assertTrue(patientFilterUiState.getClinicsIdSelected().contains(1L));
        assertTrue(patientFilterUiState.getClinicsIdSelected().contains(2L));
        assertTrue(patientFilterUiState.getClinicsIdSelected().contains(3L));
    }

    @Test
    public void testRemoveClinicaIdFromSelected() {
        patientFilterUiState.getClinicsIdSelected().add(1L);
        patientFilterUiState.getClinicsIdSelected().add(2L);
        patientFilterUiState.getClinicsIdSelected().remove(1L);

        assertEquals(1, patientFilterUiState.getClinicsIdSelected().size());
        assertEquals(Long.valueOf(2L), patientFilterUiState.getClinicsIdSelected().get(0));
    }

    @Test
    public void testAddClassificacaoImcToList() {
        ClassificacaoImc imc = ClassificacaoImc.DEFICIT;
        patientFilterUiState.getBmiClassificationsSelected().add(imc);

        assertEquals(1, patientFilterUiState.getBmiClassificationsSelected().size());
        assertEquals(imc, patientFilterUiState.getBmiClassificationsSelected().get(0));
    }

    @Test
    public void testSetWeightSliderValues() {
        float[] tupleOfWeightSlider = patientFilterUiState.getTupleOfWeightSlider();
        tupleOfWeightSlider[0] = 50.0f;
        tupleOfWeightSlider[1] = 100.0f;

        assertEquals(50.0f, patientFilterUiState.getTupleOfWeightSlider()[0], 0.0f);
        assertEquals(100.0f, patientFilterUiState.getTupleOfWeightSlider()[1], 0.0f);
    }

    @Test
    public void testSetHeightSliderValues() {
        float[] tupleOfHeightSlider = patientFilterUiState.getTupleOfHeightSlider();
        tupleOfHeightSlider[0] = 1.50f;
        tupleOfHeightSlider[1] = 2.00f;

        assertEquals(1.50f, patientFilterUiState.getTupleOfHeightSlider()[0], 0.0f);
        assertEquals(2.00f, patientFilterUiState.getTupleOfHeightSlider()[1], 0.0f);
    }

    @Test
    public void testClearClinicaIdSelected() {
        patientFilterUiState.getClinicsIdSelected().add(1L);
        patientFilterUiState.getClinicsIdSelected().add(2L);
        patientFilterUiState.getClinicsIdSelected().clear();

        assertTrue(patientFilterUiState.getClinicsIdSelected().isEmpty());
    }

    @Test
    public void testClearClassificacaoImcs() {
        patientFilterUiState.getBmiClassificationsSelected().add(ClassificacaoImc.NORMAL);
        patientFilterUiState.getBmiClassificationsSelected().clear();

        assertTrue(patientFilterUiState.getBmiClassificationsSelected().isEmpty());
    }

    @Test
    public void testMultipleInstancesIndependent() {
        PatientFilterUiState state1 = new PatientFilterUiState(20, 60);
        PatientFilterUiState state2 = new PatientFilterUiState(25, 70);

        state1.getClinicsIdSelected().add(1L);
        state1.setGeneroSelected('M');

        assertEquals(1, state1.getClinicsIdSelected().size());
        assertEquals(0, state2.getClinicsIdSelected().size());
        assertEquals('M', state1.getGeneroSelected());
        assertEquals('N', state2.getGeneroSelected());
    }

    @Test
    public void testYearSliderMinEqualsMax() {
        PatientFilterUiState state = new PatientFilterUiState(30, 30);
        assertEquals(30.0f, state.getTupleOfYearSlider()[0], 0.0f);
        assertEquals(30.0f, state.getTupleOfYearSlider()[1], 0.0f);
    }

    @Test
    public void testGetClinicaIdSelectedReturnsSameList() {
        List<Long> firstCall = patientFilterUiState.getClinicsIdSelected();
        firstCall.add(1L);

        List<Long> secondCall = patientFilterUiState.getClinicsIdSelected();
        assertEquals(1, secondCall.size());
        assertEquals(Long.valueOf(1L), secondCall.get(0));
    }

    @Test
    public void testGetClassificacaoImcsReturnsSameList() {
        List<ClassificacaoImc> firstCall = patientFilterUiState.getBmiClassificationsSelected();
        ClassificacaoImc imc = ClassificacaoImc.NORMAL;
        firstCall.add(imc);

        List<ClassificacaoImc> secondCall = patientFilterUiState.getBmiClassificationsSelected();
        assertEquals(1, secondCall.size());
        assertEquals(imc, secondCall.get(0));
    }

    @Test
    public void testConstructorWithZeroMinAndMax() {
        PatientFilterUiState state = new PatientFilterUiState(0, 0);
        assertEquals(0.0f, state.getTupleOfYearSlider()[0], 0.0f);
        assertEquals(0.0f, state.getTupleOfYearSlider()[1], 0.0f);
    }

    @Test
    public void testConstructorWithLargeAgeValues() {
        PatientFilterUiState state = new PatientFilterUiState(1, 150);
        assertEquals(1.0f, state.getTupleOfYearSlider()[0], 0.0f);
        assertEquals(150.0f, state.getTupleOfYearSlider()[1], 0.0f);
    }
}

