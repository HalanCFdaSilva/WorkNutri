package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.clinicsTypes;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.support.TestUtil;

import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class PatientsClinicScheduleInflaterTest {

    private final Context context = TestUtil.getThemedContext();

    @Test
    public void generateAgendaGroupsByNumberOfPatientsAndAddsCards() {

        Paciente p1 = TestEntityFactory.generatePatient();
        p1.setId(1); p1.setClinicaId(1);
        Paciente p2 = TestEntityFactory.generatePatient();
        p2.setId(2); p2.setClinicaId(1);
        Paciente p3 = TestEntityFactory.generatePatient();
        p3.setId(3); p3.setClinicaId(2);
        List<Paciente> patient = Arrays.asList(p1,p2,p3);

        List<Clinica> list = ClinicScheduleInflaterAsserter.generateClinicsToTest();
        String[] categoriesNamesExpected = {"2", "1","0"};
        List<List<Clinica>> clinicsExpectedInCards = Arrays.asList(List.of(list.get(0)),
                List.of(list.get(1)),
                List.of(list.get(2)));

        PatientsClinicScheduleInflater inflater = new PatientsClinicScheduleInflater(context,patient);
        LinearLayout parent = new LinearLayout(context);
        inflater.generateAgenda(parent, list);

        assertEquals(3, parent.getChildCount());
        ClinicScheduleInflaterAsserter.assertLayout(parent, categoriesNamesExpected, clinicsExpectedInCards);


    }

    @Test
    public void ifMoreThanOneClinicContainsSameNumberOfPatientsGenerateAgendaInsertAllThisClinicInSameCategory() {

        Paciente p1 = TestEntityFactory.generatePatient();
        p1.setId(1); p1.setClinicaId(1);
        Paciente p2 = TestEntityFactory.generatePatient();
        p2.setId(2); p2.setClinicaId(2);
        List<Paciente> patient = Arrays.asList(p1,p2);

        List<Clinica> list = ClinicScheduleInflaterAsserter.generateClinicsToTest();
        list = list.subList(0,2);


        String[] categoriesNamesExpected = {"1"};
        List<List<Clinica>> clinicsExpectedInCards = List.of(list);


        LinearLayout parent = new LinearLayout(context);
        PatientsClinicScheduleInflater inflater = new PatientsClinicScheduleInflater(context,patient);
        inflater.generateAgenda(parent, list);

        assertEquals(1, parent.getChildCount());
        ClinicScheduleInflaterAsserter.assertLayout(parent, categoriesNamesExpected, clinicsExpectedInCards);

    }



}

