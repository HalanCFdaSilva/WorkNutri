package com.example.worknutri.ui.agendasFragment.registryInflater;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.clinicsTypes.CityClinicRegistryInflater;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.asserter.ClinicScheduleInflaterAsserter;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.clinicsTypes.DistrictClinicRegistryInflater;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.clinicsTypes.PatientsClinicRegistryInflater;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.clinicsTypes.WeekClinicRegistryInflater;
import com.example.worknutri.ui.agendasFragment.filter.pojos.OrderFilterSelectedsBy;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ClinicRegistryInflaterTest {

    private final Context context = TestUtil.getThemedContext();
    private ClinicRegistryInflater registryInflater;
    private ClinicaFilterPojo pojo;
    private List<Clinica> clinics;
    private List<DayOfWork> daysOfWork;
    private List<Paciente> patients;

    @Before
    public void setup() {
        clinics = TestEntityFactory.generateClinicListToTest();
        daysOfWork = new ArrayList<>();
        patients = new ArrayList<>();
        pojo = new ClinicaFilterPojo();
        pojo.setClinicasSelected(clinics);
        pojo.setDayOfWorkList(daysOfWork);
        pojo.setPacientes(patients);
        registryInflater = new ClinicRegistryInflater(pojo);
    }

    @Test
    public void inflateUsesDistrictInflater() {
        pojo.getUiState().setOrderBy(OrderFilterSelectedsBy.DISTRICT);

        LinearLayout parentFromRegistry = new LinearLayout(context);
        registryInflater.inflateSchedule(parentFromRegistry, context);

        LinearLayout parentFromDirect = new LinearLayout(context);
        DistrictClinicRegistryInflater direct = new DistrictClinicRegistryInflater(context);
        direct.generateAgenda(parentFromDirect, pojo.getClinicasSelected());
        
        assertEquals(parentFromDirect.getChildCount(), parentFromRegistry.getChildCount());

        String[] categoriesNamesExpected = {
                clinics.get(0).getBairro().toUpperCase(),
                clinics.get(2).getBairro().toUpperCase()
        };
        List<List<Clinica>> clinicsExpectedInCards = List.of(
                List.of(clinics.get(0), clinics.get(1)),
                List.of(clinics.get(2))
        );
        ClinicScheduleInflaterAsserter.assertLayout(parentFromRegistry, categoriesNamesExpected,
                clinicsExpectedInCards);
    }

    @Test
    public void inflateUsesCityInflater() {
        pojo.getUiState().setOrderBy(OrderFilterSelectedsBy.CITY);

        LinearLayout parentFromRegistry = new LinearLayout(context);
        registryInflater.inflateSchedule(parentFromRegistry, context);

        LinearLayout parentFromDirect = new LinearLayout(context);
        CityClinicRegistryInflater direct = new CityClinicRegistryInflater(context);
        direct.generateAgenda(parentFromDirect, pojo.getClinicasSelected());
        
        assertEquals(parentFromDirect.getChildCount(), parentFromRegistry.getChildCount());

        String[] categoriesNamesExpected = {
                clinics.get(0).getCidade().toUpperCase(),
                clinics.get(1).getCidade().toUpperCase(),
                clinics.get(2).getCidade().toUpperCase()
        };
        List<List<Clinica>> clinicsExpectedInCards = List.of(
                List.of(clinics.get(0)),
                List.of(clinics.get(1)),
                List.of(clinics.get(2))
        );
        ClinicScheduleInflaterAsserter.assertLayout(parentFromRegistry, categoriesNamesExpected,
                clinicsExpectedInCards);
    }

    @Test
    public void inflateUsesWeekInflater() {
        DayOfWork d1 = new DayOfWork();
        d1.setDayOfWeek("SEGUNDA");
        d1.setIdClinica(1);
        d1.setId(1);

        DayOfWork d2 = new DayOfWork();
        d2.setDayOfWeek("TERÇA");
        d2.setIdClinica(2);
        d2.setId(2);

        DayOfWork d3 = new DayOfWork();
        d3.setDayOfWeek("QUARTA");
        d3.setIdClinica(3);
        d3.setId(3);

        daysOfWork.addAll(Arrays.asList(d1, d2, d3));
        pojo.getUiState().setOrderBy(OrderFilterSelectedsBy.DAY_OF_WEEK);

        LinearLayout parentFromRegistry = new LinearLayout(context);
        registryInflater.inflateSchedule(parentFromRegistry, context);

        LinearLayout parentFromDirect = new LinearLayout(context);
        WeekClinicRegistryInflater direct = new WeekClinicRegistryInflater(context, daysOfWork);
        direct.generateAgenda(parentFromDirect, pojo.getClinicasSelected());
        
        assertEquals(parentFromDirect.getChildCount(), parentFromRegistry.getChildCount());

        String[] categoriesNamesExpected = {"SEGUNDA", "TERÇA", "QUARTA"};
        List<List<Clinica>> clinicsExpectedInCards = List.of(
                List.of(clinics.get(0)),
                List.of(clinics.get(1)),
                List.of(clinics.get(2))
        );
        ClinicScheduleInflaterAsserter.assertLayout(parentFromRegistry, categoriesNamesExpected,
                clinicsExpectedInCards);
    }

    @Test
    public void inflateUsesNumberOfPatientsInflater() {
        Paciente p1 = TestEntityFactory.generatePatient();
        p1.setId(1);
        p1.setClinicaId(1);
        Paciente p2 = TestEntityFactory.generatePatient();
        p2.setId(2);
        p2.setClinicaId(1);
        Paciente p3 = TestEntityFactory.generatePatient();
        p3.setId(3);
        p3.setClinicaId(2);
        
        patients.addAll(Arrays.asList(p1, p2, p3));
        pojo.getUiState().setOrderBy(OrderFilterSelectedsBy.NUMBER_OF_PATIENTS);

        LinearLayout parentFromRegistry = new LinearLayout(context);
        registryInflater.inflateSchedule(parentFromRegistry, context);

        LinearLayout parentFromDirect = new LinearLayout(context);
        PatientsClinicRegistryInflater direct = new PatientsClinicRegistryInflater(context, patients);
        direct.generateAgenda(parentFromDirect, pojo.getClinicasSelected());
        
        assertEquals(parentFromDirect.getChildCount(), parentFromRegistry.getChildCount());

        String[] categoriesNamesExpected = {"2", "1", "0"};
        List<List<Clinica>> clinicsExpectedInCards = List.of(
                List.of(clinics.get(0)),
                List.of(clinics.get(1)),
                List.of(clinics.get(2))
        );
        ClinicScheduleInflaterAsserter.assertLayout(parentFromRegistry, categoriesNamesExpected,
                clinicsExpectedInCards);
    }
}

