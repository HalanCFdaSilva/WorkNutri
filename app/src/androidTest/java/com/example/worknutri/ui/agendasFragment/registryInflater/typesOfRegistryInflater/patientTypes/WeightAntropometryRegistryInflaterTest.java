package com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.patientTypes;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.asserter.PatientScheduleInflaterAsserter;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class WeightAntropometryRegistryInflaterTest {

    private final Context context = TestUtil.getThemedContext();

    @Test
    public void generateAgendaCreatesCategoriesAndAddsCards() {
        List<Paciente> patientList = PatientScheduleInflaterAsserter.generatePatientToTest();

        List<Antropometria> antropometriaList = PatientScheduleInflaterAsserter.generateAnthropometryToTest(patientList);


        String[] categoriesNamesExpected = new String[] {
                "60,00 - 69,99 KG",
                "80,00 - 89,99 KG",
                "90,00 - 99,99 KG"
        };

        List<List<Paciente>> patientsExpectedInCards = List.of(
                List.of(patientList.get(0)),
                List.of(patientList.get(1)),
                List.of(patientList.get(2))
        );

        LinearLayout parent = new LinearLayout(context);
        WeightAntropometryRegistryInflater inflater = new WeightAntropometryRegistryInflater(context, antropometriaList);
        inflater.generateAgenda(parent, patientList);

        assertEquals(3, parent.getChildCount());
        PatientScheduleInflaterAsserter.assertLayout(parent, categoriesNamesExpected, patientsExpectedInCards);
    }

    @Test
    public void ifMoreThenOneRegistryHasSameIntegerPartTheyAreAddedInTheSameCategory() {
        List<Paciente> patientList = PatientScheduleInflaterAsserter.generatePatientToTest();

        List<Antropometria> antropometriaList = PatientScheduleInflaterAsserter.generateAnthropometryToTest(patientList);
        antropometriaList.get(0).setPeso("60.0");
        antropometriaList.get(1).setPeso("65.38");
        antropometriaList.get(2).setPeso("69.99");



        String[] categoriesNamesExpected = new String[] {
                "60,00 - 69,99 KG"
        };

        List<List<Paciente>> patientsExpectedInCards = List.of( patientList );

        LinearLayout parent = new LinearLayout(context);
        WeightAntropometryRegistryInflater inflater = new WeightAntropometryRegistryInflater(context, antropometriaList);
        inflater.generateAgenda(parent,  patientList );

        assertEquals(1, parent.getChildCount());
        PatientScheduleInflaterAsserter.assertLayout(parent, categoriesNamesExpected, patientsExpectedInCards);
    }
}

