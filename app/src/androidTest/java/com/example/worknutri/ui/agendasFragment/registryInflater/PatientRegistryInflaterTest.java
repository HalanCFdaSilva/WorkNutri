package com.example.worknutri.ui.agendasFragment.registryInflater;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.patientTypes.AgeRegistryInflater;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.patientTypes.HeightAntropometryRegistryInflater;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.patientTypes.WeightAntropometryRegistryInflater;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.asserter.PatientScheduleInflaterAsserter;
import com.example.worknutri.ui.agendasFragment.filter.pojos.OrderFilterSelectedsBy;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PatientFilterPojo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class PatientRegistryInflaterTest {

    private final Context context = TestUtil.getThemedContext();
    private PatientRegistryInflater registryInflater;
    private PatientFilterPojo pojo;
    private List<Paciente> patients;
    private List<Antropometria> anthropometry;
    @Before
    public void setup() {
        patients = TestEntityFactory.generatePatientListToTest();
        anthropometry = TestEntityFactory.generateAnthropometryListToTest(patients);
        pojo = new PatientFilterPojo(patients, anthropometry, new ArrayList<>());
        registryInflater = new PatientRegistryInflater(pojo);
    }
    @Test
    public void inflateUsesIMCInflater() {

        pojo.getState().setOrderBy(OrderFilterSelectedsBy.IMC_CATEGORY);

        LinearLayout parentFromRegistry = new LinearLayout(context);
        registryInflater.inflateSchedule(parentFromRegistry, context);

        List<Paciente> patients = pojo.getPatientList();
        String[] expectedCategoriesName = getCategoryNamesForIMC(pojo.getAnthropometryList());
        List<List<Paciente>> patientsExpectedInCards = List.of(
                List.of(patients.get(0)),
                List.of(patients.get(1)),
                List.of(patients.get(2)));
        
        assertEquals(3, parentFromRegistry.getChildCount());
        PatientScheduleInflaterAsserter.assertLayout(parentFromRegistry, expectedCategoriesName, patientsExpectedInCards);
    }
    private String[] getCategoryNamesForIMC(List<Antropometria> anthropometry) {
        return new String[]{
                com.example.worknutri.calcular.ClassificacaoImc.tipoImc(Double.parseDouble(anthropometry.get(0).getImc())).toString(),
                com.example.worknutri.calcular.ClassificacaoImc.tipoImc(Double.parseDouble(anthropometry.get(1).getImc())).toString(),
                com.example.worknutri.calcular.ClassificacaoImc.tipoImc(Double.parseDouble(anthropometry.get(2).getImc())).toString()
        };
    }

    @Test
    public void inflateUsesHeightInflater() {
        
        pojo.getState().setOrderBy(OrderFilterSelectedsBy.HEIGHT);
        anthropometry.get(0).setAltura("1.60");
        anthropometry.get(1).setAltura("2.38");
        anthropometry.get(2).setAltura("3.99");

        LinearLayout parentFromRegistry = new LinearLayout(context);
        registryInflater.inflateSchedule(parentFromRegistry, context);

        LinearLayout parentFromDirect = new LinearLayout(context);
        HeightAntropometryRegistryInflater direct = new HeightAntropometryRegistryInflater(context, anthropometry);
        direct.generateAgenda(parentFromDirect, pojo.getPatientList());
        assertEquals(parentFromDirect.getChildCount(), parentFromRegistry.getChildCount());

        String[] categoriesNamesExpected = new String[] {
                "1,00 - 1,99 METROS",
                "2,00 - 2,99 METROS",
                "3,00 - 3,99 METROS"
        };


        List<List<Paciente>> patientsExpectedInCards = List.of(
                List.of( patients.get(0)),
                List.of( patients.get(1)),
                List.of( patients.get(2))
        );
        PatientScheduleInflaterAsserter.assertLayout(parentFromRegistry, categoriesNamesExpected,
                patientsExpectedInCards);
    }

    @Test
    public void inflateUsesWeightInflater() {
        anthropometry.get(0).setPeso("60");
        anthropometry.get(1).setPeso("80");
        anthropometry.get(2).setPeso("90");

        pojo.getState().setOrderBy(OrderFilterSelectedsBy.WEIGHT);

        LinearLayout parentFromRegistry = new LinearLayout(context);
        registryInflater.inflateSchedule(parentFromRegistry, context);

        LinearLayout parentFromDirect = new LinearLayout(context);
        WeightAntropometryRegistryInflater direct = new WeightAntropometryRegistryInflater(context, anthropometry);
        direct.generateAgenda(parentFromDirect, patients);
        assertEquals(parentFromDirect.getChildCount(), parentFromRegistry.getChildCount());

        String[] categoriesNamesExpected = new String[] {
                "60,00 - 69,99 KG",
                "80,00 - 89,99 KG",
                "90,00 - 99,99 KG"
        };
        List<List<Paciente>> patientsExpectedInCards = List.of(
                List.of(patients.get(0)),
                List.of(patients.get(1)),
                List.of(patients.get(2))
        );
        PatientScheduleInflaterAsserter.assertLayout(parentFromRegistry, categoriesNamesExpected,
                patientsExpectedInCards);
    }

    @Test
    public void inflateUsesAgeInflater() {
        patients.get(0).setNascimento("12/06/2018");
        patients.get(1).setNascimento("01/01/2005");
        patients.get(2).setNascimento("02/02/1980");

        pojo.getState().setOrderBy(OrderFilterSelectedsBy.AGE);

        LinearLayout parentFromRegistry = new LinearLayout(context);
        registryInflater.inflateSchedule(parentFromRegistry, context);

        LinearLayout parentFromDirect = new LinearLayout(context);
        AgeRegistryInflater direct = new AgeRegistryInflater(context);
        direct.generateAgenda(parentFromDirect, patients);
        assertEquals(parentFromDirect.getChildCount(), parentFromRegistry.getChildCount());

        String[] categoriesNamesExpected = new String[] {
                PatientScheduleInflaterAsserter.buildExpectedTitle(patients.get(0)),
                PatientScheduleInflaterAsserter.buildExpectedTitle(patients.get(1)),
                PatientScheduleInflaterAsserter.buildExpectedTitle(patients.get(2))
        };

        List<List<Paciente>> patientsExpectedInCards = Arrays.asList(
                List.of(patients.get(0)),
                List.of(patients.get(1)),
                List.of(patients.get(2))
        );
        PatientScheduleInflaterAsserter.assertLayout(parentFromRegistry, categoriesNamesExpected,
                patientsExpectedInCards);
    }





}

