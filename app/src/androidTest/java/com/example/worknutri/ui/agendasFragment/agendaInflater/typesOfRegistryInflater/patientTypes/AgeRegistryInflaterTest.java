package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfRegistryInflater.patientTypes;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfRegistryInflater.asserter.PatientScheduleInflaterAsserter;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AgeRegistryInflaterTest {

    private final Context context = TestUtil.getThemedContext();

    @Test
    public void generateAgendaCreatesCategoriesAndAddsCards() {
        List<Paciente> list = PatientScheduleInflaterAsserter.generatePatientToTest();
        list.get(0).setNascimento("12/06/2018"); // child

        list.get(1).setNascimento("01/01/2005"); // teenager

        list.get(2).setNascimento("02/02/1980"); // adult



        String[] categoriesNamesExpected = new String[] {
                PatientScheduleInflaterAsserter.buildExpectedTitle(list.get(0)),
                PatientScheduleInflaterAsserter.buildExpectedTitle(list.get(1)),
                PatientScheduleInflaterAsserter.buildExpectedTitle(list.get(2))
        };

        List<List<Paciente>> patientsExpectedInCards = Arrays.asList(
                List.of(list.get(0)),
                List.of(list.get(1)),
                List.of(list.get(2))
        );

        LinearLayout parent = new LinearLayout(context);
        AgeRegistryInflater inflater = new AgeRegistryInflater(context);
        inflater.generateAgenda(parent, list);

        assertEquals(3, parent.getChildCount());

        PatientScheduleInflaterAsserter.assertLayout(parent, categoriesNamesExpected, patientsExpectedInCards);

    }

    @Test
    public void ifMoreThenOneRegistryBirthInSameYearTheyAreAddedInTheSameCategory() {
        List<Paciente> list = PatientScheduleInflaterAsserter.generatePatientToTest();
        list.get(0).setNascimento("02/02/2018");
        list.get(1).setNascimento("01/06/2018");
        list.get(2).setNascimento("12/08/2018");

        String[] categoriesNamesExpected = new String[] {
                PatientScheduleInflaterAsserter.buildExpectedTitle(list.get(0)),
                PatientScheduleInflaterAsserter.buildExpectedTitle(list.get(1)),
                PatientScheduleInflaterAsserter.buildExpectedTitle(list.get(2))
        };

        List<List<Paciente>> patientsExpectedInCards = List.of(list);

        LinearLayout parent = new LinearLayout(context);
        AgeRegistryInflater inflater = new AgeRegistryInflater(context);
        inflater.generateAgenda(parent, list);

        assertEquals(1, parent.getChildCount());

        PatientScheduleInflaterAsserter.assertLayout(parent, categoriesNamesExpected, patientsExpectedInCards);
    }


}

