package com.example.worknutri.ui.agendasFragment.filter.NavsDirection;

import android.os.Bundle;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class NavDirectionClinicaFilterTest {

    private NavDirectionClinicaFilter navDirectionClinicaFilter;
    private ClinicaFilterPojo clinicaFilterPojo;
    private final int actionId = 1;

    @Before
    public void setUp() {
        clinicaFilterPojo = new ClinicaFilterPojo();

        // Create test clinics
        List<Clinica> clinicas = new ArrayList<>();
        Clinica clinica1 = new Clinica();
        clinica1.setId(1);
        clinica1.setNome("Clinic A");
        clinica1.setCidade("São Paulo");
        clinicas.add(clinica1);

        Clinica clinica2 = new Clinica();
        clinica2.setId(2);
        clinica2.setNome("Clinic B");
        clinica2.setCidade("Rio de Janeiro");
        clinicas.add(clinica2);

        clinicaFilterPojo.setClinicas(clinicas);
        clinicaFilterPojo.setClinicasSelected(clinicas);

        navDirectionClinicaFilter = new NavDirectionClinicaFilter(actionId, clinicaFilterPojo);
    }

    @Test
    public void testNavDirectionClinicaFilterConstructor() {
        assertNotNull(navDirectionClinicaFilter);
        assertNotNull(clinicaFilterPojo);
    }

    @Test
    public void testGetArgumentsReturnsBundle() {
        Bundle bundle = navDirectionClinicaFilter.getArguments();
        assertNotNull(bundle);
        assertTrue(bundle instanceof Bundle);
    }

    @Test
    public void testBundleContainsClinicaFilterPojo() {
        Bundle bundle = navDirectionClinicaFilter.getArguments();
        assertNotNull(bundle);
        assertTrue(bundle.containsKey(ConstantsFilters.CLINICA_FILTER_POJO));
    }

    @Test
    public void testBundleClinicaFilterPojoIsCorrect() {
        Bundle bundle = navDirectionClinicaFilter.getArguments();
        ClinicaFilterPojo retrievedPojo = (ClinicaFilterPojo) bundle.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);

        assertNotNull(retrievedPojo);
        assertEquals(clinicaFilterPojo.getClinicas().size(), retrievedPojo.getClinicas().size());
    }

    @Test
    public void testBundleClinicasAreCorrect() {
        Bundle bundle = navDirectionClinicaFilter.getArguments();
        ClinicaFilterPojo retrievedPojo = (ClinicaFilterPojo) bundle.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);

        assertNotNull(retrievedPojo);
        assertEquals(2, retrievedPojo.getClinicas().size());
    }

    @Test
    public void testBundleClinicasSelectedAreCorrect() {
        Bundle bundle = navDirectionClinicaFilter.getArguments();
        ClinicaFilterPojo retrievedPojo = (ClinicaFilterPojo) bundle.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);

        assertNotNull(retrievedPojo);
        assertEquals(clinicaFilterPojo.getClinicasSelected().size(), retrievedPojo.getClinicasSelected().size());
    }

    @Test
    public void testBundleClinicaFirstClinicIsCorrect() {
        Bundle bundle = navDirectionClinicaFilter.getArguments();
        ClinicaFilterPojo retrievedPojo = (ClinicaFilterPojo) bundle.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);

        assertNotNull(retrievedPojo);
        Clinica firstClinica = retrievedPojo.getClinicas().get(0);
        assertEquals("Clinic A", firstClinica.getNome());
        assertEquals("São Paulo", firstClinica.getCidade());
    }

    @Test
    public void testBundleClinicaSecondClinicIsCorrect() {
        Bundle bundle = navDirectionClinicaFilter.getArguments();
        ClinicaFilterPojo retrievedPojo = (ClinicaFilterPojo) bundle.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);

        assertNotNull(retrievedPojo);
        Clinica secondClinica = retrievedPojo.getClinicas().get(1);
        assertEquals("Clinic B", secondClinica.getNome());
        assertEquals("Rio de Janeiro", secondClinica.getCidade());
    }

    @Test
    public void testBundleCanBeCalled() {
        Bundle bundle1 = navDirectionClinicaFilter.getArguments();
        Bundle bundle2 = navDirectionClinicaFilter.getArguments();

        assertNotNull(bundle1);
        assertNotNull(bundle2);

        ClinicaFilterPojo pojo1 = (ClinicaFilterPojo) bundle1.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);
        ClinicaFilterPojo pojo2 = (ClinicaFilterPojo) bundle2.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);

        assertNotNull(pojo1);
        assertNotNull(pojo2);
        assertEquals(pojo1.getClinicas().size(), pojo2.getClinicas().size());
    }

    @Test
    public void testBundleWithEmptyClinicas() {
        ClinicaFilterPojo emptyPojo = new ClinicaFilterPojo();
        NavDirectionClinicaFilter emptyNavDirection = new NavDirectionClinicaFilter(actionId, emptyPojo);

        Bundle bundle = emptyNavDirection.getArguments();
        ClinicaFilterPojo retrievedPojo = (ClinicaFilterPojo) bundle.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);

        assertNotNull(retrievedPojo);
        assertEquals(0, retrievedPojo.getClinicas().size());
    }

    @Test
    public void testSerializableClassCheck() {
        assertTrue(java.io.Serializable.class.isAssignableFrom(ClinicaFilterPojo.class));
    }



    @Test
    public void testBundleIsNotNull() {
        Bundle bundle = navDirectionClinicaFilter.getArguments();
        assertNotNull(bundle);
    }

    @Test
    public void testMultipleClinicasInBundle() {
        ClinicaFilterPojo multiplePojo = new ClinicaFilterPojo();
        List<Clinica> clinicas = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Clinica clinica = new Clinica();
            clinica.setId(i);
            clinica.setNome("Clinic " + i);
            clinica.setCidade("City " + i);
            clinicas.add(clinica);
        }

        multiplePojo.setClinicas(clinicas);
        NavDirectionClinicaFilter multipleNavDirection = new NavDirectionClinicaFilter(actionId, multiplePojo);

        Bundle bundle = multipleNavDirection.getArguments();
        ClinicaFilterPojo retrievedPojo = (ClinicaFilterPojo) bundle.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);

        assertNotNull(retrievedPojo);
        assertEquals(5, retrievedPojo.getClinicas().size());
    }

}

