package com.example.worknutri.ui.agendasFragment.filter.NavsDirection;

import android.os.Bundle;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicFilterPojo;

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
    private ClinicFilterPojo clinicFilterPojo;
    private final int actionId = 1;

    @Before
    public void setUp() {
        clinicFilterPojo = new ClinicFilterPojo();

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

        clinicFilterPojo.setClinicsList(clinicas);
        clinicFilterPojo.setClinicsSelected(clinicas);

        navDirectionClinicaFilter = new NavDirectionClinicaFilter(actionId, clinicFilterPojo);
    }

    @Test
    public void testNavDirectionClinicaFilterConstructor() {
        assertNotNull(navDirectionClinicaFilter);
        assertNotNull(clinicFilterPojo);
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
        ClinicFilterPojo retrievedPojo = (ClinicFilterPojo) bundle.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);

        assertNotNull(retrievedPojo);
        assertEquals(clinicFilterPojo.getClinicsList().size(), retrievedPojo.getClinicsList().size());
    }

    @Test
    public void testBundleClinicasAreCorrect() {
        Bundle bundle = navDirectionClinicaFilter.getArguments();
        ClinicFilterPojo retrievedPojo = (ClinicFilterPojo) bundle.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);

        assertNotNull(retrievedPojo);
        assertEquals(2, retrievedPojo.getClinicsList().size());
    }

    @Test
    public void testBundleClinicasSelectedAreCorrect() {
        Bundle bundle = navDirectionClinicaFilter.getArguments();
        ClinicFilterPojo retrievedPojo = (ClinicFilterPojo) bundle.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);

        assertNotNull(retrievedPojo);
        assertEquals(clinicFilterPojo.getClinicsSelected().size(), retrievedPojo.getClinicsSelected().size());
    }

    @Test
    public void testBundleClinicaFirstClinicIsCorrect() {
        Bundle bundle = navDirectionClinicaFilter.getArguments();
        ClinicFilterPojo retrievedPojo = (ClinicFilterPojo) bundle.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);

        assertNotNull(retrievedPojo);
        Clinica firstClinica = retrievedPojo.getClinicsList().get(0);
        assertEquals("Clinic A", firstClinica.getNome());
        assertEquals("São Paulo", firstClinica.getCidade());
    }

    @Test
    public void testBundleClinicaSecondClinicIsCorrect() {
        Bundle bundle = navDirectionClinicaFilter.getArguments();
        ClinicFilterPojo retrievedPojo = (ClinicFilterPojo) bundle.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);

        assertNotNull(retrievedPojo);
        Clinica secondClinica = retrievedPojo.getClinicsList().get(1);
        assertEquals("Clinic B", secondClinica.getNome());
        assertEquals("Rio de Janeiro", secondClinica.getCidade());
    }

    @Test
    public void testBundleCanBeCalled() {
        Bundle bundle1 = navDirectionClinicaFilter.getArguments();
        Bundle bundle2 = navDirectionClinicaFilter.getArguments();

        assertNotNull(bundle1);
        assertNotNull(bundle2);

        ClinicFilterPojo pojo1 = (ClinicFilterPojo) bundle1.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);
        ClinicFilterPojo pojo2 = (ClinicFilterPojo) bundle2.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);

        assertNotNull(pojo1);
        assertNotNull(pojo2);
        assertEquals(pojo1.getClinicsList().size(), pojo2.getClinicsList().size());
    }

    @Test
    public void testBundleWithEmptyClinicas() {
        ClinicFilterPojo emptyPojo = new ClinicFilterPojo();
        NavDirectionClinicaFilter emptyNavDirection = new NavDirectionClinicaFilter(actionId, emptyPojo);

        Bundle bundle = emptyNavDirection.getArguments();
        ClinicFilterPojo retrievedPojo = (ClinicFilterPojo) bundle.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);

        assertNotNull(retrievedPojo);
        assertEquals(0, retrievedPojo.getClinicsList().size());
    }

    @Test
    public void testSerializableClassCheck() {
        assertTrue(java.io.Serializable.class.isAssignableFrom(ClinicFilterPojo.class));
    }



    @Test
    public void testBundleIsNotNull() {
        Bundle bundle = navDirectionClinicaFilter.getArguments();
        assertNotNull(bundle);
    }

    @Test
    public void testMultipleClinicasInBundle() {
        ClinicFilterPojo multiplePojo = new ClinicFilterPojo();
        List<Clinica> clinicas = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Clinica clinica = new Clinica();
            clinica.setId(i);
            clinica.setNome("Clinic " + i);
            clinica.setCidade("City " + i);
            clinicas.add(clinica);
        }

        multiplePojo.setClinicsList(clinicas);
        NavDirectionClinicaFilter multipleNavDirection = new NavDirectionClinicaFilter(actionId, multiplePojo);

        Bundle bundle = multipleNavDirection.getArguments();
        ClinicFilterPojo retrievedPojo = (ClinicFilterPojo) bundle.getSerializable(ConstantsFilters.CLINICA_FILTER_POJO);

        assertNotNull(retrievedPojo);
        assertEquals(5, retrievedPojo.getClinicsList().size());
    }

}

