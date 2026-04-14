package com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ClinicFilterPojoTest {

    private ClinicFilterPojo clinicFilterPojo;

    @Before
    public void setUp() {
        clinicFilterPojo = new ClinicFilterPojo();
    }

    @Test
    public void testConstructorInitializesEmptyLists() {
        assertNotNull(clinicFilterPojo.getClinicsList());
        assertNotNull(clinicFilterPojo.getClinicsSelected());
        assertNotNull(clinicFilterPojo.getDayOfWorkList());
        assertTrue(clinicFilterPojo.getClinicsList().isEmpty());
        assertTrue(clinicFilterPojo.getClinicsSelected().isEmpty());
        assertTrue(clinicFilterPojo.getDayOfWorkList().isEmpty());
    }

    @Test
    public void testConstructorInitializesUiState() {
        assertNotNull(clinicFilterPojo.getUiState());
    }

    @Test
    public void testSetAndGetClinicas() {
        List<Clinica> clinicas = new ArrayList<>();
        Clinica clinica = new Clinica();
        clinicas.add(clinica);

        clinicFilterPojo.setClinicsList(clinicas);

        assertEquals(1, clinicFilterPojo.getClinicsList().size());
        assertEquals(clinica, clinicFilterPojo.getClinicsList().get(0));
    }

    @Test
    public void testSetAndGetClinicasSelected() {
        List<Clinica> clinicasSelected = new ArrayList<>();
        Clinica clinica = new Clinica();
        clinicasSelected.add(clinica);

        clinicFilterPojo.setClinicsSelected(clinicasSelected);

        assertEquals(1, clinicFilterPojo.getClinicsSelected().size());
        assertEquals(clinica, clinicFilterPojo.getClinicsSelected().get(0));
    }

    @Test
    public void testSetAndGetDayOfWorkList() {
        List<DayOfWork> dayOfWorkList = new ArrayList<>();
        DayOfWork dayOfWork = new DayOfWork();
        dayOfWorkList.add(dayOfWork);

        clinicFilterPojo.setDayOfWorkList(dayOfWorkList);

        assertEquals(1, clinicFilterPojo.getDayOfWorkList().size());
        assertEquals(dayOfWork, clinicFilterPojo.getDayOfWorkList().get(0));
    }

    @Test
    public void testSetAndGetPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        Paciente paciente = new Paciente();
        pacientes.add(paciente);

        clinicFilterPojo.setPatientList(pacientes);

        assertEquals(1, clinicFilterPojo.getPatientList().size());
        assertEquals(paciente, clinicFilterPojo.getPatientList().get(0));
    }

    @Test
    public void testSetMultipleClinicas() {
        List<Clinica> clinicas = new ArrayList<>();
        clinicas.add(new Clinica());
        clinicas.add(new Clinica());
        clinicas.add(new Clinica());

        clinicFilterPojo.setClinicsList(clinicas);

        assertEquals(3, clinicFilterPojo.getClinicsList().size());
    }

    @Test
    public void testSetMultipleDaysOfWork() {
        List<DayOfWork> dayOfWorkList = new ArrayList<>();
        dayOfWorkList.add(new DayOfWork());
        dayOfWorkList.add(new DayOfWork());

        clinicFilterPojo.setDayOfWorkList(dayOfWorkList);

        assertEquals(2, clinicFilterPojo.getDayOfWorkList().size());
    }

    @Test
    public void testSetMultiplePacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(new Paciente());
        pacientes.add(new Paciente());

        clinicFilterPojo.setPatientList(pacientes);

        assertEquals(2, clinicFilterPojo.getPatientList().size());
    }

    @Test
    public void testGetUiStateIsNotNull() {
        ClinicFilterUiState uiState = clinicFilterPojo.getUiState();
        assertNotNull(uiState);
    }

    @Test
    public void testClinicaFilterPojoIsSerializable() {
        assertTrue(clinicFilterPojo.getClass() instanceof java.io.Serializable);
    }

    @Test
    public void testMultipleInstancesIndependent() {
        ClinicFilterPojo pojo1 = new ClinicFilterPojo();
        ClinicFilterPojo pojo2 = new ClinicFilterPojo();

        List<Clinica> clinicas1 = new ArrayList<>();
        clinicas1.add(new Clinica());
        pojo1.setClinicsList(clinicas1);

        assertEquals(1, pojo1.getClinicsList().size());
        assertEquals(0, pojo2.getClinicsList().size());
    }

}

