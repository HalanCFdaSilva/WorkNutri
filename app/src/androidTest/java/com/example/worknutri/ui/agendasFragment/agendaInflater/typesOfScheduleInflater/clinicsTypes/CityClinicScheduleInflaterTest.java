package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.clinicsTypes;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.support.TestUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CityClinicScheduleInflaterTest {

    private final Context context = TestUtil.getThemedContext();


    @Test
    public void generateAgendaCreatesCategoriesAndAddsCards() {

        List<Clinica> list = ClinicScheduleInflaterAsserter.generateClinicsToTest();


        String[] categoriesNamesExpected = {list.get(0).getCidade().toUpperCase(),
                list.get(1).getCidade().toUpperCase(),
                list.get(2).getCidade().toUpperCase()};

        List<List<Clinica>> clinicsExpectedInCards = Arrays.asList(List.of(list.get(0)),
                List.of(list.get(1)),
                List.of(list.get(2)));

        LinearLayout parent = new LinearLayout(context);
        CityClinicScheduleInflater inflater = new CityClinicScheduleInflater(context);
        inflater.generateAgenda(parent, list);

        assertEquals(3, parent.getChildCount());
        ClinicScheduleInflaterAsserter.assertLayout(parent, categoriesNamesExpected, clinicsExpectedInCards);

    }

    @Test
    public void ifMoreThanOneClinicContainsSameCityGenerateAgendaInsertAllThisCityInSameCategory() {

        List<Clinica> list = ClinicScheduleInflaterAsserter.generateClinicsToTest();
        list = list.subList(0,2); // only two clinics with same city
        list.get(1).setCidade(list.get(0).getCidade());

        String[] categoriesNamesExpected = {list.get(0).getCidade().toUpperCase()};
        List<List<Clinica>> clinicsExpectedInCards = List.of(list);


        LinearLayout parent = new LinearLayout(context);
        CityClinicScheduleInflater inflater = new CityClinicScheduleInflater(context);
        inflater.generateAgenda(parent, list);

        assertEquals(1, parent.getChildCount());
        ClinicScheduleInflaterAsserter.assertLayout(parent, categoriesNamesExpected, clinicsExpectedInCards);

    }

}

