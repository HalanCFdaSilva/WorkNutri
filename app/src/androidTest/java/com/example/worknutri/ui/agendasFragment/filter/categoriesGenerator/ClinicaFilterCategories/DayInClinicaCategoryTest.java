package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicaFilterCategories;

import android.content.Context;
import android.view.ContextThemeWrapper;


import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DayInClinicaCategoryTest {

    private ClinicaFilterPojo clinicaFilterPojo;
    private DayInClinicaCategory dayInClinicaCategory;
    private List<Clinica> clinics;

    @Before
    public void setUp() {
        Context context = TestUtil.getThemedContext();
        context = new ContextThemeWrapper(context, R.style.Theme_themeFilter_Chip);
        clinicaFilterPojo = new ClinicaFilterPojo();
        
        // Criar clínicas para teste
        clinics = new ArrayList<>();
        Clinica clinica1 = new Clinica();
        clinica1.setId(1);
        clinica1.setNome("Clinica A");
        clinica1.setCidade("Sao Paulo");
        clinica1.setBairro("Zona Sul");
        
        Clinica clinica2 = new Clinica();
        clinica2.setId(2);
        clinica2.setNome("Clinica B");
        clinica2.setCidade("Sao Paulo");
        clinica2.setBairro("Zona Leste");
        
        Clinica clinica3 = new Clinica();
        clinica3.setId(3);
        clinica3.setNome("Clinica C");
        clinica3.setCidade("Sao Paulo");
        clinica3.setBairro("Zona Norte");
        
        clinics.add(clinica1);
        clinics.add(clinica2);
        clinics.add(clinica3);
        
        // Criar DaysOfWork
        List<DayOfWork> daysOfWork = getDaysOfWork();

        clinicaFilterPojo.setClinicas(clinics);
        clinicaFilterPojo.setClinicasSelected(new ArrayList<>(clinics));
        clinicaFilterPojo.setDayOfWorkList(daysOfWork);
        
        dayInClinicaCategory = new DayInClinicaCategory(context, clinicaFilterPojo);
    }

    @NonNull
    private static List<DayOfWork> getDaysOfWork() {
        List<DayOfWork> daysOfWork = new ArrayList<>();
        DayOfWork dayOfWork1 = new DayOfWork();
        dayOfWork1.setId(1);
        dayOfWork1.setIdClinica(1);
        dayOfWork1.setDayOfWeek("SEGUNDA");
        dayOfWork1.setHoraInicio("08:00");
        dayOfWork1.setHoraFim("12:00");

        DayOfWork dayOfWork2 = new DayOfWork();
        dayOfWork2.setId(2);
        dayOfWork2.setIdClinica(2);
        dayOfWork2.setDayOfWeek("SEGUNDA");
        dayOfWork2.setHoraInicio("13:00");
        dayOfWork2.setHoraFim("17:00");

        DayOfWork dayOfWork3 = new DayOfWork();
        dayOfWork3.setId(3);
        dayOfWork3.setIdClinica(3);
        dayOfWork3.setDayOfWeek("TERÇA");
        dayOfWork3.setHoraInicio("09:00");
        dayOfWork3.setHoraFim("14:00");

        daysOfWork.add(dayOfWork1);
        daysOfWork.add(dayOfWork2);
        daysOfWork.add(dayOfWork3);
        return daysOfWork;
    }

    // ==================== Testes de Inicialização ====================

    @Test
    public void testDayInClinicaCategoryIsNotNull() {
        assertNotNull(dayInClinicaCategory);
    }

    @Test
    public void testDayInClinicaCategoryHasSelecteds() {
        assertNotNull(dayInClinicaCategory.getSelecteds());
        assertFalse(dayInClinicaCategory.getSelecteds().isEmpty());
    }

    @Test
    public void testDayInClinicaCategoryInitialSelecteds() {
        List<Clinica> selecteds = dayInClinicaCategory.getSelecteds();
        assertEquals(3, selecteds.size());
    }

    @Test
    public void testDayInClinicaCategoryInitializationWithDayOfWork() {
        List<DayOfWork> daysInCategory = clinicaFilterPojo.getDayOfWorkList();
        assertNotNull(daysInCategory);
        assertFalse(daysInCategory.isEmpty());
        assertEquals(3, daysInCategory.size());
    }

    // ==================== Testes de Reset ====================

    @Test
    public void testResetClearsDaysOfWeekSelected() {
        List<String> daysOfWeekSelected = clinicaFilterPojo.getUiState().getDaysOfWeekSelected();
        daysOfWeekSelected.add("SEGUNDA");
        daysOfWeekSelected.add("TERÇA");
        
        dayInClinicaCategory.reset();
        
        daysOfWeekSelected = clinicaFilterPojo.getUiState().getDaysOfWeekSelected();
        assertTrue(daysOfWeekSelected.isEmpty());
    }

    @Test
    public void testResetRestoresAllClinics() {
        List<Clinica> selecteds = dayInClinicaCategory.getSelecteds();
        selecteds.clear();
        assertEquals(0, selecteds.size());
        
        dayInClinicaCategory.reset();
        
        selecteds = dayInClinicaCategory.getSelecteds();
        assertEquals(3, selecteds.size());
    }

    @Test
    public void testResetMultipleTimes() {
        dayInClinicaCategory.reset();
        List<Clinica> selecteds1 = dayInClinicaCategory.getSelecteds();
        assertEquals(3, selecteds1.size());
        
        selecteds1.clear();
        dayInClinicaCategory.reset();
        List<Clinica> selecteds2 = dayInClinicaCategory.getSelecteds();
        assertEquals(3, selecteds2.size());
    }

    @Test
    public void testDayInClinicaCategoryResetRestoresInitialState() {
        List<Clinica> selecteds = dayInClinicaCategory.getSelecteds();
        selecteds.remove(0);
        List<String> daysOfWeekSelected = clinicaFilterPojo.getUiState().getDaysOfWeekSelected();
        daysOfWeekSelected.add("SEGUNDA");
        
        dayInClinicaCategory.reset();
        
        selecteds = dayInClinicaCategory.getSelecteds();
        assertEquals(3, selecteds.size());
        daysOfWeekSelected = clinicaFilterPojo.getUiState().getDaysOfWeekSelected();
        assertTrue(daysOfWeekSelected.isEmpty());
    }

    // ==================== Testes de Getters ====================

    @Test
    public void testGetSelectedsReturnsClinicasSelecteds() {
        List<Clinica> selecteds = dayInClinicaCategory.getSelecteds();
        assertNotNull(selecteds);
        assertEquals(3, selecteds.size());
        assertTrue(selecteds.contains(clinics.get(0)));
        assertTrue(selecteds.contains(clinics.get(1)));
        assertTrue(selecteds.contains(clinics.get(2)));
    }

    @Test
    public void testDayInClinicaCategoryWithMultipleDaysOfWork() {
        List<DayOfWork> daysOfWorkList = clinicaFilterPojo.getDayOfWorkList();
        assertEquals(3, daysOfWorkList.size());
        
        DayOfWork day1 = daysOfWorkList.get(0);
        assertEquals("SEGUNDA", day1.getDayOfWeek());
        assertEquals(1, day1.getIdClinica());
    }

    // ==================== Testes de Comportamento Público ====================

    @Test
    public void testClinicasFilteredCorrectlyByDayOfWeek() {
        List<Clinica> selecteds = dayInClinicaCategory.getSelecteds();
        assertEquals(3, selecteds.size());
    }

    @Test
    public void testAllClinicasInitiallySelected() {
        List<Clinica> selecteds = dayInClinicaCategory.getSelecteds();
        assertTrue(selecteds.contains(clinics.get(0)));
        assertTrue(selecteds.contains(clinics.get(1)));
        assertTrue(selecteds.contains(clinics.get(2)));
    }

    @Test
    public void testResetClearsSelections() {
        clinicaFilterPojo.getUiState().getDaysOfWeekSelected().add("SEGUNDA");
        dayInClinicaCategory.getSelecteds().clear();
        
        dayInClinicaCategory.reset();
        
        assertEquals(3, dayInClinicaCategory.getSelecteds().size());
        assertTrue(clinicaFilterPojo.getUiState().getDaysOfWeekSelected().isEmpty());
    }

}
