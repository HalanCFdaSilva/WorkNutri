package com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.asserter.ClinicScheduleInflaterAsserter;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.asserter.PatientScheduleInflaterAsserter;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class NameRegistryInflaterTest {

    private final Context context = TestUtil.getThemedContext();

    @Test
    public void generateAgendaGroupsClinicsByFirstLetterAndAddsCards() {
        List<Clinica> clinics = ClinicScheduleInflaterAsserter.generateClinicsToTest();

        String[] categoriesExpected = {"A", "B", "G"};
        List<List<Clinica>> clinicsExpectedInCards = Arrays.asList(
                List.of(clinics.get(0)),
                List.of(clinics.get(1)),
                List.of(clinics.get(2))
        );

        LinearLayout parent = new LinearLayout(context);
        NameRegistryInflater<Clinica> inflater = new NameRegistryInflater<>(context);
        inflater.generateAgenda(parent, clinics);

        assertEquals(3, parent.getChildCount());
        ClinicScheduleInflaterAsserter.assertLayout(parent, categoriesExpected, clinicsExpectedInCards);
    }

    @Test
    public void generateAgendaGroupsPatientsByFirstLetterAndAddsCards() {
        // Prepare patients with names starting with A, A, B
        Paciente p1 = TestEntityFactory.generatePatient(); p1.setNomePaciente("Alice");
        Paciente p2 = TestEntityFactory.generatePatient(); p2.setNomePaciente("Arnold");
        Paciente p3 = TestEntityFactory.generatePatient(); p3.setNomePaciente("Bruno");

        List<Paciente> patients = Arrays.asList(p1, p2, p3);

        String[] categoriesExpected = {"A", "B"};
        List<List<Paciente>> patientsExpectedInCards = Arrays.asList(
                List.of(p1, p2),
                List.of(p3)
        );

        LinearLayout parent = new LinearLayout(context);
        NameRegistryInflater<Paciente> inflater = new NameRegistryInflater<>(context);
        inflater.generateAgenda(parent, patients);

        assertEquals(2, parent.getChildCount());
        PatientScheduleInflaterAsserter.assertLayout(parent, categoriesExpected, patientsExpectedInCards);
    }

    @Test
    public void clinicsWithSameInitialLetterDifferentCaseAreGroupedTogether() {


        List<Clinica> clinics = ClinicScheduleInflaterAsserter.generateClinicsToTest().subList(0, 2);
        clinics.get(1).setNome("alpha Center");

        String[] categoriesExpected = {"A"};
        List<List<Clinica>> clinicsExpectedInCards = List.of(clinics);

        LinearLayout parent = new LinearLayout(context);
        NameRegistryInflater<Clinica> inflater = new NameRegistryInflater<>(context);
        inflater.generateAgenda(parent, clinics);

        assertEquals(1, parent.getChildCount());
        ClinicScheduleInflaterAsserter.assertLayout(parent, categoriesExpected, clinicsExpectedInCards);
    }

    @Test
    public void patientsWithSameInitialLetterDifferentCaseAreGroupedTogether() {
        Paciente p1 = TestEntityFactory.generatePatient(); p1.setNomePaciente("alice");
        Paciente p2 = TestEntityFactory.generatePatient(); p2.setNomePaciente("Alice");

        List<Paciente> patients = Arrays.asList(p1, p2);

        String[] categoriesExpected = {"A"};
        List<List<Paciente>> patientsExpectedInCards = List.of(patients);

        LinearLayout parent = new LinearLayout(context);
        NameRegistryInflater<Paciente> inflater = new NameRegistryInflater<>(context);
        inflater.generateAgenda(parent, patients);

        assertEquals(1, parent.getChildCount());
        PatientScheduleInflaterAsserter.assertLayout(parent, categoriesExpected, patientsExpectedInCards);
    }

}
