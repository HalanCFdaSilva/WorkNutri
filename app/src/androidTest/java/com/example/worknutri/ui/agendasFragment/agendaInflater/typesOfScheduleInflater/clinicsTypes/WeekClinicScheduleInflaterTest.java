package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.clinicsTypes;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.support.TestUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class WeekClinicScheduleInflaterTest {

    private final Context context = TestUtil.getThemedContext();

    @Test
    public void generateAgendaGroupsByWeekAndAddsCards() {

        // DayOfWork: assign c1 and c2 to the second day (index 1), c3 to third day (index 2)
        DayOfWork d1 = new DayOfWork();
        d1.setDayOfWeek("SEGUNDA"); d1.setIdClinica(1);d1.setId(1);

        DayOfWork d2 = new DayOfWork();
        d2.setDayOfWeek(d1.getDayOfWeek());d2.setIdClinica(2);d2.setId(2);

        DayOfWork d3 = new DayOfWork();
        d3.setDayOfWeek("TERÇA");d3.setIdClinica(3);d3.setId(3);

        List<DayOfWork> daysOfWork = Arrays.asList(d1, d2, d3);

        List<Clinica> list = ClinicScheduleInflaterAsserter.generateClinicsToTest();
        String[] categoriesNamesExpected = {d1.getDayOfWeek(), d3.getDayOfWeek()};

        List<List<Clinica>> clinicsExpectedInCards = Arrays.asList(
                Arrays.asList(list.get(0),list.get(1)),
                List.of(list.get(2)));

        WeekClinicScheduleInflater inflater = new WeekClinicScheduleInflater(context,daysOfWork);
        LinearLayout parent = new LinearLayout(context);
        inflater.generateAgenda(parent, list);

        assertEquals(2, parent.getChildCount());
        ClinicScheduleInflaterAsserter.assertLayout(parent, categoriesNamesExpected, clinicsExpectedInCards);
    }



    @Test
    public void ifMultipleDayOfWorkWithSameDayOfWeekContainsClinicIdEqualsClinicIsInsertedOnlyOneTime() {

        DayOfWork d1 = new DayOfWork();
        d1.setDayOfWeek("SEGUNDA");
        d1.setIdClinica(1);
        d1.setId(1);

        DayOfWork d2 = new DayOfWork();
        d2.setDayOfWeek(d1.getDayOfWeek());
        d2.setIdClinica(1);
        d2.setId(2);

        List<DayOfWork> daysOfWork = Arrays.asList(d1, d2);

        Clinica clinic = TestEntityFactory.generateClinic(); clinic.setId(1);
        List<Clinica> list = List.of(clinic);
        String[] categoriesNamesExpected = {d1.getDayOfWeek()};
        List<List<Clinica>> clinicsExpectedInCards = List.of(List.of(list.get(0)));


        WeekClinicScheduleInflater inflater = new WeekClinicScheduleInflater(context, daysOfWork);
        LinearLayout parent = new LinearLayout(context);
        inflater.generateAgenda(parent, list);

        assertEquals(1, parent.getChildCount());
        ClinicScheduleInflaterAsserter.assertLayout(parent, categoriesNamesExpected, clinicsExpectedInCards);
    }

    @Test
    public void ifDayOfworkWithDiferentDayOfWeekContainsClinicIdEqualsClinicIsInsertedInAllDaysOfWeekWereIsSelected() {

        DayOfWork d1 = new DayOfWork();
        d1.setDayOfWeek("SEGUNDA");
        d1.setIdClinica(1);
        d1.setId(1);

        DayOfWork d2 = new DayOfWork();
        d2.setDayOfWeek("TERÇA");
        d2.setIdClinica(1);
        d2.setId(2);

        List<DayOfWork> daysOfWork = Arrays.asList(d1, d2);

        Clinica clinic = TestEntityFactory.generateClinic();
        clinic.setId(1);
        List<Clinica> list = List.of(clinic);
        String[] categoriesNamesExpected = {d1.getDayOfWeek(), d2.getDayOfWeek()};

        List<Clinica> clinicExpected = List.of(list.get(0));
        List<List<Clinica>> clinicsExpectedInCards = Arrays.asList(clinicExpected, clinicExpected);


        WeekClinicScheduleInflater inflater = new WeekClinicScheduleInflater(context, daysOfWork);
        LinearLayout parent = new LinearLayout(context);
        inflater.generateAgenda(parent, list);

        assertEquals(2, parent.getChildCount());
        ClinicScheduleInflaterAsserter.assertLayout(parent, categoriesNamesExpected, clinicsExpectedInCards);
    }

    @Test
    public void ifClinicDoesNotContainDayOfWorkIsInsertedInCategoryWithoutDayOfWeek() {

        DayOfWork d1 = new DayOfWork();
        d1.setDayOfWeek("SEGUNDA");
        d1.setIdClinica(1);
        d1.setId(1);

        List<DayOfWork> daysOfWork = List.of(d1);

        Clinica clinic = TestEntityFactory.generateClinic();
        clinic.setId(2);
        List<Clinica> list = List.of(clinic);
        String[] categoriesNamesExpected = {WeekClinicScheduleInflater.DAY_NOT_FOUND};
        List<List<Clinica>> clinicsExpectedInCards = List.of(List.of(list.get(0)));


        WeekClinicScheduleInflater inflater = new WeekClinicScheduleInflater(context, daysOfWork);
        LinearLayout parent = new LinearLayout(context);
        inflater.generateAgenda(parent, list);

        assertEquals(1, parent.getChildCount());
        ClinicScheduleInflaterAsserter.assertLayout(parent, categoriesNamesExpected, clinicsExpectedInCards);
    }
}

