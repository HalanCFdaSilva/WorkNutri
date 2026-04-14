package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicFilterCategories.ClinicFilterCategoryFactory;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicFilterCategories.DayInClinicCategory;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicFilterCategories.HourWorkCategory;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicFilterPojo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ClinicFilterCategoryFactoryTest {

    private Context context;
    private ClinicFilterPojo clinicFilterPojo;

    @Before
    public void setUp() {
        context = TestUtil.getThemedContext();
        clinicFilterPojo = new ClinicFilterPojo();
        
        // Criar algumas clínicas para o teste
        List<Clinica> clinics = new ArrayList<>();
        Clinica clinica1 = new Clinica();
        clinica1.setId(1);
        clinica1.setNome("Clinica A");
        clinica1.setCidade("Sao Paulo");
        clinica1.setBairro("Zona Sul");
        clinics.add(clinica1);
        
        clinicFilterPojo.setClinicsSelected(clinics);
    }

    @Test
    public void testGenerateDayInClinicaCategoryReturnsNotNull() {
        DayInClinicCategory category = ClinicFilterCategoryFactory.generateDayInClinicaCategory(context, clinicFilterPojo);
        assertNotNull(category);
    }

    @Test
    public void testGenerateDayInClinicaCategoryReturnsCorrectType() {
        DayInClinicCategory category = ClinicFilterCategoryFactory.generateDayInClinicaCategory(context, clinicFilterPojo);
        assertTrue(category instanceof DayInClinicCategory);
    }

    @Test
    public void testGenerateDayInClinicaCategoryWithValidContext() {
        DayInClinicCategory category = ClinicFilterCategoryFactory.generateDayInClinicaCategory(context, clinicFilterPojo);
        assertNotNull(category);
    }

    @Test
    public void testGenerateHourWorkCategoryReturnsNotNull() {
        HourWorkCategory category = ClinicFilterCategoryFactory.generateHourWorkCategory(context, clinicFilterPojo);
        assertNotNull(category);
    }

    @Test
    public void testGenerateHourWorkCategoryReturnsCorrectType() {
        HourWorkCategory category = ClinicFilterCategoryFactory.generateHourWorkCategory(context, clinicFilterPojo);
        assertTrue(category instanceof HourWorkCategory);
    }

    @Test
    public void testGenerateHourWorkCategoryWithValidContext() {
        HourWorkCategory category = ClinicFilterCategoryFactory.generateHourWorkCategory(context, clinicFilterPojo);
        assertNotNull(category);
    }

    @Test
    public void testBothCategoriesCanBeCreatedWithSamePojo() {
        DayInClinicCategory dayCategory = ClinicFilterCategoryFactory.generateDayInClinicaCategory(context, clinicFilterPojo);
        HourWorkCategory hourCategory = ClinicFilterCategoryFactory.generateHourWorkCategory(context, clinicFilterPojo);
        
        assertNotNull(dayCategory);
        assertNotNull(hourCategory);
    }


}









