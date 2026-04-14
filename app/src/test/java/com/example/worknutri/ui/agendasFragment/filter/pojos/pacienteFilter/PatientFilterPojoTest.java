package com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PatientFilterPojoTest {

    private PatientFilterPojo patientFilterPojo;
    private List<Paciente> pacientes;
    private List<Antropometria> antropometriaList;
    private List<Clinica> clinicas;

    @Before
    public void setUp() {
        pacientes = new ArrayList<>();
        antropometriaList = new ArrayList<>();
        clinicas = new ArrayList<>();
        patientFilterPojo = new PatientFilterPojo(pacientes, antropometriaList, clinicas);
    }

    @Test
    public void testConstructorInitializesWithEmptyLists() {
        patientFilterPojo = new PatientFilterPojo(pacientes, antropometriaList, clinicas);

        assertNotNull(patientFilterPojo.getPatientList());
        assertNotNull(patientFilterPojo.getAnthropometryList());
        assertNotNull(patientFilterPojo.getClinicas());
        assertTrue(patientFilterPojo.getPatientList().isEmpty());
    }

    @Test
    public void testConstructorInitializesState() {
        patientFilterPojo = new PatientFilterPojo(pacientes, antropometriaList, clinicas);

        assertNotNull(patientFilterPojo.getState());
    }

    @Test
    public void testConstructorInitializesPacienteSelectedWithCopyOfPacientes() {
        Paciente paciente1 = new Paciente();
        paciente1.setIdade(25);
        Paciente paciente2 = new Paciente();
        paciente2.setIdade(30);
        pacientes.add(paciente1);
        pacientes.add(paciente2);

        patientFilterPojo = new PatientFilterPojo(pacientes, antropometriaList, clinicas);

        assertEquals(2, patientFilterPojo.getPatientsSelected().size());
    }

    @Test
    public void testResetPatientsSelected() {
        Paciente paciente1 = new Paciente();
        paciente1.setIdade(25);
        Paciente paciente2 = new Paciente();
        paciente2.setIdade(30);
        pacientes.add(paciente1);
        pacientes.add(paciente2);

        patientFilterPojo = new PatientFilterPojo(pacientes, antropometriaList, clinicas);

        // Clear the selected list
        patientFilterPojo.getPatientsSelected().clear();
        assertEquals(0, patientFilterPojo.getPatientsSelected().size());

        // Reset should restore the list
        patientFilterPojo.resetPatientsSelected();
        assertEquals(2, patientFilterPojo.getPatientsSelected().size());
    }

    @Test
    public void testSetAndGetPacientes() {

        List<Paciente> newPacientes = new ArrayList<>();
        newPacientes.add(new Paciente());
        newPacientes.add(new Paciente());

        patientFilterPojo.setPatientList(newPacientes);

        assertEquals(2, patientFilterPojo.getPatientList().size());
    }

    @Test
    public void testSetAndGetAntropometriaList() {

        List<Antropometria> newAntropometriaList = new ArrayList<>();
        newAntropometriaList.add(new Antropometria());
        newAntropometriaList.add(new Antropometria());

        patientFilterPojo.setAnthropometryList(newAntropometriaList);

        assertEquals(2, patientFilterPojo.getAnthropometryList().size());
    }

    @Test
    public void testSetAndGetClinicas() {

        List<Clinica> newClinicas = new ArrayList<>();
        newClinicas.add(new Clinica());

        patientFilterPojo.setClinicas(newClinicas);

        assertEquals(1, patientFilterPojo.getClinicas().size());
    }

    @Test
    public void testSetAndGetPacienteSelected() {

        List<Paciente> selectedPacientes = new ArrayList<>();
        selectedPacientes.add(new Paciente());

        patientFilterPojo.setPatientsSelected(selectedPacientes);

        assertEquals(1, patientFilterPojo.getPatientsSelected().size());
    }

    @Test
    public void testConstructorCalculatesCorrectMinAndMaxAge() {
        Paciente paciente1 = new Paciente();
        paciente1.setIdade(20);
        Paciente paciente2 = new Paciente();
        paciente2.setIdade(50);
        Paciente paciente3 = new Paciente();
        paciente3.setIdade(35);
        pacientes.add(paciente1);
        pacientes.add(paciente2);
        pacientes.add(paciente3);

        patientFilterPojo = new PatientFilterPojo(pacientes, antropometriaList, clinicas);

        float[] yearSlider = patientFilterPojo.getState().getTupleOfYearSlider();
        assertEquals(20.0f, yearSlider[0], 0.0f); // min age
        assertEquals(50.0f, yearSlider[1], 0.0f); // max age
    }

    @Test
    public void testConstructorWithSinglePatient() {
        Paciente paciente = new Paciente();
        paciente.setIdade(30);
        pacientes.add(paciente);

        patientFilterPojo = new PatientFilterPojo(pacientes, antropometriaList, clinicas);

        float[] yearSlider = patientFilterPojo.getState().getTupleOfYearSlider();
        assertEquals(30.0f, yearSlider[0], 0.0f);
        assertEquals(30.0f, yearSlider[1], 0.0f);
    }

    @Test
    public void testPacienteFilterPojoIsSerializable() {
        patientFilterPojo = new PatientFilterPojo(pacientes, antropometriaList, clinicas);
        assertTrue(patientFilterPojo instanceof java.io.Serializable);
    }

    @Test
    public void testGetPacientesReturnsSameReference() {
        List<Paciente> firstCall = patientFilterPojo.getPatientList();
        List<Paciente> secondCall = patientFilterPojo.getPatientList();

        assertEquals(firstCall, secondCall);
    }

    @Test
    public void testGetStateReturnsSameReference() {
        PatientFilterUiState firstCall = patientFilterPojo.getState();
        PatientFilterUiState secondCall = patientFilterPojo.getState();

        assertEquals(firstCall, secondCall);
    }

    @Test
    public void testMultiplePojosIndependent() {
        Paciente paciente1 = new Paciente();
        paciente1.setIdade(25);
        pacientes.add(paciente1);

        PatientFilterPojo pojo1 = new PatientFilterPojo(pacientes, antropometriaList, clinicas);

        List<Paciente> pacientes2 = new ArrayList<>();
        Paciente paciente2 = new Paciente();
        paciente2.setIdade(40);
        pacientes2.add(paciente2);

        PatientFilterPojo pojo2 = new PatientFilterPojo(pacientes2, antropometriaList, clinicas);

        assertEquals(25, pojo1.getState().getTupleOfYearSlider()[0], 0.0f);
        assertEquals(40, pojo2.getState().getTupleOfYearSlider()[0], 0.0f);
    }

    @Test
    public void testResetPatientsSelectedRestoresOriginalList() {
        Paciente paciente1 = new Paciente();
        Paciente paciente2 = new Paciente();
        pacientes.add(paciente1);
        pacientes.add(paciente2);

        patientFilterPojo = new PatientFilterPojo(pacientes, antropometriaList, clinicas);
        
        int originalSize = patientFilterPojo.getPatientsSelected().size();
        
        // Modify selected list
        patientFilterPojo.getPatientsSelected().clear();
        assertEquals(0, patientFilterPojo.getPatientsSelected().size());
        
        // Reset and verify
        patientFilterPojo.resetPatientsSelected();
        assertEquals(originalSize, patientFilterPojo.getPatientsSelected().size());
    }

    @Test
    public void testConstructorWithMultiplePacientesWithSameAge() {
        Paciente paciente1 = new Paciente();
        paciente1.setIdade(30);
        Paciente paciente2 = new Paciente();
        paciente2.setIdade(30);
        Paciente paciente3 = new Paciente();
        paciente3.setIdade(30);
        pacientes.add(paciente1);
        pacientes.add(paciente2);
        pacientes.add(paciente3);

        patientFilterPojo = new PatientFilterPojo(pacientes, antropometriaList, clinicas);

        float[] yearSlider = patientFilterPojo.getState().getTupleOfYearSlider();
        assertEquals(30.0f, yearSlider[0], 0.0f);
        assertEquals(30.0f, yearSlider[1], 0.0f);
    }

    @Test
    public void testConstructorWithMultipleClinicas() {
        Clinica clinica1 = new Clinica();
        Clinica clinica2 = new Clinica();
        Clinica clinica3 = new Clinica();
        clinicas.add(clinica1);
        clinicas.add(clinica2);
        clinicas.add(clinica3);

        patientFilterPojo = new PatientFilterPojo(pacientes, antropometriaList, clinicas);

        assertEquals(3, patientFilterPojo.getClinicas().size());
    }

    @Test
    public void testConstructorWithMultipleAntropometrias() {
        Antropometria ant1 = new Antropometria();
        Antropometria ant2 = new Antropometria();
        antropometriaList.add(ant1);
        antropometriaList.add(ant2);

        patientFilterPojo = new PatientFilterPojo(pacientes, antropometriaList, clinicas);

        assertEquals(2, patientFilterPojo.getAnthropometryList().size());
    }

    @Test
    public void testPacienteSelectedInitializedCorrectly() {
        Paciente paciente1 = new Paciente();
        Paciente paciente2 = new Paciente();
        pacientes.add(paciente1);
        pacientes.add(paciente2);

        patientFilterPojo = new PatientFilterPojo(pacientes, antropometriaList, clinicas);

        // PacienteSelected should have all patients initially
        assertEquals(pacientes.size(), patientFilterPojo.getPatientsSelected().size());
    }
}

