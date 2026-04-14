package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.patientCategories;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.ActivityToTest;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.PatientFilterCategory;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PatientFilterPojo;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class PatientInClinicCategoryTest {

    private Context context;
    private List<Clinica> clinicas;
    private List<Antropometria> antropometrias;
    private PatientFilterPojo pojo;
    private PatientInClinicCategory patientInClinicCategory;
    private LayoutInflater layoutInflater;

    @Before
    public void setUp() {
        context = TestUtil.getContextWithFilterTheme();
        context = new ContextThemeWrapper(context, R.style.Theme_themeFilter_Chip);
        layoutInflater = LayoutInflater.from(context);

        clinicas = new ArrayList<>();
        Clinica c1 = new Clinica();
        c1.setId(1L);
        c1.setNome("Clínica A");
        clinicas.add(c1);

        Clinica c2 = new Clinica();
        c2.setId(2L);
        c2.setNome("Clínica B");
        clinicas.add(c2);

        Clinica c3 = new Clinica();
        c3.setId(3L);
        c3.setNome("Clínica C");
        clinicas.add(c3);

        List<Paciente> pacientes = TestEntityFactory.generatePatientListToTest();

        Paciente p1 = pacientes.get(0);
        p1.setClinicaId(1);
        Paciente p2 = pacientes.get(1);
        p2.setClinicaId(2);
        Paciente p3 = pacientes.get(2);
        p3.setClinicaId(3);

        Paciente p4 = new Paciente();
        p4.setId(4L);
        p4.setNomePaciente("Paciente 4");
        p4.setClinicaId(3);
        pacientes.add(p4);

        antropometrias = new ArrayList<>();

        pojo = new PatientFilterPojo(pacientes, antropometrias, clinicas);

        patientInClinicCategory = new PatientInClinicCategory(context, pojo);
    }

    @Test
    public void testPatientInClinicCategoryConstructor() {
        assertNotNull(patientInClinicCategory);
        assertNotNull(pojo);
        assertEquals(4, pojo.getPatientList().size());
        assertEquals(3, pojo.getClinicas().size());
    }

    @Test
    public void testGenerateViewReturnsViewGroup() {
        ViewGroup viewGroup = patientInClinicCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertTrue(viewGroup instanceof ViewGroup);
    }

    @Test
    public void testGenerateViewContainsChipGroup() {
        ViewGroup viewGroup = patientInClinicCategory.generateView(layoutInflater);
        ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);
        assertNotNull(chipGroup);
    }

    @Test
    public void testChipsAreCreatedOnlyForClinicasWithPatients() {
        Clinica clinicaSemPacientes = new Clinica();
        clinicaSemPacientes.setId(4L);
        clinicaSemPacientes.setNome("Clínica Sem Pacientes");
        pojo.getClinicas().add(clinicaSemPacientes);

        ViewGroup viewGroup = patientInClinicCategory.generateView(layoutInflater);
        ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

        assertEquals(3, chipGroup.getChildCount());
    }

    @Test
    public void testGetSelectedsInitiallyContainsAllPacientes() {
        List<Paciente> selecteds = patientInClinicCategory.getSelecteds();
        assertNotNull(selecteds);
        assertEquals(4, selecteds.size());
    }

    @Test
    public void testResetRestoresPacientes() {
        patientInClinicCategory.getSelecteds().clear();
        assertEquals(0, patientInClinicCategory.getSelecteds().size());

        patientInClinicCategory.generateView(layoutInflater);

        patientInClinicCategory.reset();

        assertEquals(4, patientInClinicCategory.getSelecteds().size());
    }

    @Test
    public void testSelectSingleClinicFiltersCorrectly() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            ViewGroup viewGroup = patientInClinicCategory.generateView(layoutInflater);
            assertNotNull(viewGroup);

            ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);
            assertNotNull(chipGroup);
            assertTrue(chipGroup.getChildCount() > 0);

            Chip firstChip = (Chip) chipGroup.getChildAt(0);
            firstChip.setChecked(true);

            List<Paciente> selecteds = patientInClinicCategory.getSelecteds();
            assertNotNull(selecteds);
            assertFalse(selecteds.isEmpty());

            // Todos os pacientes selecionados devem ser da clínica A (id = 1)
            for (Paciente paciente : selecteds) {
                assertEquals(1, paciente.getClinicaId());
            }
        });

    }

    @Test
    public void testSelectMultipleClinicFiltersCorrectly() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            ViewGroup viewGroup = patientInClinicCategory.generateView(layoutInflater);
            ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

            if (chipGroup.getChildCount() >= 2) {
                Chip firstChip = (Chip) chipGroup.getChildAt(0);
                Chip secondChip = (Chip) chipGroup.getChildAt(1);

                firstChip.setChecked(true);
                secondChip.setChecked(true);

                List<Paciente> selecteds = patientInClinicCategory.getSelecteds();

                assertTrue(selecteds.size() >= 2);

                boolean temClinica1 = selecteds.stream().anyMatch(p -> p.getClinicaId() == 1);
                boolean temClinica2 = selecteds.stream().anyMatch(p -> p.getClinicaId() == 2);

                assertTrue(temClinica1);
                assertTrue(temClinica2);
            }});

    }

    @Test
    public void testPatientInClinicCategoryWithSingleClinic() {
        List<Paciente> singleClinicPacientes = new ArrayList<>();
        Paciente p1 = new Paciente();
        p1.setId(1L);
        p1.setClinicaId(1);
        singleClinicPacientes.add(p1);

        Paciente p2 = new Paciente();
        p2.setId(2L);
        p2.setClinicaId(1);
        singleClinicPacientes.add(p2);

        List<Clinica> singleClinic = new ArrayList<>();
        Clinica c = new Clinica();
        c.setId(1L);
        c.setNome("Clínica Única");
        singleClinic.add(c);

        PatientFilterPojo singlePojo = new PatientFilterPojo(singleClinicPacientes, antropometrias, singleClinic);
        PatientInClinicCategory singleCategory = new PatientInClinicCategory(context, singlePojo);

        ViewGroup viewGroup = singleCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
    }

    @Test
    public void testEmptyPacienteList() {
        List<Paciente> emptyList = new ArrayList<>();
        PatientFilterPojo emptyPojo = new PatientFilterPojo(emptyList, antropometrias, clinicas);
        PatientInClinicCategory emptyCategory = new PatientInClinicCategory(context, emptyPojo);

        ViewGroup viewGroup = emptyCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
    }

    @Test
    public void testPatientInClinicCategoryInheritsPatientFilterCategory() {
        assertNotNull(patientInClinicCategory);
        assertTrue(patientInClinicCategory instanceof PatientFilterCategory);
    }

    @Test
    public void testMultiplePatientInClinicCategoryInstances() {
        PatientInClinicCategory category1 = new PatientInClinicCategory(context, pojo);
        PatientInClinicCategory category2 = new PatientInClinicCategory(context, pojo);

        assertNotNull(category1);
        assertNotNull(category2);
        assertNotEquals(category1, category2);
    }

    @Test
    public void testPatientInClinicCategoryWithVariousPatients() {
        List<Paciente> variousPacientes = new ArrayList<>();
        List<Clinica> variousClinics = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            Clinica c = new Clinica();
            c.setId(i);
            c.setNome("Clínica " + i);
            variousClinics.add(c);
        }

        for (int i = 1; i <= 9; i++) {
            Paciente p = new Paciente();
            p.setId(i);
            p.setNomePaciente("Paciente " + i);
            p.setClinicaId((i % 3) + 1);
            variousPacientes.add(p);
        }

        PatientFilterPojo variousPojo = new PatientFilterPojo(variousPacientes, antropometrias, variousClinics);
        PatientInClinicCategory variousCategory = new PatientInClinicCategory(context, variousPojo);

        ViewGroup viewGroup = variousCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertEquals(9, variousPojo.getPatientList().size());
    }

    @Test
    public void testPatientInClinicCategoryStateInitialization() {
        assertNotNull(pojo.getState());
        assertNotNull(pojo.getState().getClinicsIdSelected());
        assertEquals(0, pojo.getState().getClinicsIdSelected().size());
    }

    @Test
    public void testGenerateViewWithValidLayoutInflater() {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup result = patientInClinicCategory.generateView(inflater);
        assertNotNull(result);
    }

    @Test
    public void testPatientInClinicCategoryFiltersCorrectly() {
        List<Paciente> filterPacientes = new ArrayList<>();
        Paciente p1 = new Paciente();
        p1.setId(1L);
        p1.setNomePaciente("Patient A");
        p1.setClinicaId(1);
        filterPacientes.add(p1);

        Paciente p2 = new Paciente();
        p2.setId(2L);
        p2.setNomePaciente("Patient B");
        p2.setClinicaId(2);
        filterPacientes.add(p2);



        PatientFilterPojo filterPojo = new PatientFilterPojo(filterPacientes, antropometrias, getClinicas());
        PatientInClinicCategory filterCategory = new PatientInClinicCategory(context, filterPojo);

        List<Paciente> selecteds = filterCategory.getSelecteds();
        assertEquals(2, selecteds.size());
    }

    @NonNull
    private static List<Clinica> getClinicas() {
        List<Clinica> filterClinics = new ArrayList<>();
        Clinica c1 = new Clinica();
        c1.setId(1L);
        c1.setNome("Clinic 1");
        filterClinics.add(c1);

        Clinica c2 = new Clinica();
        c2.setId(2L);
        c2.setNome("Clinic 2");
        filterClinics.add(c2);
        return filterClinics;
    }

    @Test
    public void testChipsAreNotCheckedByDefault() {
        ViewGroup viewGroup = patientInClinicCategory.generateView(layoutInflater);
        ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            assertFalse(chip.isChecked());
        }
    }

    @Test
    public void testPatientInClinicCategoryHasCorrectContext() {
        assertNotNull(patientInClinicCategory);
    }

    @Test
    public void testMultiplePatientsPerClinic() {
        // Verificar que múltiplos pacientes de uma clínica são filtrados corretamente
        List<Paciente> filterPacientes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Paciente p = new Paciente();
            p.setId(i);
            p.setNomePaciente("Paciente " + i);
            if (i <= 3) {
                p.setClinicaId(1);
            } else {
                p.setClinicaId(2);
            }
            filterPacientes.add(p);
        }



        PatientFilterPojo filterPojo = new PatientFilterPojo(filterPacientes, antropometrias, getClinicas());
        PatientInClinicCategory filterCategory = new PatientInClinicCategory(context, filterPojo);

        ViewGroup viewGroup = filterCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertEquals(5, filterCategory.getSelecteds().size());
    }

    @Test
    public void testClinicaNameDisplayedInChip() {
        ViewGroup viewGroup = patientInClinicCategory.generateView(layoutInflater);
        ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            assertNotNull(chip.getText());
            assertTrue(chip.getText().length() > 0);
        }
    }

    @Test
    public void testUncheckingClinicRemovesPatientsFromFilter() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            ViewGroup viewGroup = patientInClinicCategory.generateView(layoutInflater);
            ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

            if (chipGroup.getChildCount() > 0) {
                Chip firstChip = (Chip) chipGroup.getChildAt(0);

                String clinicName = firstChip.getText().toString();
                firstChip.setChecked(true);
                checkIfPojoClinicsSelectedContainsOrNotPatientsFromClinic(clinicName, true);

                firstChip.setChecked(false);
                checkIfPojoClinicsSelectedContainsOrNotPatientsFromClinic(clinicName, false);
            }
        });

    }

    private void checkIfPojoClinicsSelectedContainsOrNotPatientsFromClinic(String clinicName, boolean shouldContain) {
        clinicas.stream().filter(c -> c.getNome().equals(clinicName))
                .findFirst()
                .ifPresent(c -> assertEquals(shouldContain,
                        pojo.getState().getClinicsIdSelected().contains(c.getId())));
    }

    @Test
    public void testClinicaIdStateIsStoredCorrectly() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            ViewGroup viewGroup = patientInClinicCategory.generateView(layoutInflater);
            ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

            if (chipGroup.getChildCount() > 0) {

                Chip chip = (Chip) chipGroup.getChildAt(0);
                chip.setChecked(true);

                List<Long> clinicaIdSelected = pojo.getState().getClinicsIdSelected();
                assertFalse(clinicaIdSelected.isEmpty());
            }
        });

    }

    @Test
    public void testResetClearsClinicaSelection() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            ViewGroup viewGroup = patientInClinicCategory.generateCategory(layoutInflater);
            ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

            if (chipGroup.getChildCount() > 0) {
                Chip chip = (Chip) chipGroup.getChildAt(0);
                chip.setChecked(true);

                assertTrue(chip.isChecked());

                patientInClinicCategory.reset();

                assertEquals(0, pojo.getState().getClinicsIdSelected().size());
            }
        });

    }

    @Test
    public void testAllChipsCanBeMarkedSimultaneously() {
        ActivityScenario<ActivityToTest> activityScenario = ActivityScenario.launch(ActivityToTest.class);
        activityScenario.onActivity(activity -> {
            ViewGroup viewGroup = patientInClinicCategory.generateView(activity.getLayoutInflater());
            ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

            for (int i = 0; i < chipGroup.getChildCount(); i++) {
                Chip chip = (Chip) chipGroup.getChildAt(i);
                chip.setChecked(true);
            }

            List<Paciente> selecteds = patientInClinicCategory.getSelecteds();
            assertEquals(4, selecteds.size());
        });

    }

    @Test
    public void testSinglePatientInClinic() {
        List<Paciente> singlePatient = new ArrayList<>();
        Paciente p = new Paciente();
        p.setId(1L);
        p.setNomePaciente("Single Patient");
        p.setClinicaId(1);
        singlePatient.add(p);

        List<Clinica> singleClinic = new ArrayList<>();
        Clinica c = new Clinica();
        c.setId(1L);
        c.setNome("Single Clinic");
        singleClinic.add(c);

        PatientFilterPojo singlePojo = new PatientFilterPojo(singlePatient, antropometrias, singleClinic);
        PatientInClinicCategory singleCategory = new PatientInClinicCategory(context, singlePojo);

        ViewGroup viewGroup = singleCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertEquals(1, singleCategory.getSelecteds().size());
    }
}


