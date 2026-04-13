package com.example.worknutri.ui.agendasFragment.filter.NavsDirection;

import android.os.Bundle;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class NavDirectionPacienteFilterTest {

    private NavDirectionPacienteFilter navDirectionPacienteFilter;
    private PacienteFilterPojo pacienteFilterPojo;
    private final int actionId = 1;

    @Before
    public void setUp() {
        // Create test patients
        List<Paciente> pacientes = new ArrayList<>();
        Paciente paciente1 = new Paciente();
        paciente1.setId(1L);
        paciente1.setNomePaciente("Patient A");
        paciente1.setIdade(25);
        pacientes.add(paciente1);
        
        Paciente paciente2 = new Paciente();
        paciente2.setId(2L);
        paciente2.setNomePaciente("Patient B");
        paciente2.setIdade(35);
        pacientes.add(paciente2);
        
        // Create test anthropometries
        List<Antropometria> antropometrias = new ArrayList<>();
        Antropometria a1 = new Antropometria();
        a1.setIdPaciente(1);
        a1.setAltura("1.70");
        a1.setPeso("70.0");
        antropometrias.add(a1);
        
        Antropometria a2 = new Antropometria();
        a2.setIdPaciente(2);
        a2.setAltura("1.80");
        a2.setPeso("80.0");
        antropometrias.add(a2);
        
        // Create clinics
        List<Clinica> clinicas = new ArrayList<>();
        
        pacienteFilterPojo = new PacienteFilterPojo(pacientes, antropometrias, clinicas);
        navDirectionPacienteFilter = new NavDirectionPacienteFilter(actionId, pacienteFilterPojo);
    }

    @Test
    public void testNavDirectionPacienteFilterConstructor() {
        assertNotNull(navDirectionPacienteFilter);
        assertNotNull(pacienteFilterPojo);
    }

    @Test
    public void testGetArgumentsReturnsBundle() {
        Bundle bundle = navDirectionPacienteFilter.getArguments();
        assertNotNull(bundle);
        assertTrue(bundle instanceof Bundle);
    }

    @Test
    public void testBundleContainsPacienteFilterPojo() {
        Bundle bundle = navDirectionPacienteFilter.getArguments();
        assertNotNull(bundle);
        assertTrue(bundle.containsKey(ConstantsFilters.PACIENTE_FILTER_POJO));
    }

    @Test
    public void testBundlePacienteFilterPojoIsCorrect() {
        Bundle bundle = navDirectionPacienteFilter.getArguments();
        PacienteFilterPojo retrievedPojo = (PacienteFilterPojo) bundle.getSerializable(ConstantsFilters.PACIENTE_FILTER_POJO);
        
        assertNotNull(retrievedPojo);
        assertEquals(pacienteFilterPojo.getPacientes().size(), retrievedPojo.getPacientes().size());
    }

    @Test
    public void testBundlePacientesAreCorrect() {
        Bundle bundle = navDirectionPacienteFilter.getArguments();
        PacienteFilterPojo retrievedPojo = (PacienteFilterPojo) bundle.getSerializable(ConstantsFilters.PACIENTE_FILTER_POJO);
        
        assertNotNull(retrievedPojo);
        assertEquals(2, retrievedPojo.getPacientes().size());
    }

    @Test
    public void testBundleAntropometriaListIsCorrect() {
        Bundle bundle = navDirectionPacienteFilter.getArguments();
        PacienteFilterPojo retrievedPojo = (PacienteFilterPojo) bundle.getSerializable(ConstantsFilters.PACIENTE_FILTER_POJO);
        
        assertNotNull(retrievedPojo);
        assertEquals(pacienteFilterPojo.getAntropometriaList().size(), retrievedPojo.getAntropometriaList().size());
    }

    @Test
    public void testBundlePacienteFirstPacienteIsCorrect() {
        Bundle bundle = navDirectionPacienteFilter.getArguments();
        PacienteFilterPojo retrievedPojo = (PacienteFilterPojo) bundle.getSerializable(ConstantsFilters.PACIENTE_FILTER_POJO);
        
        assertNotNull(retrievedPojo);
        Paciente firstPaciente = retrievedPojo.getPacientes().get(0);
        assertEquals("Patient A", firstPaciente.getNomePaciente());
        assertEquals(25, firstPaciente.getIdade());
    }

    @Test
    public void testBundlePacienteSecondPacienteIsCorrect() {
        Bundle bundle = navDirectionPacienteFilter.getArguments();
        PacienteFilterPojo retrievedPojo = (PacienteFilterPojo) bundle.getSerializable(ConstantsFilters.PACIENTE_FILTER_POJO);
        
        assertNotNull(retrievedPojo);
        Paciente secondPaciente = retrievedPojo.getPacientes().get(1);
        assertEquals("Patient B", secondPaciente.getNomePaciente());
        assertEquals(35, secondPaciente.getIdade());
    }

    @Test
    public void testBundleFirstAntropometriaIsCorrect() {
        Bundle bundle = navDirectionPacienteFilter.getArguments();
        PacienteFilterPojo retrievedPojo = (PacienteFilterPojo) bundle.getSerializable(ConstantsFilters.PACIENTE_FILTER_POJO);
        
        assertNotNull(retrievedPojo);
        Antropometria firstAntro = retrievedPojo.getAntropometriaList().get(0);
        assertEquals("1.70", firstAntro.getAltura());
        assertEquals("70.0", firstAntro.getPeso());
    }

    @Test
    public void testBundleSecondAntropometriaIsCorrect() {
        Bundle bundle = navDirectionPacienteFilter.getArguments();
        PacienteFilterPojo retrievedPojo = (PacienteFilterPojo) bundle.getSerializable(ConstantsFilters.PACIENTE_FILTER_POJO);
        
        assertNotNull(retrievedPojo);
        Antropometria secondAntro = retrievedPojo.getAntropometriaList().get(1);
        assertEquals("1.80", secondAntro.getAltura());
        assertEquals("80.0", secondAntro.getPeso());
    }

    @Test
    public void testBundleCanBeCalled() {
        Bundle bundle1 = navDirectionPacienteFilter.getArguments();
        Bundle bundle2 = navDirectionPacienteFilter.getArguments();
        
        assertNotNull(bundle1);
        assertNotNull(bundle2);
        
        PacienteFilterPojo pojo1 = (PacienteFilterPojo) bundle1.getSerializable(ConstantsFilters.PACIENTE_FILTER_POJO);
        PacienteFilterPojo pojo2 = (PacienteFilterPojo) bundle2.getSerializable(ConstantsFilters.PACIENTE_FILTER_POJO);
        assertNotNull(pojo1);
        assertNotNull(pojo2);
        assertEquals(pojo1.getPacientes().size(), pojo2.getPacientes().size());
    }

    @Test
    public void testSerializableClassCheck() {
        assertTrue(java.io.Serializable.class.isAssignableFrom(PacienteFilterPojo.class));
    }


    @Test
    public void testBundleIsNotNull() {
        Bundle bundle = navDirectionPacienteFilter.getArguments();
        assertNotNull(bundle);
    }

    @Test
    public void testMultiplePacientesInBundle() {
        List<Paciente> pacientes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Paciente paciente = new Paciente();
            paciente.setId( i);
            paciente.setNomePaciente("Patient " + i);
            paciente.setIdade(20 + i);
            pacientes.add(paciente);
        }
        
        PacienteFilterPojo multiplePojo = new PacienteFilterPojo(pacientes, new ArrayList<>(), new ArrayList<>());
        NavDirectionPacienteFilter multipleNavDirection = new NavDirectionPacienteFilter(actionId, multiplePojo);
        
        Bundle bundle = multipleNavDirection.getArguments();
        PacienteFilterPojo retrievedPojo = (PacienteFilterPojo) bundle.getSerializable(ConstantsFilters.PACIENTE_FILTER_POJO);

        assertNotNull(retrievedPojo);
        assertEquals(5, retrievedPojo.getPacientes().size());
    }

    @Test
    public void testBundleWithEmptyPacientes() {
        PacienteFilterPojo emptyPojo = new PacienteFilterPojo(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        NavDirectionPacienteFilter emptyNavDirection = new NavDirectionPacienteFilter(actionId, emptyPojo);
        
        Bundle bundle = emptyNavDirection.getArguments();
        PacienteFilterPojo retrievedPojo = (PacienteFilterPojo) bundle.getSerializable(ConstantsFilters.PACIENTE_FILTER_POJO);
        
        assertNotNull(retrievedPojo);
    }

}

